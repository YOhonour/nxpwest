package nxp.west.infobase.nxpwest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nxp.west.infobase.nxpwest.dao.CompetitionDao;
import nxp.west.infobase.nxpwest.dao.DrawLotsDao;
import nxp.west.infobase.nxpwest.dao.DrawLotsPoolDao;
import nxp.west.infobase.nxpwest.dao.UserDao;
import nxp.west.infobase.nxpwest.entity.*;
import nxp.west.infobase.nxpwest.exception.DrawErrorException;
import nxp.west.infobase.nxpwest.exception.DrawException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DrawLotsService {
    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    DrawLotsPoolDao poolDao;

    @Autowired
    DrawLotsDao drawLotsDao;

    @Autowired
    CompetitionDao competitionDao;

    @Autowired
    UserDao userDao;

    /**
     * 从抽签池中，为队伍分配序号
     *
     * @param phone   用户的手机号
     * @param comp_id 比赛 id
     * @return 返回抽到的签
     * @throws JsonProcessingException
     * @throws DrawException
     * @throws DrawErrorException
     */
    @Transactional
    public Integer doDrawLots(String phone, Integer comp_id) throws JsonProcessingException, DrawException, DrawErrorException {
        User user = userDao.findByPhoneEquals(phone);

        if (user == null){
            // 没有该用户时提示注册
            throw new DrawErrorException("请先注册");
        }
        TeamInfo teamInfo = user.getTeamInfo();
        DrawLots drawLots = new DrawLots();
        drawLots.setTeamId(teamInfo.getId());
        drawLots.setCompId(comp_id);

        // 判断该队是否属于该比赛的组
        GroupType groupType = teamInfo.getType_name();
        Competition comp = competitionDao.findByCompId(comp_id);
        if (groupType != null && comp != null && !groupType.getTypeId().equals(comp.getType_name().getTypeId())) {
            throw new DrawErrorException("不属于该比赛组");
        }

        // pool的id对应组比赛的id
        DrawLotsPool drawLotsPool = poolDao.findByIdEquals(comp_id);
        Date date = new Date();

        // 判断是否已经抽过签
        Optional<DrawLots> one = drawLotsDao.findOne(Example.of(drawLots));
        if (one.isPresent()) {
            //如果查询出结果说明已经抽签
            //返回之前抽的签
            return one.get().getOrderNumber();
        }

        // 判断是否开始抽签
        if (date.before(drawLotsPool.getStartTime()) || date.after(drawLotsPool.getEndTime())) {
            // 如果没开始，则报错
            // 返回时间
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String startTime = format.format(drawLotsPool.getStartTime());
            String endTime = format.format(drawLotsPool.getEndTime());
            throw new DrawErrorException(String.format("抽签时间在 %s 到 %s", startTime, endTime));
        }



        List<Integer> list1 = mapper.readValue(drawLotsPool.getPool(), List.class);
        // 判断是否还能抽签
        if (list1 == null || list1.isEmpty()) {
            // 如果被抽完了
            // 返回提示信息
            throw new DrawErrorException("抽签序号已用完，请联系现场工作人员");
        }

        // 正式抽签
        // 打乱列表
        Collections.shuffle(list1);
        // 读取第一个元素 作为结果
        Integer integer = list1.get(0);
        list1.remove(0);
        drawLotsPool.setPool(mapper.writeValueAsString(list1));
        // 保存抽签结果到数据库中
        drawLots.setOrderNumber(integer);
        drawLotsDao.save(drawLots);
        poolDao.save(drawLotsPool);
        return integer;
    }

    public void saveDrawPool(DrawLotsPool pool) {
        poolDao.save(pool);
    }

}
