package nxp.west.infobase.nxpwest.dao;

import nxp.west.infobase.nxpwest.entity.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompetitionDao extends JpaRepository<Competition,Integer>, JpaSpecificationExecutor<Competition> {
    Competition findByCompId(Integer id);//group_type_name
    @Query(value = "select * from competition where comp_name = ?1 and group_type_name = ?2",nativeQuery = true)
    Competition findOneME(String compName,String typeName);
    @Query(value = "select * from competition where group_type_name = ?1",nativeQuery = true)
    List<Competition> findAllByTypeME(String typeName);
}
