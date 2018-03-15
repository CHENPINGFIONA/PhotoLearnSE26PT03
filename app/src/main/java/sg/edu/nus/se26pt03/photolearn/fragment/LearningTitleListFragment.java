package sg.edu.nus.se26pt03.photolearn.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.adapter.LearningTitleListAdapter;
import sg.edu.nus.se26pt03.photolearn.application.App;
import sg.edu.nus.se26pt03.photolearn.enums.AccessMode;
import sg.edu.nus.se26pt03.photolearn.utility.ConstHelper;

public class LearningTitleListFragment extends Fragment {
    private SearchView svSearchView;
    private TextView tvEmpty;
    private LearningTitleListAdapter learningTitleListAdapter;
    private Dialog dialog;
    private String sessionId;
    private String mode;
    private String userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // sessionId = this.getArguments().getString("sessionId");
        sessionId = "1";
        mode = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(ConstHelper.SharedPreferences_Access_Mode, AccessMode.EDIT.toString());
        userId = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(ConstHelper.SharedPreferences_User_Id, "0");

        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_learning_title_list, container, false);
        RecyclerView rvLearningTitle = (RecyclerView) fragmentView.findViewById(R.id.rv_learning_title);
        tvEmpty = (TextView) fragmentView.findViewById(R.id.tv_empty_value);

        List<LearningTitle> titles = App.session.getLearningTitles(sessionId, mode, userId, "");

        learningTitleListAdapter = new LearningTitleListAdapter(titles, sessionId, mode, userId);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvLearningTitle.setLayoutManager(mLayoutManager);
        rvLearningTitle.setItemAnimator(new DefaultItemAnimator());
        rvLearningTitle.setAdapter(learningTitleListAdapter);

        svSearchView = (SearchView) fragmentView.findViewById(R.id.sv_learningtitle);
        svSearchView.setVisibility(mode == AccessMode.VIEW.toString() ? View.VISIBLE : View.GONE);

        svSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                // your text view here
                callSearch(newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String text) {
                callSearch(text);
                return true;
            }

            private void callSearch(String text) {
                learningTitleListAdapter.refreshLearningTitles(text);
            }
        });

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
        learningTitleListAdapter.refreshLearningTitles(svSearchView.getQuery().toString());
        tvEmpty.setVisibility(learningTitleListAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    private void showDialogue(Context context) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_title);
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
                App.session.createLearningTitle(new LearningTitle(sessionId, etContent.getText().toString(), userId));
                learningTitleListAdapter.refreshLearningTitles("");
                dialog.dismiss();
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
