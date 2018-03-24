package sg.edu.nus.se26pt03.photolearn.BAL;

import android.databinding.BaseObservable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.service.ServiceCallback;

/**
 * Created by chen ping on 3/10/2018.
 */

public abstract class Title extends BaseObservable implements Serializable {
    private String id;
    private LearningSession learningSession;
    private String title;
    private String createdBy;
    private Date timestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LearningSession getLearningSession() {
        return learningSession;
    }

    public void setLearningSession(LearningSession learningSession) {
        this.learningSession = learningSession;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public abstract void createItem(Item item, ServiceCallback<Item> callback) ;
    public abstract void updateItem(Item item,ServiceCallback<Boolean> callback);
    public abstract void deleteItem(String itemId,ServiceCallback<Boolean> callback) ;
    public abstract void getItems(ServiceCallback<List<Item>> callback);

}
