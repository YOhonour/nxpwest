package nxp.west.infobase.nxpwest.service;

import nxp.west.infobase.nxpwest.bean.Rank;
import nxp.west.infobase.nxpwest.entity.Achievement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RankService {
    @Autowired
    AchievementService achievementService;

    @CacheEvict(cacheNames = "rankList", key = "#comp_id")
    public void removeRankCache(Integer comp_id) {
    }

    @Cacheable(cacheNames = "rankList", key = "#comp_id")
    public List<Rank> getRankedList(Integer comp_id) {
        List<Achievement> list = achievementService.getAchList(comp_id);
        if (list == null || list.size() == 0) {
            return new ArrayList<>();
        }
        Achievement achievement = list.get(0);
        List<Rank> rankList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            rankList.add(new Rank(list.get(i).getTeamInfo().getSchoolName() + "-" + list.get(i).getTeamInfo().getTeamName(), list.get(i).getAchTime(), (i + 1) + "/" + list.get(i).getTeamInfo().getType_name().getTeamNumber()));
        }
        return rankList;
    }

    public Rank getMyRank(Integer comp_id, Integer team_id) {
        List<Achievement> list = achievementService.getAchList(comp_id);
        if (list == null) {
            return null;
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTeamInfo().getId().equals(team_id)) {
                return new Rank(list.get(i).getTeamInfo().getSchoolName() + "-" + list.get(i).getTeamInfo().getTeamName(), list.get(i).getAchTime(), (i + 1) + "/" + list.get(i).getTeamInfo().getType_name().getTeamNumber());
            }
        }
        return null;
    }
}
