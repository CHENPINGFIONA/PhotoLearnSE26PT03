package sg.edu.nus.se26pt03.photolearn.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.application.UserActionCallback;
import sg.edu.nus.se26pt03.photolearn.application.UserActionListener;

/**
 * Created by MyatMin on 08/3/18.
 */
public class LearningSessionDetailFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learning_session_detail, container, false);
    }

    @Override
    public void onBefore(Event event, final UserActionCallback callback) {
        new AlertDialog.Builder(getContext())
                .setTitle("Title")
                .setMessage("Your unsaved data will be lost.\n Are you sure you wanted to continue?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        callback.onPass();
                    }})
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onReject();
                    }
                }).show();
    }


}
