package nxp.west.infobase.nxpwest.dao;

import nxp.west.infobase.nxpwest.entity.GroupType;
import nxp.west.infobase.nxpwest.entity.TeamInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamInfoDao extends JpaRepository<TeamInfo,Integer>, JpaSpecificationExecutor<TeamInfo> {
    @Query(value = "select * from team_info where team_name = ?1 and team_school=?2 and group_type_name = ?3 limit 1",nativeQuery = true)
    TeamInfo findByTeamNameAndSchoolNameAndType_nameByme(String teamName, String schoolName, String type_name);
    TeamInfo findByIdEquals(Integer id);
    TeamInfo findByTeamNameAndSchoolName(String teamName, String schoolName);
    @Query(value = "select * from team_info where group_type_name = ?1",nativeQuery = true)
    List<TeamInfo> findAllByType_nameEquals(String type_name);
}
