package sg.edu.nus.se26pt03.photolearn.DAL;

import com.google.firebase.database.Exclude;

import java.util.Date;

/**
 * Created by MyatMin on 12/3/18.
 */

public class BaseDAO {
    private String Id;
    private Date Timestamp;
    private String CreatedBy;
    @Exclude
    public String getId() {
        return Id;
    }
    @Exclude
    public void setId(String id) {
        Id = id;
    }

    public Date getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(Date timestamp) {
        Timestamp = timestamp;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }
}
