package nxp.west.infobase.nxpwest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nxp.west.infobase.nxpwest.dao.DrawLotsDao;
import nxp.west.infobase.nxpwest.dao.DrawLotsPoolDao;
import nxp.west.infobase.nxpwest.dao.UserDao;
import nxp.west.infobase.nxpwest.entity.DrawLots;
import nxp.west.infobase.nxpwest.entity.DrawLotsPool;
import nxp.west.infobase.nxpwest.entity.TeamInfo;
import nxp.west.infobase.nxpwest.entity.User;
import nxp.west.infobase.nxpwest.exception.DrawException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DrawLotsService {
    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    DrawLotsPoolDao poolDao;
    @Autowired
    DrawLotsDao drawLotsDao;
    @Autowired
    UserDao userDao;
    @Transactional
    public Integer doDrawLots(String phone,Integer comp_id) throws JsonProcessingException, DrawException {
        User user = userDao.findByPhoneEquals(phone);
        TeamInfo teamInfo = user.getTeamInfo();
        DrawLots drawLots = new DrawLots();
        drawLots.setTeamId(teamInfo.getId());
        drawLots.setCompId(comp_id);
        Optional<DrawLots> one = drawLotsDao.findOne(Example.of(drawLots));
        if (one.isPresent()){
            //如果查询出结果说明已经抽签
            return -1;
        }
        DrawLotsPool drawLotsPool = poolDao.findByIdEquals(comp_id);
        List<Integer> list1 = mapper.readValue(drawLotsPool.getPool(), List.class);
        Collections.shuffle(list1);
        Integer integer = list1.get(0);list1.remove(0);//获取结果
        drawLotsPool.setPool(mapper.writeValueAsString(list1));
        drawLots.setOrderNumber(integer);
        drawLotsDao.save(drawLots);
        poolDao.save(drawLotsPool);
        return integer;
    }
}
