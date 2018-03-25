package sg.edu.nus.se26pt03.photolearn.BAL;

import java.util.Date;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.service.LearningSessionService;
import sg.edu.nus.se26pt03.photolearn.service.ServiceCallback;

/**
 * Created by chen ping on 7/3/2018.
 */

public abstract class User {
    private String id;
    private String loginId;
    private String loginSource;
    private Date lastLoginDate;
    private Date timestamp;

    protected transient LearningSessionService learningSessionService = new LearningSessionService();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginSource() {
        return loginSource;
    }

    public void setLoginSource(String loginSource) {
        this.loginSource = loginSource;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void SignIn() {

    }

    public void SignOut() {

    }

    public abstract void getLearningSessions(ServiceCallback<List<LearningSession>> callback);

}
