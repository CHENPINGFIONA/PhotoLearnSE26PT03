package sg.edu.nus.se26pt03.photolearn.service;

import android.animation.TypeConverter;

import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.DAL.LearningSessionDAO;
import sg.edu.nus.se26pt03.photolearn.database.IListCallback;
import sg.edu.nus.se26pt03.photolearn.database.LearningSessionRepo;
import sg.edu.nus.se26pt03.photolearn.database.RepoCallback;

/**
 * Created by Drake on 21/3/18.
 */

public class LearningSessionService {

    private LearningSessionRepo learningSessionRepo = new LearningSessionRepo();

    private LearningSession convert(LearningSessionDAO value) {
        LearningSession learningSession = new LearningSession();
        learningSession.setId(value.getId());
        learningSession.setCourseDate(value.getCourseDate());
        learningSession.setCourseCode(value.getCourseCode());
        learningSession.setCourseName(value.getCourseName());
        learningSession.setModuleNumber(value.getModuleNumber());
        learningSession.setModuleName(value.getModuleName());
        return learningSession;
    }

    private LearningSessionDAO convert(LearningSession value) {
        LearningSessionDAO learningSessionDAO = new LearningSessionDAO();
        learningSessionDAO.setId(value.getId());
        learningSessionDAO.setCourseDate(value.getCourseDate());
        learningSessionDAO.setCourseCode(value.getCourseCode());
        learningSessionDAO.setCourseName(value.getCourseName());
        learningSessionDAO.setModuleNumber(value.getModuleNumber());
        learningSessionDAO.setModuleName(value.getModuleName());
        return learningSessionDAO;
    }

    public  void save(LearningSession learningSession, final ServiceCallback<LearningSession> callback) {
        learningSessionRepo.save(convert(learningSession), new RepoCallback<LearningSessionDAO>() {
            @Override
            public void onComplete(LearningSessionDAO data) {
                callback.onComplete(convert(data));
            }

            @Override
            public void onError(DatabaseError databaseError) {
                callback.onError(databaseError.getCode(), databaseError.getMessage(), databaseError.getDetails());
            }
        });
    }

    public  void update(LearningSession learningSession, final ServiceCallback<Boolean> callback) {
        learningSessionRepo.update(convert(learningSession), new RepoCallback<Boolean>() {
            @Override
            public void onComplete(Boolean data) {
                callback.onComplete(data);
            }

            @Override
            public void onError(DatabaseError databaseError) {
                callback.onError(databaseError.getCode(), databaseError.getMessage(), databaseError.getDetails());
            }
        });
    }

    public void getAll(final ServiceCallback<List<LearningSession>> callback) {
        learningSessionRepo.getAll(new RepoCallback<List<LearningSessionDAO>>() {
            @Override
            public void onComplete(List<LearningSessionDAO> itemList) {
                List<LearningSession> result = new ArrayList<LearningSession>();
                for (LearningSessionDAO learningSessionDAO: itemList) {
                    result.add(convert(learningSessionDAO));
                }
                callback.onComplete(result);
            }

            @Override
            public void onError(DatabaseError databaseError) {
                callback.onError(databaseError.getCode(), databaseError.getMessage(), databaseError.getDetails());
            }
        });
    }

    public void getById(String id,final ServiceCallback<LearningSession> callback) {
        learningSessionRepo.getById(id, new RepoCallback<LearningSessionDAO>() {
            @Override
            public void onComplete(LearningSessionDAO data) {
                callback.onComplete(convert(data));
            }

            @Override
            public void onError(DatabaseError databaseError) {
                callback.onError(databaseError.getCode(), databaseError.getMessage(), databaseError.getDetails());
            }
        });
    }


    public void delete(LearningSession learningSession, final ServiceCallback<Boolean> callback) {
        deleteById(learningSession.getId(), callback);
    }

    public void deleteById(String id, final ServiceCallback<Boolean> callback) {
        learningSessionRepo.deleteById(id, new RepoCallback<Boolean>() {
            @Override
            public void onComplete(Boolean data) {
                callback.onComplete(data);
            }

            @Override
            public void onError(DatabaseError databaseError) {
                callback.onError(databaseError.getCode(), databaseError.getMessage(), databaseError.getDetails());
            }
        });
    }


}
