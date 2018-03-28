package sg.edu.nus.se26pt03.photolearn.BAL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen ping on 7/3/2018.
 */

public class QuizItem extends Item implements Serializable{
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
    public void Update(int position,QuizOption option) {
        this.quizOptions.add(position,option);
    }
    public void Delete(QuizOption option) {
        this.quizOptions.remove(option);
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
