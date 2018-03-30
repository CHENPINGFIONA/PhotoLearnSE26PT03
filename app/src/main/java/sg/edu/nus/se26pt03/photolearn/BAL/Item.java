package sg.edu.nus.se26pt03.photolearn.BAL;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BR;
import sg.edu.nus.se26pt03.photolearn.service.QuizAnswerService;
import sg.edu.nus.se26pt03.photolearn.service.ServiceCallback;

/**
 * Created by chen ping on 3/10/2018.
 */

public class Item extends BaseObservable implements Serializable  {
    private String photoURL;
    private String id;
    private QuizAnswerService quizAnswerService;

    public Item(Title title) {
        this.title = title;
        quizAnswerService = new QuizAnswerService();
    }

    public Title getTitle() {
        return title;
    }
    @Bindable
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
      //  this.content = content;

        if ((content != null && this.content == null) || !this.content.equals(content)) {
            this.content = content;
            notifyValidity();
        }
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
    public void getQuizAnswer(String createdBy, ServiceCallback<List<QuizAnswer>> callback) {
        List<AbstractMap.SimpleEntry<String, Object>> listKeyValue = new ArrayList<AbstractMap.SimpleEntry<String, Object>>();
        listKeyValue.add(new AbstractMap.SimpleEntry<String, Object>("quizItemId", this.getId()));
        listKeyValue.add(new AbstractMap.SimpleEntry<String, Object>("createdBy", createdBy));
        quizAnswerService.getAllByKeyValueList(listKeyValue, callback);
    }

    public Date getTimestamp() {
        return timestamp==null?new Date():timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    @Bindable
    public boolean getValidity() {
        if ("".equals(getContent())) return false;
        return true;
    }
    @Bindable
    public String getContentError() {
        if (getContent() != null && getContent().isEmpty()) {
            return "Content is required";
        }
        return "";
    }

    private void notifyValidity() {
        //notifyPropertyChanged();
        notifyPropertyChanged(BR.validity);
        notifyPropertyChanged(BR.contentError);
    }
}
