package sg.edu.nus.se26pt03.photolearn.DAL;

import com.google.firebase.database.Exclude;

import java.util.Date;

/**
 * Created by part time team 3  on 12/3/18.
 */

public class BaseDAO {
    private String Id;
    private long Timestamp;
    private String CreatedBy;
    @Exclude
    public String getId() {
        return Id;
    }
    @Exclude
    public void setId(String id) {
        Id = id;
    }

    public long getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(long timestamp) {
        Timestamp = timestamp;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }
}
