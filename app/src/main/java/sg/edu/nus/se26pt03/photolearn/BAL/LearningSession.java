package sg.edu.nus.se26pt03.photolearn.BAL;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chen ping on 7/3/2018.
 */

public class LearningSession {
    private int id;
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

    public String getLearningSessionId() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return String.format("{0}-{1}-M{2}", df.format(courseDate), courseCode, moduleNumber);
    }

    public void createLearningTitle(LearningTitle title) {
        //test to add in to list
        String size = Integer.toString(learningTitles.size());
        title = new LearningTitle(size, "test" + size, "1");
        learningTitles.add(title);
    }

    public void updateLearningTitle(LearningTitle title, int userId) {

    }

    public void deleteLearningTitle(int learningTitleId) {

    }

    public List<LearningTitle> getLearningTitles(String sessionId, String mode, String userId) {
        //get from database
        //test to return learningTitles;
        LearningTitle title = new LearningTitle("1", "test1", "1");
        learningTitles.add(title);

        return learningTitles;
    }

    public LearningTitle getLearningTitle(int learningTitleId) {
        return new LearningTitle();
    }

    public void createQuizTitle(QuizTitle title, int userId) {

    }

    public void updateQuizTitle(QuizTitle title, int userId) {

    }

    public void deleteQuizTitle(int quizTitleId) {

    }

    public List<QuizTitle> getQuizTitles(int sessionId, int userId) {
        return (new ArrayList<QuizTitle>());
    }

    public QuizTitle getQuizTitle(int quizTitleId) {
        return new QuizTitle();
    }
}
