package sg.edu.nus.se26pt03.photolearn.DAL;

import java.util.Date;

/**
 * Created by chen ping on 3/10/2018.
 */

public class LearningItemDAO {
    private int Id;
    private int TitleId;
    private String PhotoURL;
    private String Content;
    private String Latitude;
    private String Longitude;

    private int CreatedBy;
    private Date Timestamp;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getTitleId() {
        return TitleId;
    }

    public void setTitleId(int titleId) {
        TitleId = titleId;
    }

    public String getPhotoURL() {
        return PhotoURL;
    }

    public void setPhotoURL(String photoURL) {
        PhotoURL = photoURL;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
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
