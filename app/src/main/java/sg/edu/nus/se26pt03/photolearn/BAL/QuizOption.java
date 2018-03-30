package sg.edu.nus.se26pt03.photolearn.BAL;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.io.Serializable;

import sg.edu.nus.se26pt03.photolearn.BR;

/**
 * Created by chen_ on 7/3/2018.
 */

public class QuizOption extends BaseObservable implements Serializable {
    //Uid of Quiz Option
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //Quiz Item
    private final QuizItem quizItem;

    // Question
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        notifyValidity();
    }

    // Is Answer
    private boolean isAnswer;
    public boolean isAnswer() {
        return isAnswer;
    }

    public void setAnswer(boolean answer) {
        isAnswer = answer;
        quizItem.notifyValidity();
    }

    //Constructor
    public QuizOption(QuizItem quizItem) {
        this.quizItem = quizItem;
    }

    //get Quiz Item Id
    public String getQuizItemId() {
        return quizItem.getId();
    }








    @Bindable
    public String getContentError() {
        if (getContent() != null && getContent().isEmpty()) {
            return "Question is required";
        }
        return "";
    }

    private void notifyValidity() {
        notifyPropertyChanged(BR.contentError);
        quizItem.notifyValidity();
    }
}
