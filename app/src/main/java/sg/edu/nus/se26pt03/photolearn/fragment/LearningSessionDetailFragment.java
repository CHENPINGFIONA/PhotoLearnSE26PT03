package sg.edu.nus.se26pt03.photolearn.fragment;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.BAL.Trainer;
import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.application.App;
import sg.edu.nus.se26pt03.photolearn.application.UserActionCallback;
import sg.edu.nus.se26pt03.photolearn.databinding.FragmentLearningSessionDetailBinding;
import sg.edu.nus.se26pt03.photolearn.enums.EventType;
import sg.edu.nus.se26pt03.photolearn.service.LearningSessionService;
import sg.edu.nus.se26pt03.photolearn.service.ServiceCallback;


/**
 * Created by MyatMin on 08/3/18.
 */
public class LearningSessionDetailFragment extends BaseFragment {
    private FragmentLearningSessionDetailBinding binding;
    private LearningSession learningSessionCopy;
    private LearningSession learningSession;
    private LearningSessionService learningSessionService;
    private boolean updateMode;
    private boolean emitter;

    public static LearningSessionDetailFragment newInstance(LearningSession learningSession) {
        LearningSessionDetailFragment learningSessionDetailFragment = new LearningSessionDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("learningSession", learningSession);
        learningSessionDetailFragment.setArguments(args);
        return learningSessionDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_learning_session_detail, container, false);
        View view = binding.getRoot();
        setupData(binding);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setupViews();
    }
    @Override
    public void onBefore(@EventType.Event int event, final UserActionCallback callback) {
        if (!emitter && !learningSessionCopy.equals(learningSession)) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Confirmation")
                    .setMessage("Unsaved data will be lost.\nAre you sure you wanted to continue?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            callback.onPass();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            callback.onReject();
                        }
                    }).show();
            emitter = false;
        }
        else {
            hideSoftInput(getActivity().getCurrentFocus().getWindowToken());
            callback.onPass();
        }

    }

    private void setupData(FragmentLearningSessionDetailBinding binding) {
        try {
            learningSession = (LearningSession) getArguments().getSerializable("learningSession");
            learningSessionCopy = learningSession.clone();
        }
        catch (CloneNotSupportedException e) {
            Log.d(this.getClass().getSimpleName(), e.getMessage());
        }
        updateMode = !learningSession.isEmpty();
        binding.setLearningSession(learningSession);
        learningSessionService = new LearningSessionService();
    }

    private void setupViews() {
        final EditText edt_coursedate = getView().findViewById(R.id.edt_coursedate);
        edt_coursedate.setHint(LocalDate.now().format(DateTimeFormatter.ofPattern(getResources().getString(R.string.format_coursedate_display))));
        edt_coursedate.setInputType(InputType.TYPE_NULL);
        edt_coursedate.setTextIsSelectable(false);
        edt_coursedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(edt_coursedate);
            }
        });
        edt_coursedate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) return;
                showDatePicker(edt_coursedate);
            }
        });

        Button btn_save = getView().findViewById(R.id.btn_save);
        if (updateMode) btn_save.setText("Update");
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!updateMode) {
                    ((Trainer) App.getCurrentUser()).createLearningSession(learningSession, new ServiceCallback<LearningSession>() {
                        @Override
                        public void onComplete(LearningSession data) {
                            learningSession.copy(data);
                            displayInfoMessage("Learning session has been created successfully!");
                            emitter = true;
                            if (getStackBack()) getActivity().onBackPressed();
                        }

                        @Override
                        public void onError(int code, String message, String details) {
                            displayErrorMessage(message);
                        }
                    });
                } else {
                    ((Trainer) App.getCurrentUser()).updateLearningSession(learningSession, new ServiceCallback<Boolean>() {
                        @Override
                        public void onComplete(Boolean data) {
                            displayInfoMessage("Learning session has been updated successfully!");
                            emitter = true;
                            if (getStackBack()) getActivity().onBackPressed();
                        }

                        @Override
                        public void onError(int code, String message, String details) {
                            displayErrorMessage(message);
                        }
                    });
                }
            }
        });
    }

    private void showDatePicker(final EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        LocalDate date = LocalDate.parse(("".equals(editText.getText().toString()) ? editText.getHint() : editText.getText().toString()), DateTimeFormatter.ofPattern(getResources().getString(R.string.format_coursedate_display)));
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                LocalDate date = LocalDate.of(year, month + 1, dayOfMonth);
                editText.setText(date.format(DateTimeFormatter.ofPattern(getResources().getString(R.string.format_coursedate_display))));
            }
        }, date.getYear(), date.getMonthValue()-1, date.getDayOfMonth());
        datePickerDialog.show();
    }


}
