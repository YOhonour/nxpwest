package nxp.west.infobase.nxpwest.entity;

import javax.persistence.*;

/**
 * 成绩实体
 */
@Entity
@Table(name = "achievement")
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer achId;
    private Double achTime;


    @OneToOne(targetEntity = Competition.class,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "ach_competition_id",referencedColumnName = "comp_id")
    private Competition achCompetition;
    @OneToOne(targetEntity = TeamInfo.class,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "ach_teamInfo_id",referencedColumnName = "team_id")
    private TeamInfo teamInfo;

    public Competition getAchCompetition() {
        return achCompetition;
    }

    public void setAchCompetition(Competition achCompetition) {
        this.achCompetition = achCompetition;
    }

    public TeamInfo getTeamInfo() {
        return teamInfo;
    }

    public void setTeamInfo(TeamInfo teamInfo) {
        this.teamInfo = teamInfo;
    }

    @Override
    public String toString() {
        return "Achievement{" +
                "ach_id=" + achId +
                ", ach_time=" + achTime +
                '}';
    }

    public Integer getAchId() {
        return achId;
    }

    public void setAchId(Integer ach_id) {
        this.achId = ach_id;
    }

    public Double getAchTime() {
        return achTime;
    }

    public void setAchTime(Double ach_time) {
        this.achTime = ach_time;
    }
}
