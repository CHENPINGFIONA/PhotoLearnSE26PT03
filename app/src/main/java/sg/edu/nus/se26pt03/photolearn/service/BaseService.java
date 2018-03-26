package sg.edu.nus.se26pt03.photolearn.service;

import com.google.firebase.database.DatabaseError;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.DAL.BaseDAO;
import sg.edu.nus.se26pt03.photolearn.database.BaseRepo;
import sg.edu.nus.se26pt03.photolearn.database.RepoCallback;

/**
 * Created by MyatMin on 21/3/18.
 */

public class BaseService<T, V extends BaseDAO> {
    private BaseRepo baseRepo;
    private DAOConversion<T,V> daoConversion;

    public void setBaseRepo(BaseRepo baseRepo) {
        this.baseRepo = baseRepo;
    }

    public void setDAOConversion(DAOConversion<T, V> daoConversion) {
        this.daoConversion = daoConversion;
    }

    public DAOConversion<T, V> getDAOConversion() {
        return daoConversion;
    }

    public  void save(T t, final ServiceCallback<T> callback) {
        baseRepo.save(daoConversion.convertToDAO(t), new RepoCallback<V>() {
            @Override
            public void onComplete(V data) {
                if (callback != null) callback.onComplete(daoConversion.convertFromDAO(data));
            }

            @Override
            public void onError(DatabaseError databaseError) {
                if (callback != null) callback.onError(databaseError.getCode(), databaseError.getMessage(), databaseError.getDetails());
            }
        });
    }

    public  void update(T t, final ServiceCallback<Boolean> callback) {
        baseRepo.update(daoConversion.convertToDAO(t), new RepoCallback<Boolean>() {
            @Override
            public void onComplete(Boolean data) {
                if (callback != null) callback.onComplete(data);
            }

            @Override
            public void onError(DatabaseError databaseError) {
                if (callback != null) callback.onError(databaseError.getCode(), databaseError.getMessage(), databaseError.getDetails());
            }
        });
    }

    public void getAll(final ServiceCallback<List<T>> callback) {
        baseRepo.getAll(new RepoCallback<List<V>>() {
            @Override
            public void onComplete(List<V> data) {
                if (callback != null) callback.onComplete(daoConversion.convertFromDAO(data));
            }

            @Override
            public void onError(DatabaseError databaseError) {
                if (callback != null) callback.onError(databaseError.getCode(), databaseError.getMessage(), databaseError.getDetails());
            }
        });
    }

    public void getById(String id,final ServiceCallback<T> callback) {
        baseRepo.getById(id, new RepoCallback<V>() {
            @Override
            public void onComplete(V data) {
                if (callback != null) callback.onComplete(daoConversion.convertFromDAO(data));
            }

            @Override
            public void onError(DatabaseError databaseError) {
                if (callback != null) callback.onError(databaseError.getCode(), databaseError.getMessage(), databaseError.getDetails());
            }
        });
    }


    public void delete(T t, final ServiceCallback<Boolean> callback) {
        deleteById(daoConversion.convertToDAO(t).getId(), callback);
    }

    public void deleteById(String id, final ServiceCallback<Boolean> callback) {
        baseRepo.deleteById(id, new RepoCallback<Boolean>() {
            @Override
            public void onComplete(Boolean data) {
                if (callback != null) callback.onComplete(data);
            }

            @Override
            public void onError(DatabaseError databaseError) {
                if (callback != null) callback.onError(databaseError.getCode(), databaseError.getMessage(), databaseError.getDetails());
            }
        });
    }


    public void getAllByKeyValue(String key, Object value, final ServiceCallback<List<T>> callback) {
        baseRepo.getAllByKeyValue(key, value, new RepoCallback<List<V>>() {
            @Override
            public void onComplete(List<V> data) {
                if (callback != null) callback.onComplete(daoConversion.convertFromDAO(data));
            }

            @Override
            public void onError(DatabaseError databaseError) {
                if (callback != null) callback.onError(databaseError.getCode(), databaseError.getMessage(), databaseError.getDetails());
            }
        });
    }

    public void setValueByKey(String key, Object value, final ServiceCallback<Boolean> callback) {
        if (key.split("\\.").length == 1) {
            if (callback != null) callback.onError(-1, "Child key must present.", null);
            return;
        }
        baseRepo.setValueByKey(key, value, new RepoCallback<Boolean>() {
            @Override
            public void onComplete(Boolean data) {
                if (callback != null) callback.onComplete(data);
            }

            @Override
            public void onError(DatabaseError databaseError) {
                if (callback != null) callback.onError(databaseError.getCode(), databaseError.getMessage(), databaseError.getDetails());
            }
        });
    }

}
