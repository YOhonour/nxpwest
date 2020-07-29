package nxp.west.infobase.nxpwest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * 用户表，用户和团队实体一对一
 */
@Entity
@Table(name = "user",uniqueConstraints = {@UniqueConstraint(columnNames = {"user_phone"})})
public class User {
    @Column(name = "user_phone")
    private String phone;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    @JsonIgnore
    private String password;

    //团队信息 一对一关系
    @OneToOne(targetEntity = TeamInfo.class,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "team_id",referencedColumnName = "team_id")
    @JsonIgnore
    TeamInfo teamInfo;

    public TeamInfo getTeamInfo() {
        return teamInfo;
    }

    public void setTeamInfo(TeamInfo teamInfo) {
        this.teamInfo = teamInfo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
