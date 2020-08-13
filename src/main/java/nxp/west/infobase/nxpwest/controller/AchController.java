package nxp.west.infobase.nxpwest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import nxp.west.infobase.nxpwest.annotation.Admin;
import nxp.west.infobase.nxpwest.bean.ResultBean;
import nxp.west.infobase.nxpwest.dao.*;
import nxp.west.infobase.nxpwest.entity.*;
import nxp.west.infobase.nxpwest.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@Api(value = "管理员接口", tags = "管理员接口")
public class AchController {
    @Autowired
    AchievementDao achievementDao;
    @Autowired
    CompetitionDao competitionDao;
    @Autowired
    TeamInfoDao teamInfoDao;
    @Autowired
    GroupTypeService groupTypeService;
    @Autowired
    AchievementService achievementService;
    @Autowired
    RankService rankService;

    //增加成绩 schoolName
    @PostMapping("/addScore")
    @Admin
    @ApiOperation(value = "增加成绩", notes = "学校名、队伍名、队伍分类名、比赛名、用时(Double)")
    public ResultBean addA(String schoolName, String teamName, String teamTypeName, String compName, Double scoreTime) {
        List<GroupType> all = groupTypeService.getAll();
        GroupType groupType = null;
        for (GroupType g : all) {
            if (g.getType().equals(teamTypeName)) {
                groupType = g;
                break;
            }
        }
        if (groupType == null) {
            return ResultBean.error(-1, "队伍类型不存在！");
        }
        final GroupType t = groupType;

        Competition competition = competitionDao.findOneME(compName, teamTypeName);
        TeamInfo teamInfo = teamInfoDao.findByTeamNameAndSchoolNameAndType_nameByme(teamName, schoolName, teamTypeName);
        if (competition == null || teamInfo == null) {
            return ResultBean.error(-1, "队伍不存在或比赛不存在！");
        }
        Achievement achievement = new Achievement();
        achievement.setAchCompetition(competition);
        achievement.setTeamInfo(teamInfo);
        achievement.setAchTime(scoreTime);
        achievementService.addAchievement(competition.getComp_id(), achievement);
        rankService.removeRankCache(competition.getComp_id());
        return ResultBean.success();
    }

    @Autowired
    CompetitionArrangementDao competitionArrangementDao;
    @Autowired
    CompService compService;

    @PostMapping("/getAllTeamByType") //
    @ApiOperation(value = "获取分类下所有队伍", notes = "队伍分类名")
    @Admin
    public ResultBean getAllTeamByType(String teamTypeName) {
        GroupType typeByName = groupTypeService.findGroupTypeByName(teamTypeName);
        List<TeamInfo> all = teamInfoDao.findAllByType_nameEquals(teamTypeName);
        Map<String, List> map = new HashMap<>();
        map.put("allTeamByType", all);
        return ResultBean.success(map);
    }

    @PostMapping("/addTeam")
    @ApiOperation(value = "增加队 //伍", notes = "队伍分类名、比赛名、场地、比赛时间、抽签截止时间")
    @Admin
    public ResultBean addTeam(String teamName, String schoolName, String members, String teamTypeName) {
        GroupType typeByName = groupTypeService.findGroupTypeByName(teamTypeName);
        TeamInfo teamInfo = new TeamInfo();
        teamInfo.setSchoolName(schoolName);
        teamInfo.setTeamName(teamName);
        teamInfo.setMembers(members);
        teamInfo.setType_name(typeByName);
        teamInfoDao.save(teamInfo);
        return ResultBean.success();
    }

    //增加比赛安排 yyyy-MM-dd HH:mm
    @PostMapping("/addComp") //
    @ApiOperation(value = "增加比赛", notes = "队伍分类名、比赛名、场地、比赛时间、抽签截止时间")
    @Admin
    public ResultBean addARR(String teamTypeName, String compName, String place, String timeInfo, Date drawLotsTime) {
        Competition competition = new Competition();
        competition.setComp_name(compName);
        List<GroupType> all = groupTypeService.getAll();
        GroupType groupType = null;
        for (GroupType g : all) {
            if (g.getType().equals(teamTypeName)) {
                groupType = g;
                break;
            }
        }
        if (groupType == null) {
            return ResultBean.error(-1, "队伍类型不存在！");
        }
        competition.setType_name(groupType);

        CompetitionArrangement competitionArrangement = new CompetitionArrangement();
        competitionArrangement.setDrawLotsTime(drawLotsTime);
        competitionArrangement.setArgPlace(place);
        competitionArrangement.setArgTimeInfo(timeInfo);

        compService.save(competitionArrangement,competition);
//        competitionArrangement.setCompetition(save);

        return ResultBean.success();
    }


    @Autowired
    DrawLotsService drawLotsService;

    /**
     * 初始化抽签池
     */
    @PostMapping("/pool")
    @ApiOperation(value = "初始化抽签池", notes = "比赛id 比赛队伍数量 抽签开始时间 抽签结束时间")
    @Admin
    public ResultBean saveDrawPool(Integer compId, Integer teamNumber, Date startTime, Date endTime) throws JsonProcessingException {
        DrawLotsPool pool = new DrawLotsPool();
        pool.setId(compId);
        List<Integer> list = getNumbers(1, teamNumber);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(list);
        pool.setPool(s);
        pool.setEndTime(endTime);
        pool.setStartTime(startTime);
        drawLotsService.saveDrawPool(pool);
        return ResultBean.success();
    }


    private List<Integer> getNumbers(int start, int end) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            list.add(i);
        }
        return list;
    }

    /**
     * 清楚所有缓存
     * @return
     */
    @DeleteMapping("/caches")
    @Caching(evict = {
            //成绩是直接导入数据库，所以手动清除缓存
            @CacheEvict(value = "rankList",allEntries = true),
            //比赛
            @CacheEvict(value = "comp",allEntries = true),
            //比赛
            @CacheEvict(value = "types",key = "1")
    })
    @Admin
    public ResultBean removeCaches(){
        return ResultBean.success();
    }


    @GetMapping("/tt")
    void tt(String name, String age, String mapJson, HttpServletRequest request) {
        Enumeration<String> attributeNames = request.getAttributeNames();
        System.out.println(name + ":" + age);
        System.out.println(mapJson);
    }

    @Autowired
    AdminDao adminDao;

    @GetMapping("/admin/login")
    public ResultBean login(String username, String password, HttpServletRequest request){
        nxp.west.infobase.nxpwest.entity.Admin admin = adminDao.findAdminByAdminAndPassword(username, password);
        if (admin == null){
            return ResultBean.error(-1,"密码错误");
        }
        HttpSession session = request.getSession(true);
        session.setAttribute("admin",admin.getAdmin());
        return ResultBean.success();
    }
}
