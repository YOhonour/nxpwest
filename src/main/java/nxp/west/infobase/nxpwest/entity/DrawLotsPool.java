package nxp.west.infobase.nxpwest.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "draw_lots_pool")
public class DrawLotsPool {
    @Id
    Integer id;
    @Column(name = "pool")
    String pool;

    /**
     * 抽签开始时间
     */
    @Column(name = "start_time")
    @JsonFormat(timezone = "GMT+8", pattern ="yyyy-MM-dd HH:mm")
    Date startTime;

    /**
     * 抽签结束时间
     */
    @Column(name = "end_time")
    @JsonFormat(timezone = "GMT+8", pattern ="yyyy-MM-dd HH:mm")
    Date endTime;


    @Override
    public String toString() {
        return "DrawLotsPool{" +
                "id=" + id +
                ", pool='" + pool + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPool() {
        return pool;
    }

    public void setPool(String pool) {
        this.pool = pool;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
