package nxp.west.infobase.nxpwest.dao;

import nxp.west.infobase.nxpwest.entity.DrawLots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface DrawLotsDao extends JpaRepository<DrawLots,Integer>, JpaSpecificationExecutor<DrawLots> {
//    @Query(value = "INSERT INTO draw_lots_result ( team_id,comp_id,order_number) VALUES( ?1,?2,?3)",nativeQuery = true)
//    int myInsert(Integer team_id,Integer comp_id,Integer order_number);
    DrawLots findByCompIdAndOrderNumber(Integer comp_id,Integer order_number);


}
