package sg.edu.nus.se26pt03.photolearn.BAL;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.lang.Cloneable;

import sg.edu.nus.se26pt03.photolearn.BR;
import sg.edu.nus.se26pt03.photolearn.service.LearningTitleService;
import sg.edu.nus.se26pt03.photolearn.service.QuizTitleService;
import sg.edu.nus.se26pt03.photolearn.service.ServiceCallback;
import sg.edu.nus.se26pt03.photolearn.utility.BindingHelper;
import sg.edu.nus.se26pt03.photolearn.utility.DateConversionHelper;

/**
 * Created by chen ping on 7/3/2018.
 */

public class LearningSession extends BaseObservable implements Cloneable, Serializable {
    private String id;
    private Date courseDate;
    private String courseName;
    private String courseCode;
    private int moduleNumber = -1;
    private String moduleName;

    private String createdBy;
    private Date timestamp;

    private transient List<LearningTitle> learningTitles;
    private transient List<QuizTitle> quizTitles;

    private transient LearningTitleService learningTitleService = new LearningTitleService();
    private transient QuizTitleService quizTitleService = new QuizTitleService();

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
        if ((courseDate != null && this.courseDate == null) || !this.courseDate.equals(courseDate)) {
            this.courseDate = courseDate;
            notifyValidity();
        }
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        if ((courseName != null && this.courseName == null) || !this.courseName.equals(courseName)) {
            this.courseName = courseName;
            notifyValidity();
        }
    }

    @Bindable
    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        if ((courseCode != null && this.courseCode == null) || !this.courseCode.equals(courseCode)) {
            this.courseCode = courseCode;
            notifyValidity();
        }
    }

    public int getModuleNumber() {
        return moduleNumber;
    }

    public void setModuleNumber(int moduleNumber) {
        if (this.moduleNumber != moduleNumber) {
            this.moduleNumber = moduleNumber;
            notifyValidity();
        }
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        if ((moduleName != null && this.moduleName == null) || !this.moduleName.equals(moduleName)) {
            this.moduleName = moduleName;
            notifyValidity();
        }
    }

    public LearningSession() {
        learningTitles = new ArrayList<LearningTitle>();
        quizTitles = new ArrayList<QuizTitle>();
    }

    public String getLearningSessionId() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(courseDate) + "-" + courseCode.toUpperCase() + "-M" + BindingHelper.fromModuleNumber(moduleNumber);
    }

    public boolean addLearningTitles(List<LearningTitle> learningTitles) {
        for (LearningTitle learningTitle : learningTitles) {
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
        for (LearningTitle learningTitle : learningTitles) {
            if (!removeLearningTitle(learningTitle)) {
                return false;
            }
        }
        return true;
    }

    public boolean removeLearningTitle(LearningTitle learningTitle) {
        return learningTitles.remove(learningTitle);
    }

    public boolean removeAllLearningTitle() {
        return learningTitles.removeAll(learningTitles);
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

    public boolean addQuizTitles(List<QuizTitle> quizTitles) {
        for (QuizTitle quizTitle : quizTitles) {
            if (!addQuizTitle(quizTitle)) {
                return false;
            }
        }
        return true;
    }

    public boolean removeQuizTitle(QuizTitle quizTitle) {
        return quizTitles.remove(quizTitle);
    }

    public boolean removeAllQuizTitle() {
        return quizTitles.removeAll(quizTitles);
    }

    public List<QuizTitle> getQuizTitles() {
        return quizTitles;
    }

    public QuizTitle getQuizTitle(int index) {
        return quizTitles.get(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LearningSession that = (LearningSession) o;

        if (getModuleNumber() != that.getModuleNumber()) return false;
        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getCourseDate() != null ? !getCourseDate().equals(that.getCourseDate()) : that.getCourseDate() != null)
            return false;
        if (getCourseName() != null ? !getCourseName().equals(that.getCourseName()) : that.getCourseName() != null)
            return false;
        if (getCourseCode() != null ? !getCourseCode().equals(that.getCourseCode()) : that.getCourseCode() != null)
            return false;
        if (getModuleName() != null ? !getModuleName().equals(that.getModuleName()) : that.getModuleName() != null)
            return false;
        if (getCreatedBy() != null ? !getCreatedBy().equals(that.getCreatedBy()) : that.getCreatedBy() != null)
            return false;
        return getTimestamp() != null ? getTimestamp().equals(that.getTimestamp()) : that.getTimestamp() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getCourseDate() != null ? getCourseDate().hashCode() : 0);
        result = 31 * result + (getCourseName() != null ? getCourseName().hashCode() : 0);
        result = 31 * result + (getCourseCode() != null ? getCourseCode().hashCode() : 0);
        result = 31 * result + getModuleNumber();
        result = 31 * result + (getModuleName() != null ? getModuleName().hashCode() : 0);
        result = 31 * result + (getCreatedBy() != null ? getCreatedBy().hashCode() : 0);
        result = 31 * result + (getTimestamp() != null ? getTimestamp().hashCode() : 0);
        return result;
    }

    public void copy(LearningSession value) {
        this.setCourseCode(value.getCourseCode());
        this.setCourseName(value.getCourseName());
        this.setModuleNumber(value.getModuleNumber());
        this.setModuleName(value.getModuleName());
        this.setCourseDate(value.getCourseDate());
        this.setCreatedBy(value.getCreatedBy());
        this.setTimestamp(value.getTimestamp());
    }

    @Override
    public LearningSession clone() throws CloneNotSupportedException {
        return (LearningSession) super.clone();
    }

    public boolean isEmpty() {
        return equals(new LearningSession());
    }


    @Bindable
    public boolean getValidity() {
        if ("".equals(getCourseCode())) return false;
        if ("".equals(getCourseName())) return false;
        if ("".equals(getModuleName())) return false;
        if (getModuleNumber() <= 0) return false;
        if (getCourseDate() == null) return false;
        return true;
    }

    @Bindable
    public String getCourseCodeError() {
        if (getCourseCode() != null && getCourseCode().isEmpty()) {
            return "Course code is required";
        }
        return "";
    }

    @Bindable
    public String getCourseNameError() {
        if (getCourseName() != null && getCourseName().isEmpty()) {
            return "Course name is required";
        }
        return "";
    }

    @Bindable
    public String getModuleNumberError() {
        if (getModuleNumber() == 0) {
            return "Invalid module number";
        }
        return "";
    }

    @Bindable
    public String getModuleNameError() {
        if (getModuleName() != null && getModuleName().isEmpty()) {
            return "Module name is required";
        }
        return "";
    }

    @Bindable
    public String getCourseDateError() {
        if (getCourseDate() == null) {
            return "Course date is required";
        }
        return "";
    }

    private void notifyValidity() {
        notifyPropertyChanged(BR.validity);
        notifyPropertyChanged(BR.courseCodeError);
        notifyPropertyChanged(BR.courseNameError);
        notifyPropertyChanged(BR.moduleNumberError);
        notifyPropertyChanged(BR.moduleNameError);
        notifyPropertyChanged(BR.courseDateError);
    }

    public void createLearningTitle(LearningTitle title, ServiceCallback<LearningTitle> callback) {
        try {
            learningTitleService.save((LearningTitle) title, callback);

        } catch (Exception ex) {
            throw ex;
        }
    }

    public void updateLearningTitle(LearningTitle title, ServiceCallback<Boolean> callback) {
        try {
            learningTitleService.update((LearningTitle) title, callback);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void deleteLearningTitle(String titleId) {
        try {

            learningTitleService.deleteById(titleId, null);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void createQuizTitle(QuizTitle title, ServiceCallback<QuizTitle> callback) {
        try {
            quizTitleService.save((QuizTitle) title, callback);

        } catch (Exception ex) {
            throw ex;
        }
    }

    public void updateQuizTitle(QuizTitle title, ServiceCallback<Boolean> callback) {
        try {
            quizTitleService.update((QuizTitle) title, callback);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void deleteQuizTitle(String titleId) {
        try {

            quizTitleService.deleteById(titleId, null);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
