package sg.edu.nus.se26pt03.photolearn.fragment;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;

import java.time.LocalDate;
import java.util.Date;

import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.application.UserActionCallback;


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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final EditText edt_moduledate = getView().findViewById(R.id.edt_moduledate);
        edt_moduledate.setHint(LocalDate.now().getDayOfMonth() + " / "+ LocalDate.now().getMonthValue() + " / " + LocalDate.now().getYear() + " (" + LocalDate.now().getDayOfWeek().toString() + ")");
        edt_moduledate.setInputType(InputType.TYPE_NULL);
        edt_moduledate.setTextIsSelectable(false);
        edt_moduledate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(edt_moduledate);
            }
        });
        edt_moduledate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) return;
                showDatePicker(edt_moduledate);
            }
        });
    }

    private void showDatePicker(final EditText editText) {
        InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        if (editText.getTag() == null) editText.setTag(LocalDate.now());
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                editText.setTag(LocalDate.of(year, month + 1, dayOfMonth));
                editText.setText(dayOfMonth + " / " + (month + 1) + " / " + year + " (" + ((LocalDate) editText.getTag()).getDayOfWeek().toString() + ")");
            }
        }, ((LocalDate) editText.getTag()).getYear(), ((LocalDate) editText.getTag()).getMonthValue() - 1,((LocalDate) editText.getTag()).getDayOfMonth());
        datePickerDialog.show();
    }

    @Override
    public void onBefore(Event event, final UserActionCallback callback) {
        new AlertDialog.Builder(getContext())
                .setTitle("Title")
                .setMessage("Unsaved data will be lost.\n Are you sure you wanted to continue?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
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
