package sg.edu.nus.se26pt03.photolearn.DAL;

import java.util.Date;

import sg.edu.nus.se26pt03.photolearn.utility.DateConversionHelper;

/**
 * Created by chen ping on 3/10/2018.
 * Restructured by MyatMin on 12/3/2018.
 */

public class LearningTitleDAO extends BaseDAO {
    private String Title;
    private String LearningSessionId;
    private String CreatedBy;
    private String Timestamp;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getLearningSessionId() {
        return LearningSessionId;
    }

    public void setLearningSessionId(String learningSessionId) {
        LearningSessionId = learningSessionId;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public Date getTimestamp() {
        DateConversionHelper dateConversionHelper = new DateConversionHelper();
        return dateConversionHelper.convertStringToDate(Timestamp);
    }

    public void setTimestamp(Date timestamp) {
        DateConversionHelper dateConversionHelper = new DateConversionHelper();
        Timestamp = dateConversionHelper.convertDateToString(timestamp);
    }
}
