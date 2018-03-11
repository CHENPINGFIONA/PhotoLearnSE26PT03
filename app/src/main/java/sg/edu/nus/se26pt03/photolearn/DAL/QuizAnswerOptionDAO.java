package sg.edu.nus.se26pt03.photolearn.DAL;

/**
 * Created by chen ping on 11/3/2018.
 */

public class QuizAnswerOptionDAO {
    private int AnswerId;
    private int OptionId;

    public int getAnswerId() {
        return AnswerId;
    }

    public void setAnswerId(int answerId) {
        AnswerId = answerId;
    }

    public int getOptionId() {
        return OptionId;
    }

    public void setOptionId(int optionId) {
        OptionId = optionId;
    }
}
