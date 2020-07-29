package nxp.west.infobase.nxpwest.dao;

import nxp.west.infobase.nxpwest.entity.DrawLotsPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DrawLotsPoolDao extends JpaRepository<DrawLotsPool,Integer>, JpaSpecificationExecutor<DrawLotsPool> {
    DrawLotsPool findByIdEquals(Integer id);
}
