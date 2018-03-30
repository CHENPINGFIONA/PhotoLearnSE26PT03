package sg.edu.nus.se26pt03.photolearn.DAL;

import java.util.Date;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.utility.DateConversionHelper;

/**
 * Created by chen ping on 11/3/2018.
 * Restructured by MyatMin on 12/3/2018.
 */

public class QuizAnswerDAO extends BaseDAO {
    private String QuizItemId;
    private boolean isCurrentAttempt;
    private List<String> SelectedQuizOptionIds;

    public String getQuizItemId() {
        return QuizItemId;
    }

    public void setQuizItemId(String quizItemId) {
        QuizItemId = quizItemId;
    }

    public boolean getIsCurrentAttempt() {
        return isCurrentAttempt;
    }

    public void setIsCurrentAttempt(boolean currentAttempt) {
        isCurrentAttempt = currentAttempt;
    }

    public List<String> getSelectedQuizOptionIds() {
        return SelectedQuizOptionIds;
    }

    public void setSelectedQuizOptionIds(List<String> selectedQuizOptionIds) {
        SelectedQuizOptionIds = selectedQuizOptionIds;
    }
}
