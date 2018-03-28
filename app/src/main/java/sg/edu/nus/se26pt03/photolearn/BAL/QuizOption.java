package sg.edu.nus.se26pt03.photolearn.BAL;

import java.io.Serializable;

/**
 * Created by chen_ on 7/3/2018.
 */

public class QuizOption implements Serializable {
    public QuizOption(String quizItemId) {
        this.quizItemId = quizItemId;
    }

    public String getQuizItemId() {
        return quizItemId;
    }

    public void setQuizItemId(String quizItemId) {
        this.quizItemId = quizItemId;
    }





    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isAnswer() {
        return isAnswer;
    }

    public void setAnswer(boolean answer) {
        isAnswer = answer;
    }

    private String id;
    private String content;
    private boolean isAnswer;
    private String quizItemId;
}
