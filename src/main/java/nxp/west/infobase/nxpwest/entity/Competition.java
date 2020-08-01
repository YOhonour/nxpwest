package nxp.west.infobase.nxpwest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * 比赛表。每次比赛有对应的分组类型、比赛表
 */
@Entity
public class Competition {
    /**
     * 比赛id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comp_id")
    @JsonIgnore
    private Integer compId;

    @Column(name = "comp_name")
    private String compName;

    @OneToOne(targetEntity = CompetitionArrangement.class,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "arg_id",referencedColumnName = "arg_id")
    private CompetitionArrangement competitionArrangement;

    @ManyToOne(targetEntity = GroupType.class,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "group_type_name",referencedColumnName = "type_name")
    private GroupType type_name;

    public CompetitionArrangement getCompetitionArrangement() {
        return competitionArrangement;
    }

    public void setCompetitionArrangement(CompetitionArrangement competitionArrangement) {
        this.competitionArrangement = competitionArrangement;
    }

    public GroupType getType_name() {
        return type_name;
    }

    public void setType_name(GroupType type) {
        this.type_name = type;
    }

    @Override
    public String toString() {
        return "Competition{" +
                "compId=" + compId +
                ", compName='" + compName + '\'' +
                ", competitionArrangement=" + competitionArrangement +
                '}';
    }

    public Integer getComp_id() {
        return compId;
    }

    public void setComp_id(Integer comp_id) {
        this.compId = comp_id;
    }

    public String getComp_name() {
        return compName;
    }

    public void setComp_name(String comp_name) {
        this.compName = comp_name;
    }
}
