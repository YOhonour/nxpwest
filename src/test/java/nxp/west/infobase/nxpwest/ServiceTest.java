package nxp.west.infobase.nxpwest;

import com.fasterxml.jackson.core.JsonProcessingException;
import nxp.west.infobase.nxpwest.bean.Rank;
import nxp.west.infobase.nxpwest.dao.AchievementDao;
import nxp.west.infobase.nxpwest.entity.Achievement;
import nxp.west.infobase.nxpwest.exception.DrawErrorException;
import nxp.west.infobase.nxpwest.exception.DrawException;
import nxp.west.infobase.nxpwest.service.AchievementService;
import nxp.west.infobase.nxpwest.service.DrawLotsService;
import nxp.west.infobase.nxpwest.service.RankService;
import nxp.west.infobase.nxpwest.service.VerificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ServiceTest {
    @Autowired
    DrawLotsService drawLotsService;
    @Autowired
    VerificationService verificationService;
//    @Test
    void name1() throws JsonProcessingException, DrawException, DrawErrorException {
        Integer integer = drawLotsService.doDrawLots("15086924104", 2);
        System.out.println(integer);
    }
    @Autowired
    ConcurrentMapCacheManager concurrentMapCacheManager;
//    @Test
    void name2() throws InterruptedException {
        Cache code1 = concurrentMapCacheManager.getCache("code");
        if (code1 != null){
            Cache.ValueWrapper valueWrapper1 = code1.get("15086924104");
//            String  o = (String) valueWrapper1.get();
        }
        String verificationCode1 = verificationService.getVerificationCode("15086924104");
        System.out.println(verificationCode1);
        System.out.println(verificationService.isRightCode("15086924104",verificationCode1));

        String verificationCode = verificationService.getVerificationCode("15086924104");
        System.out.println(verificationCode);
    }
    @Autowired
    DataSource dataSource;
    @Autowired
    AchievementDao achievementDao;
//    @Test
    void name3() throws SQLException {
//        Integer rank = achievementDao.findByTimeWithRank(6, 1);
//        System.out.println(rank);
//        String  sql = "SET @currank \\:= 0\\;\n" +
//                "SELECT ach_id,ach_time,ach_team_info_id,ach_competition_id,\n" +
//                "@currank \\:= @currank + 1 AS rank\n" +
//                "FROM achievement \n" +
//                "ORDER BY ach_time;";
//        Connection connection = dataSource.getConnection();
//        System.out.println(connection);
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery(sql);
//        System.out.println(resultSet);
        List<Achievement> list = achievementDao.findAllByCompIDOrderBy(1);
        Achievement achievement = list.get(0);
        List<Rank> rankList = new ArrayList<>();
        for (int i = 0;i < list.size();i ++){
            rankList.add(new Rank(list.get(i).getTeamInfo().getSchoolName()+list.get(i).getTeamInfo().getTeamName(),list.get(i).getAchTime(),(i+1)+"/"+list.get(i).getTeamInfo().getType_name().getTeamNumber()));
            System.out.println(rankList.get(i));
        }
//        System.out.println(rankList);
    }
    @Autowired
    AchievementService achievementService;
    @Autowired
    RankService rankService;
    @Test
    void name6() throws InterruptedException {
        List<Rank> rankedList = rankService.getRankedList(1);
        System.out.println(rankedList);
        Thread.sleep(2);
        List<Rank> rankedList1 = rankService.getRankedList(1);
        System.out.println(rankedList1);
    }
}
