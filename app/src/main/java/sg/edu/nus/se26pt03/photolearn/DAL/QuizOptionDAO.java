package sg.edu.nus.se26pt03.photolearn.DAL;

import com.google.firebase.database.Exclude;

/**
 * Created by chen ping on 11/3/2018.
 * Restructured by MyatMin on 12/3/2018.
 */

public class QuizOptionDAO extends BaseDAO {
    private String QuizItemId;
    private String Content;
    private boolean IsAnswer;

    public String getQuizItemId() {
        return QuizItemId;
    }

    public void setQuizItemId(String quizItemId) {
        QuizItemId = quizItemId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public boolean isAnswer() {
        return IsAnswer;
    }

    public void setAnswer(boolean answer) {
        IsAnswer = answer;
    }
}
