package nxp.west.infobase.nxpwest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "group_type")
public class GroupType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    @JsonIgnore
    private Integer typeId;
    @Column(name = "type_name")
    private String type;
    @Column(name = "type_team_number")
    private Integer teamNumber;//各个分类的参赛队伍个数

    //放弃主键维护权
    @OneToMany(mappedBy = "type_name",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Competition> competitionSet;

    public Set<Competition> getCompetitionSet() {
        return competitionSet;
    }

    public void setCompetitionSet(Set<Competition> competitionSet) {
        this.competitionSet = competitionSet;
    }

    @Override
    public String toString() {
        return "GroupType{" +
                "typeId=" + typeId +
                ", type='" + type + '\'' +
                ", teamNumber=" + teamNumber +
                '}';
    }

    public Integer getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(Integer teamNumber) {
        this.teamNumber = teamNumber;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
