package nxp.west.infobase.nxpwest.entity;

import javax.persistence.*;

@Entity
@Table(name = "draw_lots_pool")
public class DrawLotsPool {
    @Id
    Integer id;
    @Column(name = "pool")
    String pool;

    @Override
    public String toString() {
        return "DrawLotsPool{" +
                "id=" + id +
                ", pool='" + pool + '\'' +
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
}
