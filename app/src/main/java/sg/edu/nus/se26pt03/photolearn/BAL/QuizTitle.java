package sg.edu.nus.se26pt03.photolearn.BAL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.service.ServiceCallback;

/**
 * Created by chen ping on 7/3/2018.
 */

public class QuizTitle extends Title {
    private List<QuizItem> quizItems;

    public QuizTitle() {
        setLearningSession(new LearningSession());
        quizItems = new ArrayList<QuizItem>();
    }

    public void copy(QuizTitle value) {
        //set learning session Id
        this.setId(value.getId());
        this.setTitle(value.getTitle());
        this.setCreatedBy(value.getCreatedBy());
    }

    public int getQuizScore(int userId) {
        return 0;
    }


    public void getPreviousItem() {
    }

    public void getNextItem() {
    }

    public void getCurrentItem() {

    }

    public void removeAllAnswer() {

    }



    @Override
    public void createItem(Item item, ServiceCallback<Item> callback) {

    }

    @Override
    public void updateItem(Item item, ServiceCallback<Boolean> callback) {

    }

    @Override
    public void deleteItem(String itemId, ServiceCallback<Boolean> callback) {

    }

    @Override
    public void getItems(ServiceCallback<List<Item>> callback) {

    }
}
