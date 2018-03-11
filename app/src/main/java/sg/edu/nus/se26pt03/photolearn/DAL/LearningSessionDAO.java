package sg.edu.nus.se26pt03.photolearn.DAL;

import java.util.Date;

/**
 * Created by chen ping on 3/10/2018.
 */

public class LearningSessionDAO {
    private int Id;
    private Date CourseDate;
    private String CourseName;
    private String CourseCode;
    private int ModuleNumber;

    private int CreatedBy;
    private Date Timestamp;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

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

    public int getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(int createdBy) {
        CreatedBy = createdBy;
    }

    public Date getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(Date timestamp) {
        Timestamp = timestamp;
    }
}
