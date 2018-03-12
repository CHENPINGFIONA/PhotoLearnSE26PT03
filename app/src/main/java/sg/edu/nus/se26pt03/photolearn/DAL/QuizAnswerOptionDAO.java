package sg.edu.nus.se26pt03.photolearn.DAL;
/**
 * Created by chen ping on 11/3/2018.
 * Restructured by MyatMin on 12/3/2018.
 */

public class QuizAnswerOptionDAO extends  BaseDAO{
    private String QuizAnswerId;
    private String QuizOptionId;

    public String getQuizAnswerId() {
        return QuizAnswerId;
    }

    public void setQuizAnswerId(String quizAnswerId) {
        QuizAnswerId = quizAnswerId;
    }

    public String getQuizOptionId() {
        return QuizOptionId;
    }

    public void setQuizOptionId(String quizOptionId) {
        QuizOptionId = quizOptionId;
    }
}
