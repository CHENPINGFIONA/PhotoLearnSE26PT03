package sg.edu.nus.se26pt03.photolearn.service;

import com.google.firebase.database.DatabaseError;

import java.time.format.DateTimeFormatter;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.Coordinate;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningItem;
import sg.edu.nus.se26pt03.photolearn.DAL.LearningItemDAO;
import sg.edu.nus.se26pt03.photolearn.database.LearningItemRepo;
import sg.edu.nus.se26pt03.photolearn.database.RepoCallback;

/**
 * Created by c.banisetty on 3/22/2018.
 */

public class LearningItemService extends BaseService<LearningItem, LearningItemDAO> {
    private LearningItemRepo learningItemRepo = new LearningItemRepo();


    public LearningItemService() {
        setBaseRepo(learningItemRepo);
        setDAOConversion(new DAOConversion<LearningItem, LearningItemDAO>() {
            @Override
            public LearningItem convertFromDAO(LearningItemDAO value) {
                LearningItem result=new LearningItem();
                result.setContent(value.getContent());
                result.setCoordinate(new Coordinate(value.getLatitude()==null?0:value.getLatitude(),value.getLongitude()==null?0:value.getLongitude()));
                result.setPhotoURL(value.getPhotoURL());
                result.setCreatedBy(value.getCreatedBy());
                result.setId(value.getId());
                result.setTimestamp(value.getTimestamp());
                return result;

            }

            @Override
            public LearningItemDAO convertToDAO(LearningItem value) {
                LearningItemDAO result=new LearningItemDAO();
                result.setContent(value.getContent());
                result.setLatitude(value.getCoordinate()==null?0:value.getCoordinate().getLatitude());
                result.setLongitude(value.getCoordinate()==null?0:value.getCoordinate().getLongitude());
                result.setPhotoURL(value.getPhotoURL()==null?"":value.getPhotoURL());
                result.setCreatedBy(value.getCreatedBy()==null?"":value.getCreatedBy());
                result.setId(value.getId()==null?"":value.getId());
                result.setTimestamp(value.getTimestamp());
                return result;
            }
        });
    }
    public void getAllByLearningTitleId(String id, final ServiceCallback<List<LearningItem>> callback) {
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
