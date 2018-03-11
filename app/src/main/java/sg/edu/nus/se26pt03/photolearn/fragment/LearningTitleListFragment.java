package sg.edu.nus.se26pt03.photolearn.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Date;

import sg.edu.nus.se26pt03.photolearn.DAL.LearningTitleDAO;
import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.adapter.LearningTitleListAdapter;
import sg.edu.nus.se26pt03.photolearn.database.LearningTitleRepo;
import sg.edu.nus.se26pt03.photolearn.utility.ConstHelper;

public class LearningTitleListFragment extends Fragment {
    private TextView tvEmpty;
    private LearningTitleListAdapter learningTitleListAdapter;
    private Dialog dialog;
    private int sessionId;
    private int mode;
    private int createdBy;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_learning_title_list, container, false);
        ListView learningTitleList = (ListView) fragmentView.findViewById(R.id.rv_learning_title);
        sessionId = this.getArguments().getInt("sessionId");
        mode = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt(ConstHelper.SharedPreferences_Access_Mode, 0);
        createdBy = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt(ConstHelper.SharedPreferences_User_Id, 0);

        tvEmpty = (TextView) fragmentView.findViewById(R.id.tv_empty_value);
        learningTitleListAdapter = new LearningTitleListAdapter(getActivity(), sessionId, mode, createdBy);

        learningTitleList.setAdapter(learningTitleListAdapter);
        FloatingActionButton floatingActionButton =
                (FloatingActionButton) fragmentView.findViewById(R.id.fab_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogue(getContext());
            }
        });

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        learningTitleListAdapter.refreshLearningTitles();
        tvEmpty.setVisibility(learningTitleListAdapter.getCount() == 0 ? View.VISIBLE : View.GONE);
    }

    private void showDialogue(Context context) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_learning_title);
        dialog.show();

        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        tvTitle.setText(R.string.learning_title);

        final EditText etContent = (EditText) dialog.findViewById(R.id.et_content);
        etContent.setHint(R.string.enter_title);

        Button btnSave = (Button) dialog.findViewById(R.id.btn_save);
        Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try (LearningTitleRepo repo = new LearningTitleRepo()) {
                    LearningTitleDAO learningTitleDao = new LearningTitleDAO();
                    learningTitleDao.setTitle(etContent.toString());
                    learningTitleDao.setSessionId(sessionId);
                    learningTitleDao.setCreatedBy(createdBy);
                    learningTitleDao.setTimestamp(new Date());

                    repo.save(learningTitleDao);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
