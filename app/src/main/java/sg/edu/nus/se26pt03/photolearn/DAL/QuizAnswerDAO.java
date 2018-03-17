package sg.edu.nus.se26pt03.photolearn.DAL;

import java.util.Date;

import sg.edu.nus.se26pt03.photolearn.utility.DateConversionHelper;

/**
 * Created by chen ping on 11/3/2018.
 * Restructured by MyatMin on 12/3/2018.
 */

public class QuizAnswerDAO extends BaseDAO {
    private String QuizItemId;
    private String CreatedBy;
    private String Timestamp;

    public String getQuizItemId() {
        return QuizItemId;
    }

    public void setQuizItemId(String quizItemId) {
        QuizItemId = quizItemId;
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
