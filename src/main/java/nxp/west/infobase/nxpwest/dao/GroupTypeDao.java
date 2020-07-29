package nxp.west.infobase.nxpwest.dao;

import nxp.west.infobase.nxpwest.entity.GroupType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface GroupTypeDao extends JpaRepository<GroupType,Integer>, JpaSpecificationExecutor<GroupType> {
    public GroupType findByTypeIdOrderByTypeId(Integer typeId);
    public List<GroupType> findAllByTypeNotNullOrderByTypeId();
    public GroupType findByTypeId(Integer type_id);
    GroupType findByType(String type);
}
