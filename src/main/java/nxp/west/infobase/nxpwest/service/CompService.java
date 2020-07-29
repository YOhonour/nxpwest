package nxp.west.infobase.nxpwest.service;

import nxp.west.infobase.nxpwest.dao.CompetitionDao;
import nxp.west.infobase.nxpwest.entity.Competition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompService {
    @Autowired
    CompetitionDao competitionDao;
    @Cacheable(cacheNames = "comp",key = "#comp_id")
    public Competition getByID(Integer comp_id){
        return competitionDao.findByCompId(comp_id);
    }
//    //获取分类下所有比赛
//    @Cacheable(cacheNames = "compByType",key = "#teamTypeName")
//    public List<Competition> getCompSByClassName(String teamTypeName){
//        List<Competition> allByTypeME = competitionDao.findAllByTypeME(teamTypeName);
//        return allByTypeME;
//    }
    @CacheEvict(cacheNames = "compByType",key = "#teamTypeName")
    public boolean removeCompCaches(String teamTypeName){
        return true;
    }
}
