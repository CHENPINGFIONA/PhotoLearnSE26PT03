package sg.edu.nus.se26pt03.photolearn.DAL;

import java.util.Date;

import sg.edu.nus.se26pt03.photolearn.utility.DateConversionHelper;

/**
 * Created by part time team 3  on 3/10/2018.
 * Restructured by part time team 3  on 12/3/2018.
 */

public class UserDAO extends BaseDAO {
    private String LoginId;
    private String LoginSource;
    private Date LastLoginDate;
    private String Timestamp;

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
}
