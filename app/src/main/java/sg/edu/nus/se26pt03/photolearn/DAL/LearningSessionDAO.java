package sg.edu.nus.se26pt03.photolearn.DAL;

import java.util.Date;

/**
 * Created by chen ping on 3/10/2018.
 * Restructured by MyatMin on 12/3/2018.
 */

public class LearningSessionDAO extends BaseDAO {
    private Date CourseDate;
    private String CourseName;
    private String CourseCode;
    private int ModuleNumber;

    private String CreatedBy;
    private Date Timestamp;

    public Date getCourseDate() {
        return CourseDate;
    }

    public void setCourseDate(Date courseDate) {
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

    public int getModuleNumber() {
        return ModuleNumber;
    }

    public void setModuleNumber(int moduleNumber) {
        ModuleNumber = moduleNumber;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public Date getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(Date timestamp) {
        Timestamp = timestamp;
    }
}
