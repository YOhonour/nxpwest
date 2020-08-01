package nxp.west.infobase.nxpwest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nxp.west.infobase.nxpwest.dao.*;
import nxp.west.infobase.nxpwest.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@SpringBootTest
class NxpwestApplicationTests {

    @Autowired
    UserDao userDao;
    @Autowired
    GroupTypeDao groupTypeDao;
    @Autowired
    CompetitionDao competitionDao;
    @Autowired
    TeamInfoDao teamInfoDao;
    @Autowired
    AchievementDao achievementDao;

    /**
     * 查找比赛类型
     */
//    @Test
    void name1() {
//        List<GroupType> all = groupTypeDao.findAllByTypeNotNullOrderByTypeId();
//        GroupType byTypeId = groupTypeDao.findByTypeIdOrderByTypeId(2);
//        System.out.println(all);
        User byIdEquals = userDao.findByIdEquals(3);
        TeamInfo teamInfo1 = byIdEquals.getTeamInfo();

        TeamInfo teamInfo = teamInfoDao.findByTeamNameAndSchoolName("小飞侠0-1", "重庆邮电大学0-1");
        User team_captain = teamInfo.getTeam_captain();
        System.out.println(team_captain);
    }

    @Autowired
    CompetitionArrangementDao competitionArrangementDao;
//    @Test
    void name11() {
        Competition byComp_id = competitionDao.findByCompId(13);
        System.out.println(byComp_id);;
        CompetitionArrangement competitionArrangement = new CompetitionArrangement();
        competitionArrangement.setArgPlace("测试");
        competitionArrangement.setArgTimeInfo("24日 ");
        competitionArrangement.setDrawLotsTime(new Date());
        byComp_id.setCompetitionArrangement(competitionArrangement);
        competitionArrangement.setCompetition(byComp_id);
        competitionArrangementDao.save(competitionArrangement);
        competitionDao.save(byComp_id);
    }

    /**
     * 添加比赛
     */
    //@Test



    //增加成绩
    @Autowired
    DrawLotsDao drawLotsDao;

    //@Test
    void name5() throws JsonProcessingException {
        DrawLots drawLots = new DrawLots();
        drawLots.setCompId(1);
        drawLots.setOrderNumber(1);
//        drawLots.setTeamId(2);

//        drawLotsDao.myInsert(3,2,1);
        DrawLots byCompIdAndOrderNumber = drawLotsDao.findByCompIdAndOrderNumber(1, 1);
//        drawLotsDao.exists(Example.of(drawLots))

        List<Integer> list = new ArrayList<>();
        for (int i = 1;i <= 50;i++){
            list.add(i);
        }
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(list);
        System.out.println(s);
        System.out.println(drawLotsDao.exists(Example.of(drawLots)));
        String l = "[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50]";

        List list1 = mapper.readValue(l, List.class);
        System.out.println(list1.size());
        System.out.println(list1);
        Collections.shuffle(list1);
        System.out.println(list1);
    }

    @Autowired
    DrawLotsPoolDao poolDao;

    //@Test
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

    /**
     * 初始化抽签池，并抽签
     */


    @Test
    void name6() throws JsonProcessingException {

        Competition c = competitionDao.findOneME("25日测试", "双车汇车组");
        System.out.println(c.toString());

    }
}
