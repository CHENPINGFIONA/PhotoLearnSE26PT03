package sg.edu.nus.se26pt03.photolearn.BAL;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Date;

/**
 * Created by chen ping on 3/10/2018.
 */

public class Item implements Serializable {
    private String photoURL;
    private String id;

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    private Title title;
    private String content;
    private Coordinate coordinate;
    private String createdBy;
    private Date timestamp;

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getContent() {
        return content ==null?"":content ;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getTimestamp() {
        return timestamp==null?new Date():timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
