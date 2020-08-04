package nxp.west.infobase.nxpwest;


import nxp.west.infobase.nxpwest.dao.CompetitionArrangementDao;
import nxp.west.infobase.nxpwest.dao.CompetitionDao;
import nxp.west.infobase.nxpwest.entity.Competition;
import nxp.west.infobase.nxpwest.entity.CompetitionArrangement;
import nxp.west.infobase.nxpwest.entity.GroupType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @author MaYunHao
 * @version 1.0
 * @description
 * @date 2020/8/4 10:29
 */
@SpringBootTest
public class CompTest {
    @Autowired
    CompetitionDao competitionDao;

    @Autowired
    CompetitionArrangementDao competitionArrangementDao;



    /**
     * 保存比赛测试
     */
    @Test
    void saveTest() {
        Competition competition = new Competition();
        competition.setComp_name("8月4日测试");
        GroupType groupType = new GroupType();
        groupType.setTypeId(2);
        groupType.setType("四轮光电组");
        competition.setType_name(groupType);
        CompetitionArrangement arrangement = new CompetitionArrangement();
        arrangement.setArgPlace("研究中心");
        arrangement.setDrawLotsTime(new Date());
        arrangement.setArgTimeInfo("5.20 9:00 - 5.21 16:00");
        CompetitionArrangement saved = competitionArrangementDao.save(arrangement);
        competition.setCompetitionArrangement(saved);
        // 需要先保存关联的对象
        competitionDao.save(competition);
    }
}
