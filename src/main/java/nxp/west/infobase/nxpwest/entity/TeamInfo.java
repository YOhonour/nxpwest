package nxp.west.infobase.nxpwest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "team_info")
public class TeamInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    @JsonIgnore
    private Integer id;
    @Column(name = "team_name")
    private String teamName;
    @Column(name = "team_school")
    private String schoolName;
    @Column(name = "team_members")
    private String members;

    @OneToOne(mappedBy = "teamInfo",cascade = CascadeType.REMOVE)
//    @JoinColumn(name = "team_captain_id")
    private User team_captain;

    @ManyToOne(targetEntity = GroupType.class,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "group_type_name",referencedColumnName = "type_name")
    private GroupType type_name;



    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "TeamInfo{" +
                "id=" + id +
                ", teamName='" + teamName + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", members='" + members + '\'' +
                '}';
    }

    public GroupType getType_name() {
        return type_name;
    }

    public void setType_name(GroupType type_name) {
        this.type_name = type_name;
    }

    public User getTeam_captain() {
        return team_captain;
    }

    public void setTeam_captain(User team_captain) {
        this.team_captain = team_captain;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}
