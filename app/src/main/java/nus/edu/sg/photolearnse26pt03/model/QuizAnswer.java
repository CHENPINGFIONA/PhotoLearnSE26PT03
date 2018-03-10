package nus.edu.sg.photolearnse26pt03.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chen ping on 7/3/2018.
 */

public class QuizAnswer {
    private int ID;
    private int quizItemID;
    private int sessionID;
    private int createdBy;
    private Date timestamp;

    private List<Integer> selectedOptionIDs;

    public QuizAnswer() {
        selectedOptionIDs = new ArrayList<Integer>();
    }

    public List<Integer> getSelectedOptionIDs() {
        return new ArrayList<Integer>();
    }
}
