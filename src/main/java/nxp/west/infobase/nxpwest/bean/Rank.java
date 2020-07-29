package nxp.west.infobase.nxpwest.bean;

import nxp.west.infobase.nxpwest.entity.Achievement;

import javax.persistence.Entity;

//@Entity
public class Rank {
    private String teamInfo;
    private Double time;
    private String rank;

    public Rank(String teamInfo, Double time, String rank) {
        this.teamInfo = teamInfo;
        this.time = time;
        this.rank = rank;
    }


    @Override
    public String toString() {
        return "Rank{" +
                "teamInfo='" + teamInfo + '\'' +
                ", time=" + time +
                ", rank='" + rank + '\'' +
                '}';
    }

    public String getTeamInfo() {
        return teamInfo;
    }

    public void setTeamInfo(String teamInfo) {
        this.teamInfo = teamInfo;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
