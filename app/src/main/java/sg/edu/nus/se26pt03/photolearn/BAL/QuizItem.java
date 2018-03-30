package sg.edu.nus.se26pt03.photolearn.BAL;

import android.databinding.Bindable;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BR;
import sg.edu.nus.se26pt03.photolearn.service.QuizAnswerService;
import sg.edu.nus.se26pt03.photolearn.service.ServiceCallback;

/**
 * Created by chen ping on 7/3/2018.
 */

public class QuizItem extends Item implements Serializable,Cloneable{
    public List<QuizOption> getQuizOptions() {
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

    private QuizAnswerService quizAnswerService;

    public QuizItem(Title title) {

        super(title);
        quizOptions = new ArrayList<QuizOption>();
        quizAnswerService = new QuizAnswerService();
    }

    public void getQuizAnswers(String createdBy, ServiceCallback<List<QuizAnswer>> callback) {
        List<AbstractMap.SimpleEntry<String, Object>> listKeyValue = new ArrayList<AbstractMap.SimpleEntry<String, Object>>();
        listKeyValue.add(new AbstractMap.SimpleEntry<String, Object>("quizItemId", this.getId()));
        listKeyValue.add(new AbstractMap.SimpleEntry<String, Object>("createdBy", createdBy));
        quizAnswerService.getAllByKeyValueList(listKeyValue, callback);
    }

    public boolean checkAnswer(int selectedOptionId) {
        return true;
    }

    public void Add(QuizOption option) {
        this.quizOptions.add(option);
    }
    public void Update(int position,QuizOption option) {
        this.quizOptions.add(position,option);
    }
    public void Delete(QuizOption option) {
        this.quizOptions.remove(option);
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
        if(!getQuizOptions().stream().anyMatch(m->m.isAnswer())){
        return "Atleast one check box is required";
        }
        return "";
    }




    private void notifyValidity() {
        //notifyPropertyChanged();
       notifyPropertyChanged(BR.contentError);
       notifyPropertyChanged(BR.optionsError);
      notifyPropertyChanged(BR.isAnswerError);
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
