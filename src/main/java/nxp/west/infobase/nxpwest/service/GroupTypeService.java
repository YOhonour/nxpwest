package nxp.west.infobase.nxpwest.service;

import nxp.west.infobase.nxpwest.dao.GroupTypeDao;
import nxp.west.infobase.nxpwest.entity.Competition;
import nxp.west.infobase.nxpwest.entity.GroupType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class GroupTypeService {
    @Autowired
    GroupTypeDao groupTypeDao;
    @Cacheable(cacheNames = "types",key = "1")
    public List<GroupType> getAll(){
        return groupTypeDao.findAll();
    }

    @Cacheable(cacheNames = "compByType",key = "#teamTypeName")
    public Set<Competition> findComps(String teamTypeName){
        GroupType byType = groupTypeDao.findByType(teamTypeName);

        Set<Competition> set = byType.getCompetitionSet();

        return set;
    }
    @Cacheable(cacheNames = "findGroupTypeByName",key = "#teamTypeName")
    public GroupType findGroupTypeByName(String teamTypeName){
        GroupType byType = groupTypeDao.findByType(teamTypeName);
        return byType;
    }

    public List<GroupType> findAll() {
        return groupTypeDao.findAll();
    }
}
