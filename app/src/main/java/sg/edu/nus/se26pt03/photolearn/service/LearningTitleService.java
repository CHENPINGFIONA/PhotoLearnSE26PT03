package sg.edu.nus.se26pt03.photolearn.service;

import com.google.firebase.database.DatabaseError;

import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.DAL.LearningTitleDAO;
import sg.edu.nus.se26pt03.photolearn.database.LearningTitleRepo;
import sg.edu.nus.se26pt03.photolearn.database.RepoCallback;
import sg.edu.nus.se26pt03.photolearn.utility.DateConversionHelper;

/**
 * Created by MyatMin on 21/3/18.
 */

public class LearningTitleService extends BaseService<LearningTitle, LearningTitleDAO> {
    private LearningTitleRepo learningTitleRepo = new LearningTitleRepo();

    public LearningTitleService() {
        setBaseRepo(learningTitleRepo);
        setDAOConversion(new DAOConversion<LearningTitle, LearningTitleDAO>() {
            @Override
            public LearningTitle convertFromDAO(LearningTitleDAO value) {
                LearningTitle learningTitle = new LearningTitle();
                learningTitle.setId(value.getId());
                learningTitle.getLearningSession().setId(value.getLearningSessionId());
                learningTitle.setTitle(value.getTitle());
                learningTitle.setCreatedBy(value.getCreatedBy());
                learningTitle.setTimestamp(value.getTimestamp());
                return learningTitle;
            }

            @Override
            public LearningTitleDAO convertToDAO(LearningTitle value) {
                LearningTitleDAO learningTitleDAO = new LearningTitleDAO();
                learningTitleDAO.setId(value.getId());
                learningTitleDAO.setLearningSessionId(value.getLearningSession().getId());
                learningTitleDAO.setTitle(value.getTitle());
                learningTitleDAO.setCreatedBy(value.getCreatedBy());
                learningTitleDAO.setTimestamp(value.getTimestamp());
                return learningTitleDAO;
            }
        });
    }

    public void getAllByLearningSessionId(String id, final ServiceCallback<List<LearningTitle>> callback) {
        learningTitleRepo.getAllByKeyValue("LearningSessionId", id, new RepoCallback<List<LearningTitleDAO>>() {
            @Override
            public void onComplete(List<LearningTitleDAO> data) {
                callback.onComplete(getDAOConversion().convertFromDAO(data));

            }

            @Override
            public void onError(DatabaseError databaseError) {
                callback.onError(databaseError.getCode(), databaseError.getMessage(), databaseError.getDetails());
            }
        });
    }
}
