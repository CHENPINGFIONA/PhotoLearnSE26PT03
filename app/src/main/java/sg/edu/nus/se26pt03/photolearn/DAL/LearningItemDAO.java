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
    private Double Latitude;
    private Double Longitude;

    public int getPosition() {
        return Position;
    }

    public void setPosition(int position) {
        Position = position;
    }

    private int Position;


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

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }
}
