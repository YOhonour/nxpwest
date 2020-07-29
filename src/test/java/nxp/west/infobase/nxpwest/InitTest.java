package nxp.west.infobase.nxpwest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nxp.west.infobase.nxpwest.dao.*;
import nxp.west.infobase.nxpwest.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class InitTest {
    @Autowired
    DrawLotsPoolDao poolDao;
    @Autowired
    DrawLotsDao drawLotsDao;
    @Autowired
    GroupTypeDao groupTypeDao;
    @Autowired
    CompetitionDao competitionDao;
    @Autowired
    TeamInfoDao teamInfoDao;
    @Autowired
    AchievementDao achievementDao;



//    @Test
    void addGroupTypes() {
        List<GroupType> allTypes = groupTypeDao.findAll();
        for (GroupType g :
                allTypes) {
            //添加默认队伍信息
            for (int i = 0 ;i < 5; i++){
                TeamInfo teamInfo = new TeamInfo();
                teamInfo.setSchoolName("重庆邮电大学"+i+"-"+g.getTypeId());
                teamInfo.setTeamName("小飞侠"+i+"-"+g.getTypeId());
                teamInfo.setMembers("张三、李氏、测试名字、"+i);
                teamInfo.setType_name(g);
                TeamInfo save = teamInfoDao.save(teamInfo);
                System.out.println(save);
            }
            ////添加默认比赛
            Competition competition = new Competition();
            competition.setComp_name("25日测试");
            competition.setType_name(g);
            competitionDao.save(competition);
        }

    }
//    @Test//初始化抽签池
    void init() throws JsonProcessingException {
        drawLotsDao.deleteAll();
        initLotsPool();
    }
    void initLotsPool() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<GroupType> all = groupTypeDao.findAll();
        for (GroupType byTypeId :
                all) {
            List<Integer> list = new ArrayList<>();
            for (int i = 1; i <= byTypeId.getTeamNumber(); i++) {
                list.add(i);
            }
            DrawLotsPool pool = new DrawLotsPool();
            pool.setId(byTypeId.getTypeId());//比赛ID
            pool.setPool(mapper.writeValueAsString(list));
            poolDao.save(pool);//信息存入数据库
        }
    }
    @Autowired
    UserDao userDao;
//    @Test//添加默认用户
    void contextLoads() {
        User user = new User();
        user.setPassword("123456");
        user.setPhone("15086924104");
        TeamInfo t = teamInfoDao.findByIdEquals(1);
        user.setTeamInfo(t);
        try {
            User save = userDao.save(user);
            t.setTeam_captain(save);
            teamInfoDao.save(t);
        }catch (DataIntegrityViolationException e){
            System.out.println("无法添加用户");
        }
    }

}
