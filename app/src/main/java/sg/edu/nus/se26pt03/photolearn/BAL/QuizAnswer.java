package sg.edu.nus.se26pt03.photolearn.BAL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chen ping on 7/3/2018.
 */

public class QuizAnswer {
    private int ID;
    private int quizItemID;
    private Participant participant;
    private Date timestamp;

    public List<QuizOption> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(List<QuizOption> answerOptions) {
        this.answerOptions = answerOptions;
    }

    private List<QuizOption> answerOptions;



}
