package nxp.west.infobase.nxpwest.entity;

import javax.persistence.*;

/**
 * 抽签实体
 */
@Entity
@Table(name = "draw_lots_result")
@IdClass(DrawLotsIdClass.class)
public class DrawLots {

    @Column(name = "team_id")
    private Integer teamId;
    @Id
    @Column(name = "comp_id")
    private Integer compId;
    @Id
    @Column(name = "order_number")
    private Integer orderNumber;

    @Override
    public String toString() {
        return "DrawLots{" +
                "teamId=" + teamId +
                ", compId=" + compId +
                ", orderNumber=" + orderNumber +
                '}';
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getCompId() {
        return compId;
    }

    public void setCompId(Integer compId) {
        this.compId = compId;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }
}

