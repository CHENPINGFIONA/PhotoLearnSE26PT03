package sg.edu.nus.se26pt03.photolearn.DAL;

import com.google.firebase.database.Exclude;

/**
 * Created by MyatMin on 12/3/18.
 */

public class BaseDAO {
    private String Id;
    private String Timestamp;
    private String CreatedBy;
    @Exclude
    public String getId() {
        return Id;
    }
    @Exclude
    public void setId(String id) {
        Id = id;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }
}
