package sg.edu.nus.se26pt03.photolearn.fragment;

import android.app.Dialog;
import android.graphics.Canvas;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.QuizTitle;
import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.adapter.QuizTitleListAdapter;
import sg.edu.nus.se26pt03.photolearn.application.App;
import sg.edu.nus.se26pt03.photolearn.controller.SwipeController;
import sg.edu.nus.se26pt03.photolearn.enums.AccessMode;
import sg.edu.nus.se26pt03.photolearn.enums.UserRole;
import sg.edu.nus.se26pt03.photolearn.service.QuizTitleService;
import sg.edu.nus.se26pt03.photolearn.service.ServiceCallback;
import sg.edu.nus.se26pt03.photolearn.utility.ConstHelper;

public class QuizTitleListFragment extends BaseFragment {
    private QuizTitleListAdapter quizTitleListAdapter;
    private int role;
    private String sessionId;
    private int mode;
    private String userId;

    private QuizTitleService quizTitleService = new QuizTitleService();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View fragmentView = inflater.inflate(R.layout.fragment_quiz_title_list, container, false);
        //  sessionId = this.getArguments().getString("sessionId");
        sessionId = "1";
        mode = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt(ConstHelper.SharedPreferences_Access_Mode, AccessMode.toInt(AccessMode.EDIT));
        role = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt(ConstHelper.SharedPreferences_User_Id, UserRole.toInt(UserRole.TRAINER));
        userId = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(ConstHelper.SharedPreferences_User_Id, "0");

        return inflater.inflate(R.layout.fragment_quiz_title_list, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadQuizTitleList();
    }

    private void loadQuizTitleList() {
        quizTitleService.getAllByKeyValue("learningSessionId", App.session.getId(), new ServiceCallback<List<QuizTitle>>() {
            @Override
            public void onComplete(List<QuizTitle> data) {
                final List<QuizTitle> quizTitleList = data;
                quizTitleListAdapter = new QuizTitleListAdapter(quizTitleList, new QuizTitleListAdapter.QuizTitleViewHolderClick() {
                    @Override
                    public void onItemClick(QuizTitleListAdapter.QuizTitleViewHolder viewHolder) {
                        onLoad(quizTitleList.get(viewHolder.getAdapterPosition()), null);
                    }
                });
                setupViews();
                setupControls();
            }

            @Override
            public void onError(int code, String message, String details) {

            }
        });
    }

    private void setupViews() {
        // View fragmentView = inflater.inflate(R.layout.fragment_quiz_title_list, container, false);
        RecyclerView rvQuizTitle = getView().findViewById(R.id.rv_quiz_title);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvQuizTitle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvQuizTitle.setAdapter(quizTitleListAdapter);

        TextView tvEmpty = getView().findViewById(R.id.tv_empty_value);
        tvEmpty.setVisibility(quizTitleListAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);

        final SwipeController swipeController = new SwipeController(0, (App.currentAccessMode == AccessMode.EDIT ? R.layout.partial_swipe_item : 0)) {
            @Override
            public void onRevealInflated(View view, int position) {
                if (view instanceof LinearLayout) {
                    LinearLayout linearLayout = (LinearLayout) ((LinearLayout) view).getChildAt(0);
                    // linearLayout.findViewWithTag("edit").setVisibility(View.GONE);
                    // inearLayout.findViewWithTag("delete").setVisibility(View.GONE);
                    linearLayout.findViewWithTag("edit").setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            // showDialogue(title);
                        }
                    });
                }
            }

            @Override
            public void onClicked(Object tag, int position) {
                switch (tag.toString()) {
                    case "delete":
                        quizTitleService.deleteById(quizTitleListAdapter.quizTitleList.get(position).getId(), null);
                        App.session.removeQuizTitle(quizTitleListAdapter.quizTitleList.get(position));
                        quizTitleListAdapter.notifyItemRemoved(position);
                        quizTitleListAdapter.notifyItemRangeChanged(position, quizTitleListAdapter.getItemCount());
//                        App.session.deleteQuizTitle(quizTitleListAdapter.quizTitleList.get(position), new ICallback<Boolean>() {
//                            @Override
//                            public void onCallback(Boolean item) {
//                                loadQuizTitleList();
//                            }
//                        });
                        break;
                    case "edit":
                        //onEdit(quizSessionListAdapter.quizSessionList.get(position), null);
                        break;
                }
            }
        };
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(rvQuizTitle);
        rvQuizTitle.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }

    private void setupControls() {
        FloatingActionButton floatingActionButton = getView().findViewById(R.id.fab_quiztitlelist);
        //floatingActionButton.setVisibility((mode == AccessMode.toInt(AccessMode.EDIT) && role == UserRole.toInt(UserRole.PARTICIPENT)) ? View.VISIBLE : View.GONE);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogue(null);
            }
        });
    }

    private void showDialogue(QuizTitle title) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_title);
        dialog.show();

        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        tvTitle.setText(R.string.quiz_title);

        final EditText etContent = (EditText) dialog.findViewById(R.id.et_content);
        etContent.setHint(R.string.enter_title);
        if (title != null) {
            etContent.setText(title.getTitle());
        }

        Button btnSave = (Button) dialog.findViewById(R.id.btn_save);
        Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuizTitle newTitle = new QuizTitle();
                newTitle.setLearningSession(App.session);
                newTitle.setTitle(etContent.getText().toString());
                newTitle.setCreatedBy(userId);
                newTitle.setTimestamp(new Date());

                App.session.addQuizTitle(newTitle);
                quizTitleService.save(newTitle,null);
                loadQuizTitleList();
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
