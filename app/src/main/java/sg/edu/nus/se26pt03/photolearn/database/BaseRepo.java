package sg.edu.nus.se26pt03.photolearn.database;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    public T save(final T t) {
        String key = mDatabaseRef.push().getKey();
        t.setId(key);
        mDatabaseRef.child(key).setValue(t);
        return t;
    }

    @Override
    public T update(T t, final ICallback<Boolean> iCallback) {
        DatabaseReference databaseReference = mDatabaseRef.child(t.getId());
        if (databaseReference == null) {
            iCallback.onCallback(false);
        } else {
            databaseReference.setValue(t, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        iCallback.onCallback(false);
                    } else {
                        iCallback.onCallback(true);
                    }
                }
            });
        }
        return t;
    }

    @Override
    public void delete(T t, final ICallback<Boolean> iCallback) {
        DatabaseReference databaseReference = mDatabaseRef.child(t.getId());
        if (databaseReference == null) {
            iCallback.onCallback(false);
        }

        databaseReference.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    iCallback.onCallback(false);
                } else {
                    iCallback.onCallback(true);
                }
            }
        });
    }

    @Override
    public void deleteById(String id, final ICallback<Boolean> iCallback) {
        DatabaseReference databaseReference = mDatabaseRef.child(id);
        if (databaseReference == null) {
            iCallback.onCallback(false);
        }

        databaseReference.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    iCallback.onCallback(false);
                } else {
                    iCallback.onCallback(true);
                }
            }
        });
    }

    @Override
    public T getById(String id, final IListCallback<T> iListCallback) {
        final List<T> result = new ArrayList<>();
        mDatabaseRef.child(id).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //use the onDataChange() method to read a static snapshot of the contents at a given path
                        // Get Post object and use the values to update the UI
                        result.add(getValue(dataSnapshot));
                        iListCallback.onCallback(result);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                    }
                });
        //why use list here? because value inside EventListener can not be directly passed to outside(need final as Modifier)
        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    @Override
    public Collection<T> getAll(final RepoCallback<T> callback) {
        final List<T> result = new ArrayList<>();
        mDatabaseRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //use the onDataChange() method to read a static snapshot of the contents at a given path
                        // Get Post object and use the values to update the UI
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                            result.add(getValue(childDataSnapshot));
                        }
                        callback.onRecieved(result);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                    }
                });
        return result;
    }

    protected T getValue(DataSnapshot dataSnapshot) {
        T t = dataSnapshot.getValue(tClass);
        if (t != null) {
            t.setId(dataSnapshot.getKey());
        }
        return t;
    }

}
