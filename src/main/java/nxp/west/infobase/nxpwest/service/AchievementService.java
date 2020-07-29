package nxp.west.infobase.nxpwest.service;

import nxp.west.infobase.nxpwest.dao.AchievementDao;
import nxp.west.infobase.nxpwest.dao.CompetitionDao;
import nxp.west.infobase.nxpwest.dao.TeamInfoDao;
import nxp.west.infobase.nxpwest.entity.Achievement;
import nxp.west.infobase.nxpwest.entity.Competition;
import nxp.west.infobase.nxpwest.entity.TeamInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AchievementService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    AchievementDao achievementDao;

    public List<Achievement> getAchList(Integer comp_id){
        logger.info("进入Service方法查询");
        List<Achievement> list = null;
        list = achievementDao.findAllByCompIDOrderBy(1);
        return list;
    }


    @Autowired
    TeamInfoDao teamInfoDao;
    @Autowired
    CompetitionDao competitionDao;
    @CacheEvict(cacheNames = "rankList",key = "#comp_id")
    public boolean addAchievement(Integer comp_id,Achievement achievement){
        logger.info("添加成绩");
        achievementDao.save(achievement);
        return true;
    }

}
