package nxp.west.infobase.nxpwest;

import nxp.west.infobase.nxpwest.dao.TeamInfoDao;
import nxp.west.infobase.nxpwest.entity.GroupType;
import nxp.west.infobase.nxpwest.entity.TeamInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author MaYunHao
 * @version 1.0
 * @description
 * @date 2020/7/31 14:24
 */
//@SpringBootTest
public class LoadData {
    @Autowired
    TeamInfoDao teamInfoDao;

    /**
     * 导入队伍信息
     * @throws FileNotFoundException
     */
    void loadTeam() throws FileNotFoundException {
        // 队伍信息所在路径
        Scanner scanner = new Scanner(new FileInputStream(new File("C:\\Users\\11726\\Desktop\\ai.txt")));
        while (scanner.hasNextLine()) {
            String next = scanner.nextLine();
            int i = next.indexOf(" ");
            System.out.println(i);
            if (i != -1) {
                // 学校名
                String schoolName = next.substring(0, i);
                System.out.println(schoolName);
                // 队伍名
                String teamName = next.substring(i + 1);
                System.out.println(teamName);
                TeamInfo info = new TeamInfo();
                info.setSchoolName(schoolName);
                info.setTeamName(teamName);
                // 设置组
                GroupType type = new GroupType();
                type.setType("AI 电磁组");
                type.setTypeId(5);
                info.setType_name(type);
                teamInfoDao.save(info);
            }
        }
    }
}
