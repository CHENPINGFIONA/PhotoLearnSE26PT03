package nus.edu.sg.photolearnse26pt03.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chen ping on 7/3/2018.
 */

public class QuizTitle {
    private int ID;
    private String title;
    private int sessionID;
    private int createdBy;
    private Date timestamp;
    private int currentSequenceNum;

    private List<QuizItem> quizItems;

    public QuizTitle() {
        quizItems = new ArrayList<QuizItem>();
    }

    public void createQuizItem(QuizItem item) {

    }

    public void updateQuizItem(QuizItem item) {

    }

    public void deleteQuizItem(int quizItemID) {

    }

    public List<QuizItem> getQuizItems() {
        return (new ArrayList<QuizItem>());
    }

    public int getQuizScore(int userID) {
        return 0;
    }

    public QuizItem getCurrentItem() {
        return new QuizItem();
    }

    public QuizItem getPreviousItem() {
        return new QuizItem();
    }

    public QuizItem getNextItem() {
        return new QuizItem();
    }

    public void startQuiz(){

    }
}
