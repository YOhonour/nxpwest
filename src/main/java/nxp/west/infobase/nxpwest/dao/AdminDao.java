package nxp.west.infobase.nxpwest.dao;

import nxp.west.infobase.nxpwest.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdminDao extends JpaRepository<Admin,String>, JpaSpecificationExecutor<Admin> {

}
