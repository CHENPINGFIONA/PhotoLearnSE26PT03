package sg.edu.nus.se26pt03.photolearn.DAL;

import java.util.Date;

import sg.edu.nus.se26pt03.photolearn.utility.DateConversionHelper;

/**
 * Created by chen ping on 3/10/2018.
 * Restructured by MyatMin on 12/3/2018.
 */

public class LearningItemDAO extends BaseDAO {
    private String LearningTitleId;
    private String PhotoURL;
    private String Content;
    private String Latitude;
    private String Longitude;

    private String CreatedBy;
    private String Timestamp;

    public String getLearningTitleId() {
        return LearningTitleId;
    }

    public void setLearningTitleId(String learningTitleId) {
        LearningTitleId = learningTitleId;
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

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public Date getTimestamp() {
        if (Timestamp == null) {
            return null;
        } else {
            DateConversionHelper dateConversionHelper = new DateConversionHelper();
            return dateConversionHelper.convertStringToDate(Timestamp);
        }
    }

    public void setTimestamp(Date timestamp) {
        if (timestamp == null) {
            Timestamp = null;
        } else {
            DateConversionHelper dateConversionHelper = new DateConversionHelper();
            Timestamp = dateConversionHelper.convertDateToString(timestamp);
        }
    }
}
