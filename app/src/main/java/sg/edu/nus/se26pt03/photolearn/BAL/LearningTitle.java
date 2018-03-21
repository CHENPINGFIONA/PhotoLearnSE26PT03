package sg.edu.nus.se26pt03.photolearn.BAL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chen ping on 7/3/2018.
 */

public class LearningTitle extends Title implements Serializable {
    private List<LearningItem> learningItems;

    public LearningTitle() {
        setLearningSession(new LearningSession());
        learningItems = new ArrayList<LearningItem>();
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
