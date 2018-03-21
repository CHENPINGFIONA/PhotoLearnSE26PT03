package sg.edu.nus.se26pt03.photolearn.BAL;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.DAL.LearningTitleDAO;
import sg.edu.nus.se26pt03.photolearn.DAL.QuizTitleDAO;
import sg.edu.nus.se26pt03.photolearn.database.ICallback;
import sg.edu.nus.se26pt03.photolearn.database.IListCallback;
import sg.edu.nus.se26pt03.photolearn.database.LearningTitleRepo;
import sg.edu.nus.se26pt03.photolearn.database.QuizTitleRepo;
import sg.edu.nus.se26pt03.photolearn.enums.AccessMode;
import sg.edu.nus.se26pt03.photolearn.utility.ConvertHelper;

/**
 * Created by chen ping on 7/3/2018.
 */

public class LearningSession {
    private String id;
    private Date courseDate;
    private String courseName;
    private String courseCode;
    private int moduleNumber;
    private String moduleName;

    private String createdBy;
    private Date timestamp;

    private List<LearningTitle> learningTitles;
    private List<QuizTitle> quizTitles;

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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

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
        learningTitles = new ArrayList<LearningTitle>();
        quizTitles = new ArrayList<QuizTitle>();
    }

    public LearningSession(String id) {
        this();
        this.id = id;
    }

    public String getLearningSessionId() {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return String.format("{0}-{1}-M{2}", df.format(courseDate), courseCode, moduleNumber);
    }

    public boolean addLearningTitle(List<LearningTitle> learningTitles) {
        for (LearningTitle learningTitle: learningTitles) {
            if (!addLearningTitle(learningTitle)) {
                return false;
            }
        }
        return true;
    }

    public boolean addLearningTitle(LearningTitle learningTitle) {
        learningTitle.setLearningSession(this);
        return learningTitles.add(learningTitle);
    }

    public boolean removeLearningTitle(List<LearningTitle> learningTitles) {
        for (LearningTitle learningTitle: learningTitles) {
            if (!removeLearningTitle(learningTitle)) {
                return false;
            }
        }
        return true;
    }

    public boolean removeLearningTitle(LearningTitle learningTitle) {
        return learningTitles.remove(learningTitle);
    }

    public List<LearningTitle> getLearningTitles() {
        return learningTitles;
    }

    public LearningTitle getLearningTitle(int index) {
        return learningTitles.get(index);
    }

    public boolean addQuizTitle(QuizTitle quizTitle) {
        quizTitle.setLearningSession(this);
        return quizTitles.add(quizTitle);
    }

    public boolean removeQuizTitle(QuizTitle quizTitle) {
        return quizTitles.remove(quizTitle);
    }

    public List<QuizTitle> getQuizTitles() {
        return quizTitles;
    }

    public QuizTitle getQuizTitle(int index) {
        return quizTitles.get(index);
    }

}
