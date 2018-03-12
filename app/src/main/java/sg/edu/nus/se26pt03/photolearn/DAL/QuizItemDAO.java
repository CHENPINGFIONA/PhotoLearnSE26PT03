package sg.edu.nus.se26pt03.photolearn.DAL;

import com.google.firebase.database.Exclude;

import java.util.Date;

/**
 * Created by chen ping on 3/10/2018.
 * Restructured by MyatMin on 12/3/2018.
 */

public class QuizItemDAO extends  BaseDAO {
    private String QuizTitleId;
    private String PhotoURL;
    private String Content;
    private String Explaination ;
    private int Position;

    private String CreatedBy;
    private Date Timestamp;

    public String getQuizTitleId() {
        return QuizTitleId;
    }

    public void setQuizTitleId(String quizTitleId) {
        QuizTitleId = quizTitleId;
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

    public String getExplaination() {
        return Explaination;
    }

    public void setExplaination(String explaination) {
        Explaination = explaination;
    }

    public int getPosition() {
        return Position;
    }

    public void setPosition(int position) {
        Position = position;
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
