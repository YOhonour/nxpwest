package nxp.west.infobase.nxpwest.service;

import nxp.west.infobase.nxpwest.dao.TeamInfoDao;
import nxp.west.infobase.nxpwest.entity.TeamInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class TeamInfoService {
    @Autowired
    TeamInfoDao teamInfoDao;
    public boolean isExistSchool(String school){
        TeamInfo teamInfo = new TeamInfo();
        teamInfo.setSchoolName(school);
        return teamInfoDao.exists(Example.of(teamInfo));
    }
    public boolean isExistTeamName(String teamName){
        TeamInfo teamInfo = new TeamInfo();
        teamInfo.setTeamName(teamName);
        return teamInfoDao.exists(Example.of(teamInfo));
    }
    public boolean isExist(TeamInfo teamInfo){
        return teamInfoDao.exists(Example.of(teamInfo));
    }

    public TeamInfo find( String schoolName,String teamName){
        return teamInfoDao.findByTeamNameAndSchoolName(teamName, schoolName);
    }
}
