package sg.edu.nus.se26pt03.photolearn.DAL;

import java.util.Date;

/**
 * Created by chen ping on 3/10/2018.
 */

public class QuizItemDAO {
    private int Id;
    private int TitleId;
    private String PhotoURL;
    private String Content;
    private String Explaination ;
    private int Position;

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
