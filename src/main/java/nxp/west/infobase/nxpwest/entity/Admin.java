package nxp.west.infobase.nxpwest.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Admin {
    @Id
    private String admin;
    private String password;

    @Override
    public String toString() {
        return "Admin{" +
                "admin='" + admin + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
