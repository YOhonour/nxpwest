package nxp.west.infobase.nxpwest.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comp_arrangement")
public class CompetitionArrangement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "arg_id")
    @JsonIgnore
    private Integer argId;
    @Column(name = "arg_place")
    private String argPlace;
    @Column(name = "arg_time_info")
    private String argTimeInfo;
    @Column(name = "arg_draw_lots_time")
    @JsonFormat(timezone = "GMT+8", pattern ="yyyy-MM-dd HH:mm")
    private Date drawLotsTime;

    @OneToOne(targetEntity = Competition.class,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "arg_comp_id",referencedColumnName = "comp_id")
    @JsonIgnore
    private Competition competition;

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    @Override
    public String toString() {
        return "CompetitionArrangement{" +
                "argId=" + argId +
                ", argPlace='" + argPlace + '\'' +
                ", argTimeInfo='" + argTimeInfo + '\'' +
                ", drawLotsTime=" + drawLotsTime +
                '}';
    }

    public Date getDrawLotsTime() {
        return drawLotsTime;
    }

    public void setDrawLotsTime(Date drawLotsTime) {
        this.drawLotsTime = drawLotsTime;
    }

    public Integer getArgId() {
        return argId;
    }

    public void setArgId(Integer argId) {
        this.argId = argId;
    }

    public String getArgPlace() {
        return argPlace;
    }

    public void setArgPlace(String argPlace) {
        this.argPlace = argPlace;
    }

    public String getArgTimeInfo() {
        return argTimeInfo;
    }

    public void setArgTimeInfo(String argTimeInfo) {
        this.argTimeInfo = argTimeInfo;
    }
}
