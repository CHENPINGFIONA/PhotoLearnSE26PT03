package sg.edu.nus.se26pt03.photolearn.BAL;

import android.databinding.Bindable;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.Observable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BR;

/**
 * Created by chen ping on 7/3/2018.
 */

public class QuizItem extends Item implements Serializable, Cloneable {
    public List<QuizOption> getQuizOptions() {
        if(quizOptions.isEmpty()){
            quizOptions.add(0, new QuizOption(this));
            quizOptions.add(1,new QuizOption(this));
            quizOptions.add(2,new QuizOption(this));
            quizOptions.add(3,new QuizOption(this));

        };
        return quizOptions;
    }

    private List<QuizOption> quizOptions;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private int position;

    public QuizItem(Title title) {

        super(title);
        quizOptions = new ArrayList<QuizOption>();
    }

    public boolean checkAnswer(int selectedOptionId) {
        return true;
    }

    public void Add(QuizOption option) {
        this.quizOptions.add(option);
    }

    public void Update(int position, QuizOption option) {
        this.quizOptions.add(position, option);
    }

    public void Delete(QuizOption option) {
        this.quizOptions.remove(option);
    }
    @Override
    public QuizItem clone() throws CloneNotSupportedException {
        return (QuizItem) super.clone();
    }
    @Bindable
    public boolean getValidity() {
        if ("".equals(getContent())) return false;
        if (getQuizOptions() != null) {
            if (!getQuizOptions().stream().anyMatch(m -> m.isAnswer())) {
                return false;
            }
            if (getQuizOptions().stream().anyMatch(m -> "".equals(m.getContent()))) {
                return false;
            }
        }
        else return false;


            return true;
    }

    @Override
    public void setContent(String content) {
        super.setContent(content);
    }

    @Bindable
    public String getContentError() {
        if (getContent() != null && getContent().isEmpty()) {
            return "Question is required";
        }

        return "";
    }

    @Bindable
    public String getOptionsError() {
        if (getQuizOptions() != null && getQuizOptions().isEmpty()) {
            return "Quiz Options are required";
        }
        return "";
    }

    @Bindable
    public String getIsAnswerError() {
        if (getQuizOptions() != null)
            if (!getQuizOptions().stream().anyMatch(m -> m.isAnswer())) {
                return "Atleast one check box is required";
            }
        return "";
    }
    @Bindable
    public String getOptionContentError() {
        if (getQuizOptions() != null)
            if (getQuizOptions().stream().anyMatch(m -> "".equals(m.getContent())) ) {
                return "All options are required";
            }
        return "";
    }
    @Override
    protected void notifyValidity() {
        //notifyPropertyChanged();
        notifyPropertyChanged(BR.validity);
        notifyPropertyChanged(BR.contentError);
        notifyPropertyChanged(BR.optionsError);
        notifyPropertyChanged(BR.isAnswerError);
        notifyPropertyChanged(BR.optionContentError);

    }



/*    public  void createQuizOption(QuizOption option, ServiceCallback<QuizOption> optionServiceCallback){
        quizOptionService.save(option,optionServiceCallback);
    }
    public  void updateQuizOption(QuizOption option, ServiceCallback<Boolean> optionServiceCallback){
        quizOptionService.update(option,optionServiceCallback);
    }
    public  void deleteQuizOption(String optionId, ServiceCallback<Boolean> optionServiceCallback){
        quizOptionService.deleteById(optionId,optionServiceCallback);
    }*/
   /* public List<QuizOption> getOptions(ServiceCallback<Boolean> optionServiceCallback){

    }*/

  /*  public void createQuizAnswer(QuizAnswer answer, int userId) {
    }*/

}
