package sg.edu.nus.se26pt03.photolearn.DAL;

import com.google.firebase.database.Exclude;

import java.util.Date;
/**
 * Created by chen ping on 11/3/2018.
 * Restructured by MyatMin on 12/3/2018.
 */

public class QuizAnswerDAO extends BaseDAO {
    private String QuizItemId;
    private String CreatedBy;
    private Date Timestamp;

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
        return Timestamp;
    }

    public void setTimestamp(Date timestamp) {
        Timestamp = timestamp;
    }
}
