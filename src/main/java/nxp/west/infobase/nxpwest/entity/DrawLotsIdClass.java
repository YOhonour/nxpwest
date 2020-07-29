package nxp.west.infobase.nxpwest.entity;


import java.io.Serializable;
import java.util.Objects;

public class DrawLotsIdClass implements Serializable {

    private Integer compId;
    private Integer orderNumber;

    public DrawLotsIdClass() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DrawLotsIdClass that = (DrawLotsIdClass) o;
        return Objects.equals(compId, that.compId) &&
                Objects.equals(orderNumber, that.orderNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(compId, orderNumber);
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
