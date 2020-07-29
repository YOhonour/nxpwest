package nxp.west.infobase.nxpwest.dao;

import nxp.west.infobase.nxpwest.bean.Rank;
import nxp.west.infobase.nxpwest.entity.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AchievementDao extends JpaRepository<Achievement,Integer>, JpaSpecificationExecutor<Achievement> {
    @Query(value = "from Achievement where achCompetition.compId=?1 order by achTime DESC")
    List<Achievement> findAllByCompIDOrderBy(Integer comp_id);
}
