package sg.edu.nus.se26pt03.photolearn.DAL;

import java.util.Date;

/**
 * Created by chen ping on 3/10/2018.
 */

public class QuizTitleDAO {
    private int Id;
    private String Title;
    private int SessionId;
    private int CreatedBy;
    private Date Timestamp;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getSessionId() {
        return SessionId;
    }

    public void setSessionId(int sessionId) {
        SessionId = sessionId;
    }

    public int getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(int createdBy) {
        CreatedBy = createdBy;
    }

    public Date getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(Date timestamp) {
        Timestamp = timestamp;
    }
}
