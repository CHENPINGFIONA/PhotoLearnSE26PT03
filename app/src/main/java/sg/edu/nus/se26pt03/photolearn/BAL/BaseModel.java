package sg.edu.nus.se26pt03.photolearn.BAL;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Drake on 20/3/18.
 */

public class BaseModel implements Serializable{
    private String Id;
    private User createdBy;
    private Date timestamp;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
