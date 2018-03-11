package sg.edu.nus.se26pt03.photolearn.DAL;

import java.util.Date;

/**
 * Created by chen ping on 3/10/2018.
 */

public class UserDAO {
    private int Id;
    private String LoginId;
    private String LoginSource;
    private Date LastLoginDate;
    private Date Timestamp;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getLoginId() {
        return LoginId;
    }

    public void setLoginId(String loginId) {
        LoginId = loginId;
    }

    public String getLoginSource() {
        return LoginSource;
    }

    public void setLoginSource(String loginSource) {
        LoginSource = loginSource;
    }

    public Date getLastLoginDate() {
        return LastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        LastLoginDate = lastLoginDate;
    }

    public Date getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(Date timestamp) {
        Timestamp = timestamp;
    }
}
