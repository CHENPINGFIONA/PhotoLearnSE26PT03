package sg.edu.nus.se26pt03.photolearn.DAL;

import android.animation.TypeConverter;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.utility.DateConversionHelper;

/**
 * Created by chen ping on 3/10/2018.
 * Restructured by MyatMin on 12/3/2018.
 */
@IgnoreExtraProperties
public class LearningSessionDAO extends BaseDAO{
    private String CourseDate;
    private String CourseName;
    private String CourseCode;
    private int ModuleNumber;
    private  String ModuleName;

    private String Timestamp;

//    @Override
//    public LearningSession convert(LearningSessionDAO value) {
//        LearningSession learningSession = new LearningSession();
//        learningSession.setCourseDate(value.getCourseDate());
//        learningSession.setCourseCode(value.getCourseCode());
//        learningSession.setCourseName(value.getCourseName());
//        learningSession.setModuleNumber(value.getModuleNumber());
//        learningSession.setModuleName(value.getModuleName());
//        return learningSession;
//    }

    public Date getCourseDate() {
        if (CourseDate == null) {
            return null;
        } else {
            DateConversionHelper dateConversionHelper = new DateConversionHelper();
            return dateConversionHelper.convertStringToDate(CourseDate);
        }
    }

    public void setCourseDate(Date courseDate) {
        if (courseDate == null) {
            CourseDate = null;
        } else {
            DateConversionHelper dateConversionHelper = new DateConversionHelper();
            CourseDate = dateConversionHelper.convertDateToString(courseDate);
        }
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

    public int getModuleNumber() {
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

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public Date getTimestamp() {
        if (Timestamp == null) {
            return null;
        } else {
            DateConversionHelper dateConversionHelper = new DateConversionHelper();
            return dateConversionHelper.convertStringToDate(Timestamp);
        }
    }

    public void setTimestamp(Date timestamp) {
        if (timestamp == null) {
            Timestamp = null;
        } else {
            DateConversionHelper dateConversionHelper = new DateConversionHelper();
            Timestamp = dateConversionHelper.convertDateToString(timestamp);
        }
    }
}
