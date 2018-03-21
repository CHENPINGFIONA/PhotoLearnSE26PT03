package sg.edu.nus.se26pt03.photolearn.BAL;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Date;

/**
 * Created by chen ping on 3/10/2018.
 */

public class Item implements Serializable {
    private String photoURL;
    private String content;
    private Coordinate coordinate;

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getContent() {
        return content;
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
}
