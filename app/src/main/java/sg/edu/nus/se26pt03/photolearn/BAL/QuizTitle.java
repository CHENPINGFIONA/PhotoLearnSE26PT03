package sg.edu.nus.se26pt03.photolearn.BAL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.service.QuizItemService;
import sg.edu.nus.se26pt03.photolearn.service.ServiceCallback;

/**
 * Created by chen ping on 7/3/2018.
 */

public class QuizTitle extends Title implements Serializable {
    private List<QuizItem> quizItems;
    transient QuizItemService  quizItemService = new QuizItemService(this);

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
        try {
            quizItemService.save((QuizItem) item, callback);

        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void updateItem(Item item, ServiceCallback<Boolean> callback) {
        try {
            quizItemService.update((QuizItem) item, callback);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void deleteItem(String itemId, ServiceCallback<Boolean> callback) {
        try {

            quizItemService.deleteById(itemId, callback);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void getItems(ServiceCallback<List<Item>> callback) {
        try {
            quizItemService.getAllByQuizTitleId(this.getId(), callback);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void getQuizItems(ServiceCallback<List<Item>> callback) {
        try {
            quizItemService.getAllByKeyValue("quizTitleId", this.getId(), callback);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
