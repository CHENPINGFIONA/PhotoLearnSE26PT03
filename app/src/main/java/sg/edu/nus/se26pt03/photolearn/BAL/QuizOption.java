package sg.edu.nus.se26pt03.photolearn.BAL;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.io.Serializable;

import sg.edu.nus.se26pt03.photolearn.BR;

/**
 * Created by chen_ on 7/3/2018.
 */

public class QuizOption extends BaseObservable implements Serializable {
    private final QuizItem quizItem;

  /*  public QuizOption(String quizItemId) {
        this.quizItemId = quizItemId;
    }*/
    public QuizOption(QuizItem quizItem) {
        this.quizItem = quizItem;
    }

    public String getQuizItemId() {
        return quizItem.getId();
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
        this.content = content;   notifyValidity();
    }

    public boolean isAnswer() {
        return isAnswer;
    }

    public void setAnswer(boolean answer) {
        isAnswer = answer; quizItem.notifyValidity();
    }

   /*public boolean isAnswer() {
        return isAnswer;
    }

    public void setAnswer(boolean answer) {
        isAnswer = answer;
    }*/

    private String id;
    private String content;
    private boolean isAnswer;

    @Bindable
    public String getContentError() {
        if (getContent() != null && getContent().isEmpty()) {
            return "Question is required";
        }
        return "";
    }




    private void notifyValidity() {
        //notifyPropertyChanged();
        notifyPropertyChanged(BR.contentError);
        quizItem.notifyValidity();
       // notifyPropertyChanged(BR.);
    }
}
