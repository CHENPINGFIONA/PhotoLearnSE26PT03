package sg.edu.nus.se26pt03.photolearn.BAL;

import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.service.LearningItemService;
import sg.edu.nus.se26pt03.photolearn.service.ServiceCallback;

/**
 * Created by chen ping on 7/3/2018.
 */

public class LearningTitle extends Title implements Serializable {
    private List<LearningItem> learningItems;
    private LearningItemService learningItemService = new LearningItemService();

    public LearningTitle() {
        setLearningSession(new LearningSession());
        learningItems = new ArrayList<LearningItem>();
    }

    public void copy(LearningTitle value) {
        //set learning session Id
        this.setId(value.getId());
        this.setTitle(value.getTitle());
        this.setCreatedBy(value.getCreatedBy());
    }

    @Override
    public void createItem(Item item, ServiceCallback<Item> callback) {
        try {
            learningItemService.save((LearningItem) item, callback);

        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void updateItem(Item item, ServiceCallback<Boolean> callback) {
        try {
            learningItemService.update((LearningItem) item, callback);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void deleteItem(String itemId, ServiceCallback<Boolean> callback) {
        try {

            learningItemService.deleteById(itemId, callback);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void getItems(ServiceCallback<List<Item>> callback) {
        try {
            learningItemService.getAllByLearningTitleId(this.getId(), callback);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
