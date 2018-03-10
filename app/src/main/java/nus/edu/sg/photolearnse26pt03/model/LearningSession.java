package nus.edu.sg.photolearnse26pt03.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chen ping on 7/3/2018.
 */

public class LearningSession {
    private int ID;
    private Date courseDate;
    private String courseName;
    private String courseCode;
    private int moduleNumber;
    private int createdBy;
    private Date timestamp;

    private List<LearningTitle> learningTitles;
    private List<QuizTitle> quizTitles;

    public LearningSession() {
        learningTitles = new ArrayList<LearningTitle>();
        quizTitles = new ArrayList<QuizTitle>();
    }

    public String getLearningSessionID() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return String.format("{0}-{1}-M{2}", df.format(courseDate), courseCode, moduleNumber);
    }

    public void createLearningTitle(LearningTitle title, int userID) {

    }

    public void updateLearningTitle(LearningTitle title, int userID) {

    }

    public void deleteLearningTitle(int learningTitleID) {

    }

    public List<LearningTitle> getLearningTitles(int sessionID, int mode, int userID) {
        return (new ArrayList<LearningTitle>());
    }

    public LearningTitle getLearningTitle(int learningTitleID) {
        return new LearningTitle();
    }

    public void createQuizTitle(QuizTitle title, int userID) {

    }

    public void updateQuizTitle(QuizTitle title, int userID) {

    }

    public void deleteQuizTitle(int quizTitleID) {

    }

    public List<QuizTitle> getQuizTitles(int sessionID, int userID) {
        return (new ArrayList<QuizTitle>());
    }

    public QuizTitle getQuizTitle(int quizTitleID) {
        return new QuizTitle();
    }
}
