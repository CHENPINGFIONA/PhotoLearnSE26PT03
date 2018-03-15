package sg.edu.nus.se26pt03.photolearn.fragment;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.preference.PreferenceManager;
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
import android.widget.TextView;

import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.QuizTitle;
import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.adapter.QuizTitleListAdapter;
import sg.edu.nus.se26pt03.photolearn.application.App;
import sg.edu.nus.se26pt03.photolearn.enums.AccessMode;
import sg.edu.nus.se26pt03.photolearn.enums.UserRole;
import sg.edu.nus.se26pt03.photolearn.utility.ConstHelper;

public class QuizTitleListFragment extends Fragment {
    private TextView tvEmpty;
    private QuizTitleListAdapter quizTitleListAdapter;
    private Dialog dialog;
    private int role;
    private String sessionId;
    private String userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_quiz_title_list, container, false);
        RecyclerView rvQuizTitle = (RecyclerView) fragmentView.findViewById(R.id.rv_quiz_title);
        //  sessionId = this.getArguments().getString("sessionId");
        sessionId = "1";
        userId = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(ConstHelper.SharedPreferences_User_Id, "0");
        role = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt(ConstHelper.SharedPreferences_User_Id, UserRole.toInt(UserRole.TRAINER));

        tvEmpty = (TextView) fragmentView.findViewById(R.id.tv_empty_value);

        List<QuizTitle> titles = App.session.getQuizTitles(sessionId);

        quizTitleListAdapter = new QuizTitleListAdapter(titles, sessionId);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvQuizTitle.setLayoutManager(mLayoutManager);
        rvQuizTitle.setItemAnimator(new DefaultItemAnimator());
        rvQuizTitle.setAdapter(quizTitleListAdapter);

        FloatingActionButton floatingActionButton =
                (FloatingActionButton) fragmentView.findViewById(R.id.fab_add);
        floatingActionButton.setVisibility(role == UserRole.toInt(UserRole.TRAINER) ? View.VISIBLE : View.GONE);
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
//quizTitleListAdapter.refreshQuizTitles();
        tvEmpty.setVisibility(quizTitleListAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    private void showDialogue(Context context) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_title);
        dialog.show();

        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        tvTitle.setText(R.string.quiz_title);

        final EditText etContent = (EditText) dialog.findViewById(R.id.et_content);
        etContent.setHint(R.string.enter_title);

        Button btnSave = (Button) dialog.findViewById(R.id.btn_save);
        Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.session.createQuizTitle(new QuizTitle(sessionId, etContent.getText().toString(), userId));
                quizTitleListAdapter.refreshQuizTitles();
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
