package nxp.west.infobase.nxpwest.dao;

import nxp.west.infobase.nxpwest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserDao extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User> {
    User findByPhoneEquals(String phone);
    User findByPhoneEqualsAndPassword(String phone,String password);
    User findByIdEquals(Integer id);
}
