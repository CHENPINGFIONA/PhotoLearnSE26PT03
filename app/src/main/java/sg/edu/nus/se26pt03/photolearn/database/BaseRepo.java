package sg.edu.nus.se26pt03.photolearn.database;

import android.util.Log;

import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.QuizTitle;
import sg.edu.nus.se26pt03.photolearn.DAL.BaseDAO;
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
        DatabaseReference databaseReference = mDatabaseRef.child(t.getId());
        if (databaseReference == null) {
            callback.onComplete(false);
        } else {
            databaseReference.setValue(t, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        callback.onComplete(false);
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
                if (databaseError != null) {
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
    public void getAllByKeyValue(String key, Object value, final RepoCallback<List<T>> callback) {
        Query query = mDatabaseRef.orderByChild(key);
        if (value instanceof Double) {
            query = query.equalTo((Double) value);
        }
        else if (value instanceof String) {
            query = query.equalTo((String) value);
        }
        else if (value instanceof Boolean) {
            query = query.equalTo((Boolean) value);
        }

        query.addListenerForSingleValueEvent(
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

    protected T getValue(DataSnapshot dataSnapshot) {
        T t = dataSnapshot.getValue(tClass);
        if (t != null) {
            t.setId(dataSnapshot.getKey());
        }
        return t;
    }

}
