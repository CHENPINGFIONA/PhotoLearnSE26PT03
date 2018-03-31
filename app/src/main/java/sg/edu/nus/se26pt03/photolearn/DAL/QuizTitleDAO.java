package sg.edu.nus.se26pt03.photolearn.DAL;

import java.util.Date;

import sg.edu.nus.se26pt03.photolearn.utility.DateConversionHelper;

/**
 * Created by part time team 3 on 3/10/2018.
 * Restructured by part time team 3  on 12/3/2018.
 */

public class QuizTitleDAO extends BaseDAO {
    private String Title;
    private String LearningSessionId;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getLearningSessionId() {
        return LearningSessionId;
    }

    public void setLearningSessionId(String learningSessionId) {
        LearningSessionId = learningSessionId;
    }
}
