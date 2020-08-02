package nxp.west.infobase.nxpwest.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 抽签实体
 */

/**
 * 修改记录
 * 2020/8/1
 *
 * 增加字段typeId
 * 将comp_id设置为自增主键
 * 去掉双主键（去掉主键orderNumber）
 *
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


    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getCompId() {
        return compId;
    }

    public void setCompId(Integer compid) {
        this.compId = compid;
    }
}

