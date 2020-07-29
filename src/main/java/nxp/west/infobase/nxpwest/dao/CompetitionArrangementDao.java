package nxp.west.infobase.nxpwest.dao;

import nxp.west.infobase.nxpwest.entity.CompetitionArrangement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CompetitionArrangementDao extends JpaRepository<CompetitionArrangement,Integer>, JpaSpecificationExecutor<CompetitionArrangement> {
//    public CompetitionArrangement findByArg_idExists();
}
