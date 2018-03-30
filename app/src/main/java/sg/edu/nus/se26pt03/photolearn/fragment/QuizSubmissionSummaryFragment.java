package sg.edu.nus.se26pt03.photolearn.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.Item;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizAnswer;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizItem;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizOption;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizTitle;
import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.adapter.QuizItemQuizAnswerListAdapter;
import sg.edu.nus.se26pt03.photolearn.application.App;
import sg.edu.nus.se26pt03.photolearn.service.ServiceCallback;

/**
 * Created by MyatMin on 08/3/18.
 */
public class QuizSubmissionSummaryFragment extends BaseFragment {
    private QuizItemQuizAnswerListAdapter quizItemQuizAnswerListAdapter;
    private QuizTitle quizTitle;
    private List<AbstractMap.SimpleEntry<QuizItem, QuizAnswer>> listQuizItemQuizAnswer;
    private SwipeRefreshLayout srf_quizitem_quizanswer;


    public static QuizSubmissionSummaryFragment newInstance(QuizTitle quizTitle) {
        QuizSubmissionSummaryFragment quizSubmissionSummaryFragment = new QuizSubmissionSummaryFragment();
        Bundle args = new Bundle();
        args.putSerializable("quizTitle", quizTitle);
        quizSubmissionSummaryFragment.setArguments(args);
        return quizSubmissionSummaryFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setupData();
        return inflater.inflate(R.layout.fragment_quiz_submission_summary, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setupControls();
    }

    private void setupData() {
        listQuizItemQuizAnswer = new ArrayList<AbstractMap.SimpleEntry<QuizItem, QuizAnswer>>();
        quizTitle = (QuizTitle) getArguments().getSerializable("quizTitle");
    }

    private void loadData() {
        srf_quizitem_quizanswer.setRefreshing(true);
        listQuizItemQuizAnswer.clear();
        quizTitle.getQuizItems(new ServiceCallback<List<Item>>() {
            @Override
            public void onComplete(List<Item> data) {
                for (Item item : data) {
                    ((QuizItem) item).getQuizAnswers(App.getCurrentUser().getId(), new ServiceCallback<List<QuizAnswer>>() {
                        @Override
                        public void onComplete(List<QuizAnswer> childData) {
                            if (childData.size() > 0) {
                                listQuizItemQuizAnswer.add(new AbstractMap.SimpleEntry<QuizItem, QuizAnswer>((QuizItem) item, childData.get(0)));
                                refreshViews();
                                if (listQuizItemQuizAnswer.size() >= data.size()) {
                                    srf_quizitem_quizanswer.setRefreshing(false);
                                }
                            }
                        }

                        @Override
                        public void onError(int code, String message, String details) {
                            srf_quizitem_quizanswer.setRefreshing(false);
                            displayErrorMessage(message);
                        }
                    });
                }
            }
            @Override
            public void onError(int code, String message, String details) {
                srf_quizitem_quizanswer.setRefreshing(false);
                displayErrorMessage(message);
            }
        });
    }

    private void refreshViews() {
        if(quizItemQuizAnswerListAdapter.getListQuizItemQuizAnswer() != listQuizItemQuizAnswer) {
            quizItemQuizAnswerListAdapter.setListQuizItemQuizAnswer(listQuizItemQuizAnswer);
        }
        quizItemQuizAnswerListAdapter.notifyDataSetChanged();
        TextView tv_score = getView().findViewById(R.id.tv_score);
        int totalQuestions = listQuizItemQuizAnswer.size();
        int totalCorrectAnswer = 0;
        for(AbstractMap.SimpleEntry<QuizItem, QuizAnswer> quizItemQuizAnswer: listQuizItemQuizAnswer) {
            for(QuizOption quizOption: quizItemQuizAnswer.getKey().getQuizOptions()) {
//                quizOption.
            }
        }
    }

    private void setupControls() {

        final RecyclerView rv_quizitem_quizanswer = getView().findViewById(R.id.rv_quizitem_quizanswer);
        rv_quizitem_quizanswer.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        quizItemQuizAnswerListAdapter = new QuizItemQuizAnswerListAdapter(listQuizItemQuizAnswer);
        rv_quizitem_quizanswer.setAdapter(quizItemQuizAnswerListAdapter);

        srf_quizitem_quizanswer =  getView().findViewById(R.id.srf_quizitem_quizanswer);
        srf_quizitem_quizanswer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        srf_quizitem_quizanswer.post(new Runnable() {
            @Override
            public void run() {
                srf_quizitem_quizanswer.setRefreshing(true);
                loadData();
            }
        });
    }

}
