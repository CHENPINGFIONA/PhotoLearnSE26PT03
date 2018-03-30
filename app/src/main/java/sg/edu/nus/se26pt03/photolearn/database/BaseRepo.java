package sg.edu.nus.se26pt03.photolearn.database;

import android.provider.ContactsContract;
import android.util.Log;

import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sg.edu.nus.se26pt03.photolearn.BAL.QuizTitle;
import sg.edu.nus.se26pt03.photolearn.DAL.BaseDAO;
import sg.edu.nus.se26pt03.photolearn.application.App;
import sg.edu.nus.se26pt03.photolearn.fragment.LoginFragment;

/**
 * Created by chen ping on 11/3/2018.
 * Restructured by MyatMin on 12/3/2018.
 */

public class BaseRepo<T extends BaseDAO> implements AutoCloseable, IRepository<T> {
    protected DatabaseReference mDatabaseRef;
    private final Class<T> tClass;

    public BaseRepo(Class tClass) {
        if (mDatabaseRef == null) {
            mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        }
        this.tClass = tClass;
    }

    @Override
    public void close() throws Exception {
        FirebaseDatabase.getInstance().goOffline();
    }

    @Override
    public void save(final T t, final RepoCallback<T> callback) {
        String key = mDatabaseRef.push().getKey();
        t.setId(key);
        mDatabaseRef.child(key).setValue(t);
        mDatabaseRef.child(key).child("timestamp").setValue(ServerValue.TIMESTAMP);
        mDatabaseRef.child(key).child("createdBy").setValue(App.getCurrentUser().getId());
        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callback.onComplete(t);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError);
            }
        });

    }

    @Override
    public void update(T t, final RepoCallback<Boolean> callback) {
        Map<String, Object> map = new HashMap<>();
        Class<?> c = t.getClass();
        for (Method m: c.getDeclaredMethods()) {
            if (m.getName().startsWith("get")) {
                try {
                    map.put(m.getName().replace("get" + m.getName().substring(3,4), m.getName().substring(3,4).toLowerCase()), m.invoke(t));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        DatabaseReference databaseReference = mDatabaseRef.child(t.getId());
        if (databaseReference == null) {
            callback.onComplete(false);
        } else {
            databaseReference.updateChildren(map, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError == null) {
                        callback.onComplete(true);
                    } else {
                        callback.onError(databaseError);
                    }
                }
            });
        }
    }

    @Override
    public void delete(T t, RepoCallback<Boolean> callback) {
        deleteById(t.getId(),callback);
    }

    @Override
    public void deleteById(String id, final RepoCallback<Boolean> callback) {
        DatabaseReference databaseReference = mDatabaseRef.child(id);
//        if (databaseReference == null) {
//            iCallback.onCallback(false);
//        }

        databaseReference.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    callback.onComplete(true);
                } else {
                    callback.onError(databaseError);
                }
            }
        });
    }

    @Override
    public void getById(String id, final RepoCallback<T> callback) {
//        final List<T> result = new ArrayList<>();
        mDatabaseRef.child(id).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //use the onDataChange() method to read a static snapshot of the contents at a given path
                        // Get Post object and use the values to update the UI
//                        result.add(getValue(dataSnapshot));
                        callback.onComplete(getValue(dataSnapshot));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                        callback.onError(databaseError);
                    }
                });
//        //why use list here? because value inside EventListener can not be directly passed to outside(need final as Modifier)
//        if (result.isEmpty()) {
//            return null;
//        } else {
//            return result.get(0);
//        }
    }

    @Override
    public void getAll(final RepoCallback<List<T>> callback) {
        mDatabaseRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<T> result = new ArrayList<>();
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                            result.add(getValue(childDataSnapshot));
                        }
                        callback.onComplete(result);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        callback.onError(databaseError);
                    }
                });
    }

    @Override
    public void getAllByKeyValueList(List<AbstractMap.SimpleEntry<String, Object>> listKeyValue, final RepoCallback<List<T>> callback) {
        if (listKeyValue.size() >= 1) {
            getAllByKeyValue(listKeyValue.get(0).getKey(), listKeyValue.get(0).getValue(), new RepoCallback<List<T>>() {
                @Override
                public void onComplete(List<T> data) {
                    List<T> result = data;
                    if (result != null) {
                        for(int i = 1; i < listKeyValue.size(); i++) {
                            for (T t : result) {
                                if (!isValid(t, listKeyValue.get(i).getKey(), listKeyValue.get(i).getValue()))
                                {
                                    result.remove(t);
                                }
                            }
                        }
                    }
                    callback.onComplete(result);
                }

                @Override
                public void onError(DatabaseError databaseError) {
                    callback.onError(databaseError);
                }
            });
        }
    }

    @Override
    public void getAllByKeyValue(String key, Object value, final RepoCallback<List<T>> callback) {
        String[] keys = key.split("\\.");

        Query query = mDatabaseRef.orderByChild(keys[0]);
        if (keys.length == 1) {
            if (value instanceof Double) {
                query = query.equalTo((Double) value);
            } else if (value instanceof String) {
                query = query.equalTo((String) value);
            } else if (value instanceof Boolean) {
                query = query.equalTo((Boolean) value);
            }
        }
        query.addValueEventListener(
            new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<T> result = new ArrayList<>();
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                        if (keys.length == 1) result.add(getValue(childDataSnapshot));
                        else {
                            if (isValid(childDataSnapshot,key, value)) {
                                result.add(getValue(childDataSnapshot));
                            }
                        }
                    }
                    callback.onComplete(result);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    callback.onError(databaseError);
                }
            });
    }

    private boolean isValid(Object object, String key, Object value) {
        String[] keys = key.split("\\.");
        Class<?> c = object.getClass();
        try {
            Method m = c.getDeclaredMethod("get"+ keys[0].substring(0,1).toUpperCase() + keys[0].substring(1), null);
            Object readValue = null;
            try {
                readValue = m.invoke(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                return false;
            }
            if (keys.length == 1) {
                return value.equals(readValue);
            }
            if (readValue instanceof Iterable) {
                for (Object childValue : (Iterable) readValue) {
                    String childKey = key.substring(key.indexOf(".") + 1);
                    if (isValid(childValue, childKey, value)) {
                        return true;
                    }
                }
            }
            else {
                String childKey = key.substring(key.indexOf(".") + 1);
                if (isValid(readValue, childKey, value)) {
                    return true;
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return  false;
        }

        return false;
    }

    private boolean isValid(DataSnapshot dataSnapshot, String key, Object value) {
        String[] keys = key.split("\\.");
        if (keys.length == 1) {
            return dataSnapshot.getKey().equals(keys[0]) && dataSnapshot.getValue() == value;
        }
        for(DataSnapshot childDataSnapshot: dataSnapshot.child(keys[0]).getChildren()) {
            String childKey = key.substring(key.indexOf(".") + 1);
            if (isValid(childDataSnapshot, childKey, value )) {
                return true;
            }
        }
        return false;
    }

    protected T getValue(DataSnapshot dataSnapshot) {
        T t = dataSnapshot.getValue(tClass);
        if (t != null) {
            t.setId(dataSnapshot.getKey());
        }
        return t;
    }


    @Override
    public void setValueByKey(String key, Object value, final RepoCallback<Boolean> callback) {
        String[] keys = key.split("\\.");
        DatabaseReference databaseReference = mDatabaseRef.child(keys[0]);
        for (int i = 1; i < keys.length; i++) {
            databaseReference = databaseReference.child(keys[i]);
        }
        databaseReference.setValue(value);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callback.onComplete(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError(databaseError);
            }
        });
    }

}
