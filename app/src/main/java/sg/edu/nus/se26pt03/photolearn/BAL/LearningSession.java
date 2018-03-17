package sg.edu.nus.se26pt03.photolearn.BAL;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.DAL.LearningTitleDAO;
import sg.edu.nus.se26pt03.photolearn.DAL.QuizTitleDAO;
import sg.edu.nus.se26pt03.photolearn.database.FireBaseCallback;
import sg.edu.nus.se26pt03.photolearn.database.LearningTitleRepo;
import sg.edu.nus.se26pt03.photolearn.database.QuizTitleRepo;
import sg.edu.nus.se26pt03.photolearn.enums.AccessMode;
import sg.edu.nus.se26pt03.photolearn.utility.ConvertHelper;

/**
 * Created by chen ping on 7/3/2018.
 */

public class LearningSession implements ICallback {
    private int id;
    private Date courseDate;
    private String courseName;
    private String courseCode;
    private int moduleNumber;
    private String moduleName;
    private int createdBy;
    private Date timestamp;

    private List<LearningTitle> learningTitles;
    private List<QuizTitle> quizTitles;
    private LearningTitleRepo learningTitleRepo;
    private QuizTitleRepo quizTitleRepo;

    public Date getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(Date courseDate) {
        this.courseDate = courseDate;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public int getModuleNumber() {
        return moduleNumber;
    }

    public void setModuleNumber(int moduleNumber) {
        this.moduleNumber = moduleNumber;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public LearningSession() {
        learningTitleRepo = new LearningTitleRepo();
        quizTitleRepo = new QuizTitleRepo();
        learningTitles = new ArrayList<LearningTitle>();
        quizTitles = new ArrayList<QuizTitle>();
    }

    public String getLearningSessionId() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return String.format("{0}-{1}-M{2}", df.format(courseDate), courseCode, moduleNumber);
    }

    public void createLearningTitle(LearningTitle title) {
        learningTitleRepo.save(ConvertHelper.toLearningTitleDao(title));
    }

    public void updateLearningTitle(LearningTitle title) {
        learningTitleRepo.update(ConvertHelper.toLearningTitleDao(title));
    }

    public void deleteLearningTitle(LearningTitle title) {
        learningTitleRepo.delete(ConvertHelper.toLearningTitleDao(title));
    }

    private List<LearningTitle> processLearningTitles(List<LearningTitleDAO> daos) {
        List<LearningTitle> titles = new ArrayList<LearningTitle>();

        for (LearningTitleDAO learningTitleDAO : daos) {
            titles.add(ConvertHelper.fromLearningTitle(learningTitleDAO));
        }

        return titles;
    }

    public List<LearningTitle> getLearningTitles(String learningSessionId, String userId, int mode, String text) {
        List<LearningTitleDAO> learningTitleDAOs;
        if (mode == AccessMode.toInt(AccessMode.EDIT)) {
            learningTitleRepo.getAllByCreator(learningSessionId, userId, new FireBaseCallback<LearningTitleDAO>() {
                @Override
                public void onCallback(List<LearningTitleDAO> itemList) {
                    learningTitles = processLearningTitles(itemList);
                }
            });
        } else {
            learningTitleRepo.getAllByLearningSessionID(learningSessionId, text, new FireBaseCallback<LearningTitleDAO>() {
                @Override
                public void onCallback(List<LearningTitleDAO> itemList) {
                    learningTitles = processLearningTitles(itemList);
                }
            });
        }

        return learningTitles;
    }

    public LearningTitle getLearningTitle(int learningTitleId) {
        return new LearningTitle();
    }

    public void createQuizTitle(QuizTitle title) {
        QuizTitleRepo repo = new QuizTitleRepo();
        repo.save(ConvertHelper.toQuizTitleDao(title));
    }

    public void updateQuizTitle(QuizTitle title) {
        quizTitleRepo.update(ConvertHelper.toQuizTitleDao(title));
    }

    public void deleteQuizTitle(QuizTitle title) {
        quizTitleRepo.delete(ConvertHelper.toQuizTitleDao(title));
    }

    public List<QuizTitle> getQuizTitles(String learningSessionId) {
//        Collection<QuizTitleDAO> quizTitleDAOs = quizTitleRepo.getAllByLearningSessionID(learningSessionId);
//
//        for (QuizTitleDAO quizTitleDAO : quizTitleDAOs) {
//            quizTitles.clear();
//            quizTitles.add(ConvertHelper.fromQuizTitle(quizTitleDAO));
//        }

        return quizTitles;
    }

    public QuizTitle getQuizTitle(int quizTitleId) {
        return new QuizTitle();
    }

    @Override
    public void callback() {

    }
}
