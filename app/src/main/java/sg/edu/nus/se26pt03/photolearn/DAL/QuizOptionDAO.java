package sg.edu.nus.se26pt03.photolearn.DAL;

/**
 * Created by chen ping on 11/3/2018.
 */

public class QuizOptionDAO {
    private int Id;
    private int QuizItemId;
    private String Content;
    private boolean IsAnswer;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getQuizItemId() {
        return QuizItemId;
    }

    public void setQuizItemId(int quizItemId) {
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
