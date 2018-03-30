package sg.edu.nus.se26pt03.photolearn.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import sg.edu.nus.se26pt03.photolearn.BAL.Item;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningItem;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizAnswer;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizItem;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.Title;
import sg.edu.nus.se26pt03.photolearn.BAL.User;
import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.adapter.ItemFragmentPageAdapter;
import sg.edu.nus.se26pt03.photolearn.adapter.QuizItemFragmentPageAdapter;
import sg.edu.nus.se26pt03.photolearn.application.App;
import sg.edu.nus.se26pt03.photolearn.enums.AccessMode;
import sg.edu.nus.se26pt03.photolearn.enums.AppMode;
import sg.edu.nus.se26pt03.photolearn.enums.UserRole;
import sg.edu.nus.se26pt03.photolearn.service.QuizAnswerService;
import sg.edu.nus.se26pt03.photolearn.service.ServiceCallback;
import sg.edu.nus.se26pt03.photolearn.utility.ConstHelper;


/**
 * Created by MyatMin on 08/3/18.
 */
public class QuizItemListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    public static String SharedPreferences_Access_Mode = "ACCESSMODE";


    private int role;
    private int mode;
    private String sessionId;
    private String userId;
    private List<Item> quizItemList = null;


    private Dialog dialog;
    private ImageView popupimagebutton;
    private QuizTitle quizTitle;
    private SwipeRefreshLayout srf_quizItemList;
    private sg.edu.nus.se26pt03.photolearn.view.ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private TextView tvEmpty;
    private FloatingActionButton Add;
    private QuizAnswerService quizAnswerService;
    private QuizAnswer currentAttempt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz_item_list, container, false);
        quizTitle = (QuizTitle) getArguments().getSerializable(ConstHelper.REF_QUIZ_TITLES);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupControls();
    }

    public void updateCurrentAttempt(QuizAnswer quizAnswer) {
        if (currentAttempt != null && currentAttempt.getQuizItemId() != null && !currentAttempt.getQuizItemId().equals(quizAnswer.getQuizItemId())) {
            currentAttempt.setIsCurrentAttempt(false);
            quizAnswerService.update(currentAttempt, new ServiceCallback<Boolean>() {
                @Override
                public void onComplete(Boolean data) {
                    if (data) displayInfoMessage("Former Attempt removed!");
                    else displayInfoMessage("Error occured when removing Former Attempt!");
                }

                @Override
                public void onError(int code, String message, String details) {

                }
            });
        }
        currentAttempt = quizAnswer;
    }

    private void setupControls() {

        quizAnswerService = new QuizAnswerService();
        tvEmpty = (TextView) getView().findViewById(R.id.tv_itemempty_value);
        Add = (FloatingActionButton) getView().findViewById(R.id.fab_addquizitem);
        popupimagebutton = (ImageView) getView().findViewById(R.id.img_popupmenu);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreate(new QuizItem(quizTitle), null);
            }
        });
        popupimagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = popupSetup(popupimagebutton);
                popupMenu.show();
            }
        });
        mPager = getView().findViewById(R.id.vp_quizitem);
        if (App.getCurrentAppMode() == AppMode.TRAINER) {
            mPager.setSwipeEnabled(true);
        }
        mPagerAdapter = new QuizItemFragmentPageAdapter(getChildFragmentManager(), quizTitle, this.quizItemList);
        mPager.setAdapter(mPagerAdapter);

        srf_quizItemList = getView().findViewById(R.id.srf_quizItemList);
        srf_quizItemList.setOnRefreshListener(this);
        srf_quizItemList.post(new Runnable() {
            @Override
            public void run() {
                srf_quizItemList.setRefreshing(true);
                loadList();
            }
        });
    }

    private void setupViews() {
        tvEmpty.setVisibility(mPagerAdapter.getCount() == 0 ? View.VISIBLE : View.INVISIBLE);
        if (App.getCurrentAppMode() == AppMode.TRAINER) {
            popupimagebutton.setVisibility(mPagerAdapter.getCount() > 0 ? View.VISIBLE : View.INVISIBLE);
            Add.setVisibility(View.VISIBLE);
        }
    }

    public PopupMenu popupSetup(ImageView imageView) {
        PopupMenu popupMenu = new PopupMenu(getContext(), imageView);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.menu_learning_item, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                boolean result = false;
                switch (menuItem.getItemId()) {
                    case R.id.menu_delete:
                        new AlertDialog.Builder(getContext())
                                .setTitle("Quiz Item")
                                .setMessage("Are you sure you wanted to delete this item?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        quizTitle.deleteItem(((QuizItemFragmentPageAdapter) mPagerAdapter).getQuizItemByPosition(mPager.getCurrentItem()).getId(), new ServiceCallback<Boolean>() {
                                            @Override
                                            public void onComplete(Boolean data) {
                                                loadList();

                                            }

                                            @Override
                                            public void onError(int code, String message, String details) {

                                            }
                                        });
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        return;
                                    }
                                }).show();
                        result = true;
                        break;
                    case R.id.menu_edit:
                        onEdit(((QuizItemFragmentPageAdapter) mPagerAdapter).getQuizItemByPosition(mPager.getCurrentItem()), null);
                        result = true;
                        break;
                }
                return result;
            }
        });
        return popupMenu;

    }

    private void loadList() {
        srf_quizItemList.setRefreshing(true);
        this.quizTitle.getItems(new ServiceCallback<List<Item>>() {

            @Override
            public void onComplete(List<Item> data) {

                if (data != null) {
                    if (quizItemList != null)
                        quizItemList.clear();
                    quizItemList = data;
                    ((QuizItemFragmentPageAdapter) mPagerAdapter).setQuizItemList(data);
                    mPagerAdapter.notifyDataSetChanged();
                    setupViews();
                    srf_quizItemList.setRefreshing(false);
                    if (App.getCurrentAppMode() == AppMode.PARTICIPENT) {
                        getLastAttempt();
                    }
                }
            }

            @Override
            public void onError(int code, String message, String details) {
                Log.w("ERROR", code + "-" + message + "-" + details);

            }
        });
    }

    private void getLastAttempt() {
        List<String> quizItemIds = quizItemList.stream().map(x -> x.getId()).collect(Collectors.toList());
        quizAnswerService.getCurrentAttemptByQuizItemIDAndParticipantID
                (App.getCurrentUser().getId(), quizItemIds, new ServiceCallback<QuizAnswer>() {
                    @Override
                    public void onComplete(QuizAnswer data) {
                        currentAttempt = data;
                        if (data == null) {
                            return;
                        }
                        OptionalInt position = IntStream.range(0, quizItemIds.size()).filter(i -> (data.getQuizItemId()).equals(quizItemIds.get(i))).findFirst();
                        if (position.isPresent() && position.getAsInt() >= 0) {
                            int currentPosition = position.getAsInt() + 1;
                            mPager.setCurrentItem(currentPosition > mPagerAdapter.getCount() - 1 ? currentPosition - 1 : currentPosition);
                        }
                    }

                    @Override
                    public void onError(int code, String message, String details) {
                        displayErrorMessage(message);
                    }
                });
    }

    @Override
    public void onRefresh() {
        loadList();
    }


}
