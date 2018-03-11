package sg.edu.nus.se26pt03.photolearn.BAL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chen ping on 7/3/2018.
 */

public class QuizTitle extends Title {
    private List<QuizItem> quizItems;

    public QuizTitle() {
        quizItems = new ArrayList<QuizItem>();
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
    public void createItem(Item item) {

    }

    @Override
    public void updateItem(Item item) {

    }

    @Override
    public void deleteItem(int itemId) {

    }

    @Override
    public List<Item> getItems() {
        return null;
    }
}
