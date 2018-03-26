package sg.edu.nus.se26pt03.photolearn.service;

import com.google.firebase.database.DatabaseError;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.Coordinate;
import sg.edu.nus.se26pt03.photolearn.BAL.Item;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningItem;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.Title;
import sg.edu.nus.se26pt03.photolearn.DAL.LearningItemDAO;
import sg.edu.nus.se26pt03.photolearn.database.LearningItemRepo;
import sg.edu.nus.se26pt03.photolearn.database.RepoCallback;

/**
 * Created by c.banisetty on 3/22/2018.
 */

public class LearningItemService extends BaseService<Item, LearningItemDAO> {
    private LearningItemRepo learningItemRepo = new LearningItemRepo();
    private Title title;

    public LearningItemService(Title title) {
        setBaseRepo(learningItemRepo);
        this.title=title;
        setDAOConversion(new DAOConversion<Item, LearningItemDAO>() {
            @Override
            public LearningItem convertFromDAO(LearningItemDAO value) {

                LearningItem result = new LearningItem(title);
                result.setContent(value.getContent());
                result.setCoordinate(new Coordinate(value.getLatitude() == null ? 0 : value.getLatitude(), value.getLongitude() == null ? 0 : value.getLongitude()));
                result.setPhotoURL(value.getPhotoURL());
                result.setCreatedBy(value.getCreatedBy());
                result.setId(value.getId());
                result.setTimestamp(new Date(value.getTimestamp()));
                return result;

            }

            @Override
            public LearningItemDAO convertToDAO(Item value) {
                LearningItemDAO result = new LearningItemDAO();
                LearningItem source = (LearningItem) value;
                result.setContent(source.getContent());
                result.setLatitude(source.getCoordinate() == null ? 0 : source.getCoordinate().getLatitude());
                result.setLongitude(source.getCoordinate() == null ? 0 : source.getCoordinate().getLongitude());
                result.setPhotoURL(source.getPhotoURL() == null ? "" : source.getPhotoURL());
                result.setCreatedBy(source.getCreatedBy() == null ? "" : source.getCreatedBy());
                result.setLearningTitleId(value.getTitle().getId());
                result.setId(source.getId() == null ? "" : source.getId());
                result.setTimestamp(source.getTimestamp().getTime());
                return result;
            }
        });
    }

    public void getAllByLearningTitleId(String id, final ServiceCallback<List<Item>> callback) {
        learningItemRepo.getAllByLearningTitleID(id, new RepoCallback<List<LearningItemDAO>>() {
            @Override
            public void onComplete(List<LearningItemDAO> data) {
                callback.onComplete(getDAOConversion().convertFromDAO(data));
            }

            @Override
            public void onError(DatabaseError databaseError) {
                callback.onError(databaseError.getCode(), databaseError.getMessage(), databaseError.getDetails());
            }
        });
    }


}
