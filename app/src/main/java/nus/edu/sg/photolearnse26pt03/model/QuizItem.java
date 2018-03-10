package nus.edu.sg.photolearnse26pt03.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chen ping on 7/3/2018.
 */

public class QuizItem {
    private int ID;
    private int quizTitleID;
    private int sequenceNum;
    private String photoURL;
    private int createdBy;
    private Date timestamp;

    private List<QuizOption> options;

    public QuizItem() {
        options = new ArrayList<QuizOption>();
    }

    public int getScore() {
        return 0;
    }

    public boolean checkAnswer(int selectedOptionID) {
        return true;
    }

    public void saveAnswer(QuizAnswer answer, int userID) {
    }

    public QuizAnswer getAnswer(int userID) {
        return new QuizAnswer();
    }
}
