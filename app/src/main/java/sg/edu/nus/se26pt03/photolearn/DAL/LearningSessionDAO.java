package sg.edu.nus.se26pt03.photolearn.DAL;

import android.animation.TypeConverter;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.utility.DateConversionHelper;

/**
 * Created by part time team 3 on 3/10/2018.
 * Restructured by part time team 3  on 12/3/2018.
 */
@IgnoreExtraProperties
public class LearningSessionDAO extends BaseDAO{
    private long CourseDate;
    private String CourseName;
    private String CourseCode;
    private int ModuleNumber;
    private  String ModuleName;
    private String LearningSessionId;

    public String getLearningSessionId() {
        return LearningSessionId;
    }

    public void setLearningSessionId(String learningSessionId) {
        LearningSessionId = learningSessionId;
    }

    public long getCourseDate() {
        return CourseDate;
    }

    public void setCourseDate(long courseDate) {
        CourseDate = courseDate;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getCourseCode() {
        return CourseCode;
    }

    public void setCourseCode(String courseCode) {
        CourseCode = courseCode;
    }

    public Integer getModuleNumber() {
        return ModuleNumber;
    }

    public void setModuleNumber(int moduleNumber) {
        ModuleNumber = moduleNumber;
    }

    public String getModuleName() {
        return ModuleName;
    }

    public void setModuleName(String moduleName) {
        ModuleName = moduleName;
    }
}
