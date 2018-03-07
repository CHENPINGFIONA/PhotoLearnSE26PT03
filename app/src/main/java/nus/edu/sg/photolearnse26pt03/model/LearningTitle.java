package nus.edu.sg.photolearnse26pt03.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chen ping on 7/3/2018.
 */

public class LearningTitle {
    private int ID;
    private String title;
    private int sessionID;
    private int createdBy;
    private Date timestamp;

    private List<LearningItem> learningItems;

    public LearningTitle() {
        learningItems = new ArrayList<LearningItem>();
    }

    public void createLearningItem(LearningItem item, int userID) {

    }

    public void updateLearningItem(LearningItem item, int userID) {

    }

    public void deleteLearningItem(int learningItemID) {

    }

    public List<LearningItem> getLearningItems(int learningTitleID) {
        return (new ArrayList<LearningItem>());
    }
}
