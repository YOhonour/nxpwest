package nxp.west.infobase.nxpwest.controller;

import com.aliyuncs.exceptions.ClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import nxp.west.infobase.nxpwest.annotation.UserLoginToken;
import nxp.west.infobase.nxpwest.bean.Rank;
import nxp.west.infobase.nxpwest.bean.ResultBean;
import nxp.west.infobase.nxpwest.dao.UserDao;
import nxp.west.infobase.nxpwest.entity.Competition;
import nxp.west.infobase.nxpwest.entity.GroupType;
import nxp.west.infobase.nxpwest.entity.TeamInfo;
import nxp.west.infobase.nxpwest.entity.User;
import nxp.west.infobase.nxpwest.exception.DrawErrorException;
import nxp.west.infobase.nxpwest.exception.DrawException;
import nxp.west.infobase.nxpwest.service.*;
import nxp.west.infobase.nxpwest.utils.SendSmsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@Api(tags = "登陆用户相关接口")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    UserDao userDao;
    @Autowired
    TeamInfoService teamInfoService;


    @GetMapping("test")
    @UserLoginToken
    public void t(String id,Integer longs){
        logger.error("访问成功");
        return ;
    }



    @GetMapping("/login")
    @ApiOperation(value = "登陆",notes = "手机号和密码,返回示例：{<br>" +
            "\"code\": 0,<br>" +
            "\"message\": \"success\",<br>" +
            "\"data\": {<br>" +
            "\"team_info\": {<br>" +
            "\"teamName\": \"小飞侠\",<br>" +
            "\"schoolName\": \"重庆邮电大学\",<br>" +
            "\"members\": \"吕进豪\",<br>" +
            "\"team_captain\": {<br>" +
            "\"phone\": \"15086924104\",<br>" +
            "\"id\": 6<br>" +
            "},<br>" +
            "\"type_name\": {<br>" +
            "\"type\": \"无线节能组\",<br>" +
            "\"teamNumber\": 50<br>" +
            "}<br>" +
            "}<br>" +
            "}<br>" +
            "}")
    public ResultBean login(String phone, String password, HttpServletRequest request){
        User user = userDao.findByPhoneEqualsAndPassword(phone, password);
        if (user == null){
            return ResultBean.error(-1,"密码错误");
        }
        HttpSession session = request.getSession(true);
        session.setAttribute("user",user.getId());
        Map<String , TeamInfo> map = new HashMap<>();
        map.put("team_info",user.getTeamInfo());
        return ResultBean.success(map);
    }
    @PostMapping("/signup")
    @ApiOperation(value = "注册",notes = "手机号、密码、验证码、学校名、队伍名")
    public ResultBean signup(String phone, String password,String code,String school,String teamName, HttpServletRequest request){
        if (!verificationService.isRightCode(phone, code)){
            return ResultBean.error(-1,"验证码错误");
        }
        TeamInfo teamInfo = teamInfoService.find(school, teamName);
        if (teamInfo == null){
            return ResultBean.error(-1,"队伍不存在");
        }

        if (teamInfo.getTeam_captain() != null){
            return ResultBean.error(-1,"队伍已注册");
        }

        User user = new User();
        user.setPhone(phone);
        user.setPassword(password);
        user.setTeamInfo(teamInfo);
        teamInfo.setTeam_captain(user);
        User save;
        try {
            save = userDao.save(user);
        }catch (DataIntegrityViolationException e){
            return ResultBean.error(-1,"用户已存在");
        }
        verificationService.erase(phone);
        return ResultBean.success();
    }
    @PostMapping("/reset")
    @ApiOperation(value = "重置密码")
    public ResultBean resetPassword(String phone, String newPassword,String code){
        if (!verificationService.isRightCode(phone, code)){
            return ResultBean.error(-1,"验证码错误");
        }
        User user = userDao.findByPhoneEquals(phone);
        user.setPassword(newPassword);
        userDao.save(user);
        verificationService.erase(phone);
        return ResultBean.success();
    }

    @Autowired
    VerificationService verificationService;
    @Autowired
    SendSmsUtil util;

    @PostMapping("/code")
    @ApiOperation(value = "获取验证码",notes = "手机号和密码")
    public ResultBean login(String phone) throws ClientException {
        String code = verificationService.getVerificationCode(phone);
        util.sendSms(code,phone);
        return ResultBean.success();
    }
    @Autowired
    GroupTypeService groupTypeService;
    //查看所有分类
    @GetMapping("/types")
    @ApiOperation(value = "查看所有分类")
    public ResultBean getAllType(){
        List<GroupType> all = groupTypeService.getAll();
        Map<String,List> map = new HashMap<>();
        map.put("types",all);
        return ResultBean.success(map);
    }
    //根据分类查看比赛安排
    @GetMapping("/comps/{type_name}")
    @ApiOperation(value = "根据分类名查看分类下所有比赛安排",notes = "{<br>" +
            "\"code\": 0,<br>" +
            "\"message\": \"success\",<br>" +
            "\"data\": {<br>" +
            "\"competitions\": [<br>" +
            "{<br>" +
            "\"competitionArrangement\": {<br>" +
            "\"argPlace\": \"双车组场地2\",<br>" +
            "\"argTimeInfo\": \"24日 \",时间<br>" +
            "\"drawLotsTime\": \"2020-04-2 07:34\" 抽签开始时间,格式：yyyy-MM-dd HH:mm<br>" +
            "},<br>" +
            "\"type_name\": {<br>" +
            "\"type\": \"无线节能组\",<br>" +
            "\"teamNumber\": 50<br>" +
            "},<br>" +
            "\"comp_id\": 1,比赛ID，抽签需要<br>" +
            "\"comp_name\": \"4-2\"<br>" +
            "},<br>" +
            "{<br>" +
            "\"competitionArrangement\": {<br>" +
            "\"argPlace\": \"天空之城\",<br>" +
            "\"argTimeInfo\": \"24日 \",<br>" +
            "\"drawLotsTime\": \"2020-04-12 07:34\"<br>" +
            "},<br>" +
            "\"type_name\": {<br>" +
            "\"type\": \"无线节能组\",<br>" +
            "\"teamNumber\": 50<br>" +
            "},<br>" +
            "\"comp_id\": 2,<br>" +
            "\"comp_name\": \"25日测试\"<br>" +
            "}<br>" +
            "]<br>" +
            "}<br>" +
            "}")
    public ResultBean getCompInfo(@PathVariable String type_name){
        Set<Competition> set = groupTypeService.findComps(type_name);
        Map<String,Set> map = new HashMap<>();
        map.put("competitions",set);
        return ResultBean.success(map);
    }

    @ApiOperation("获取所有分类名及对应的比赛")
    @GetMapping("/comps")
    public ResultBean getComps(){
        List<GroupType> all = groupTypeService.getAll();
        ArrayList<Map> list = new ArrayList<>();
        for (GroupType type : all) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("comps",type.getCompetitionSet());
            data.put("type",type.getType());
            list.add(data);
        }
        HashMap<String, List> map = new HashMap<>();
        map.put("data",list);
        return ResultBean.success(map);
    }

    @Autowired
    DrawLotsService drawLotsService;
    //抽签
    @GetMapping("/draw_lots")
    @UserLoginToken
    @ApiOperation(value = "抽签",notes = "参数：比赛ID、手机号")
    public ResultBean doDrawLots(Integer comp_id,String phone) throws JsonProcessingException, DrawException, DrawErrorException {
        Integer integer = drawLotsService.doDrawLots(phone, comp_id);
        if (integer == -1){
            return ResultBean.error(-1,"请勿重复抽签");
        }
        Map<String,Integer> map = new HashMap<>();
        map.put("draw_lots_result",integer);
        return ResultBean.success(map);
    }
//    UserDao userDao;
    @UserLoginToken
    @GetMapping("/my/{comp_id}")
    @ApiOperation(value = "获取用户比赛信息",notes = "参数：比赛ID、手机号")
    public ResultBean myAch(@PathVariable Integer comp_id,HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Integer userId = (Integer)session.getAttribute("user");
        User user = userDao.findByIdEquals(userId);
        Integer teamId = user.getTeamInfo().getId();
        Rank myRank = rankService.getMyRank(comp_id, teamId);
        Competition competition = compService.getByID(comp_id);
        Map<String,Object> map = new HashMap<>();
//        map.put("rank",myRank);
        map.put("teamName",user.getTeamInfo().getTeamName());
        map.put("rank",myRank==null ?null: myRank.getRank());
        map.put("type",competition.getType_name().getType());
        map.put("compName",competition.getComp_name());
        map.put("time",myRank==null ?null:myRank.getTime());

        return ResultBean.success(map);
    }
    //根据比赛ID查询所有成绩
    @Autowired
    AchievementService achievementService;
    @Autowired
    RankService rankService;
    @Autowired
    CompService compService;
    @GetMapping("/ranked/{comp_id}")
    public ResultBean getAchiByComp(@PathVariable Integer comp_id){
        List<Rank> rankedList = rankService.getRankedList(comp_id);
        Map<String,List> map = new HashMap<>();
        map.put("ranked",rankedList);
        return ResultBean.success(map);
    }


}
