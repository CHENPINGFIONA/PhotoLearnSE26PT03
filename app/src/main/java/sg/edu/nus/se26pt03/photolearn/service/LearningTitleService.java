package sg.edu.nus.se26pt03.photolearn.service;

import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.Item;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.DAL.LearningItemDAO;
import sg.edu.nus.se26pt03.photolearn.DAL.LearningTitleDAO;
import sg.edu.nus.se26pt03.photolearn.database.LearningTitleRepo;
import sg.edu.nus.se26pt03.photolearn.database.RepoCallback;
import sg.edu.nus.se26pt03.photolearn.utility.DateConversionHelper;

/**
 * Created by part time team 3  on 21/3/18.
 */

public class LearningTitleService extends BaseService<LearningTitle, LearningTitleDAO> {
    private LearningTitleRepo learningTitleRepo = new LearningTitleRepo();
    private LearningSession learningSession;
    public LearningTitleService(LearningSession learningSession) {
        setBaseRepo(learningTitleRepo);
        this.learningSession=learningSession;
        setDAOConversion(new DAOConversion<LearningTitle, LearningTitleDAO>() {
            @Override
            public LearningTitle convertFromDAO(LearningTitleDAO value) {
                LearningTitle learningTitle = new LearningTitle();
                learningTitle.setId(value.getId());
                learningTitle.setLearningSession(learningSession);
                learningTitle.setTitle(value.getTitle());
                learningTitle.setCreatedBy(value.getCreatedBy());
                learningTitle.setTimestamp(new Date(value.getTimestamp()));
                return learningTitle;
            }

            @Override
            public LearningTitleDAO convertToDAO(LearningTitle value) {
                LearningTitleDAO learningTitleDAO = new LearningTitleDAO();
                learningTitleDAO.setId(value.getId());
                learningTitleDAO.setLearningSessionId(value.getLearningSession().getId());
                learningTitleDAO.setTitle(value.getTitle());
                learningTitleDAO.setCreatedBy(value.getCreatedBy());
                return learningTitleDAO;
            }
        });
    }

    public void getAllByLearningSessionId(String id, final String text, final ServiceCallback<List<LearningTitle>> callback) {
        learningTitleRepo.getAllByKeyValue("learningSessionId", id, new RepoCallback<List<LearningTitleDAO>>() {
            @Override
            public void onComplete(List<LearningTitleDAO> data) {
                List<LearningTitleDAO> daos = new ArrayList<>();
                for (LearningTitleDAO titleDao : data) {
                    if (titleDao.getTitle().contains(text)) {
                        daos.add(titleDao);
                    }
                }
                callback.onComplete(getDAOConversion().convertFromDAO(data));
            }

            @Override
            public void onError(DatabaseError databaseError) {
                callback.onError(databaseError.getCode(), databaseError.getMessage(), databaseError.getDetails());
            }
        });
    }
}
