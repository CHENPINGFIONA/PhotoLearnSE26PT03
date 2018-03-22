package sg.edu.nus.se26pt03.photolearn;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizTitle;

/**
 * Created by chen ping on 22/3/2018.
 */

public class LearningSessionUnitTest {
    private LearningSession session = null;
    private List<LearningTitle> learningTitles;
    private List<QuizTitle> quizTitles;

    public LearningSessionUnitTest() {
        setInitialValues();
    }

    private void setInitialValues() {
        session = new LearningSession();
        session.setId("1");
        session.setCourseCode("cs");
        session.setCourseDate(new Date());
        session.setCourseName("Course 1");
        session.setModuleName("md");
        session.setModuleNumber(1);
        session.setCreatedBy("chen ping");
        session.setTimestamp(new Date());

        learningTitles = new ArrayList<>();
        LearningTitle title = new LearningTitle();
        title.setLearningSession(session);
        title.setTitle("learning title 1");
        title.setCreatedBy("chen ping");
        title.setTimestamp(new Date());

        learningTitles.add(title);

        title.setTitle("learning title 2");
        title.setCreatedBy("chen ping");
        title.setTimestamp(new Date());

        learningTitles.add(title);
    }

    @Test
    public void Assert_CreateLearningTitle() throws Exception {
        LearningTitle title = new LearningTitle();
        title.setLearningSession(session);
        title.setTitle("learning title 3");
        title.setCreatedBy("chen ping");
        title.setTimestamp(new Date());

        session.addLearningTitle(title);
    }

    @Test
    public void Assert_UpdateLearningTitle() throws Exception {

    }

    @Test
    public void Assert_DeleteLearningTitle() throws Exception {

    }

    @Test
    public void Assert_GetLearningTitle() throws Exception {

    }

    @Test
    public void Assert_GetLearningTitles() throws Exception {

    }

    @Test
    public void Assert_CreateQuizTitle() throws Exception {


    }

    @Test
    public void Assert_UpdateQuizTitle() throws Exception {

    }

    @Test
    public void Assert_DeleteQuizTitle() throws Exception {

    }

    @Test
    public void Assert_GetQuizTitle() throws Exception {

    }

    @Test
    public void Assert_GetQuizTitles() throws Exception {

    }
}
