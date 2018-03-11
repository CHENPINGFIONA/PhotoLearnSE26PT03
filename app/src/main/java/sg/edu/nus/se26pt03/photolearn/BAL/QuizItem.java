package sg.edu.nus.se26pt03.photolearn.BAL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chen ping on 7/3/2018.
 */

public class QuizItem extends Item{
    private List<QuizOption> options;

    public QuizItem() {
        options = new ArrayList<QuizOption>();
    }

    public boolean checkAnswer(int selectedOptionId) {
        return true;
    }

    public  void createQuizOption(QuizOption option){

    }
    public  void updateQuizOption(QuizOption option){

    }
    public  void deleteQuizOption(int optionId){

    }
    public List<QuizOption> getOptions(){
        return  null;
    }

    public void createQuizAnswer(QuizAnswer answer, int userId) {
    }

}
