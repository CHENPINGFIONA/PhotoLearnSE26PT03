package sg.edu.nus.se26pt03.photolearn.utility;

import android.databinding.BindingAdapter;
import android.databinding.InverseMethod;
import android.support.design.widget.TextInputLayout;
import android.view.View;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.application.App;

/**
 * Created by MyatMin on 23/3/18.
 */

public abstract class BindingHelper {
    @InverseMethod("fromCourseDate")
    public static  Date toCourseDate(String value) {
        return Date.from(LocalDate.parse(value, DateTimeFormatter.ofPattern(App.getContext().getResources().getString(R.string.format_coursedate_display))).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static String fromCourseDate(Date value) {
        if (value == null) return null;
        return value.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern(App.getContext().getResources().getString(R.string.format_coursedate_display)));
    }

    @InverseMethod("fromModuleNumber")
    public static int toModuleNumber(String value) {
        if ("".equals(value)) return 0;
        return Integer.parseInt(value);
    }

    public static String fromModuleNumber(int value) {
        if (value <= 0) return "";
        return String.format(App.getContext().getResources().getString(R.string.format_modulenumber_display), value).toString();
    }

    public static int toVisibility(boolean value) {
        if (!value) return View.INVISIBLE;
        else return View.VISIBLE;
    }
    @BindingAdapter("error")
    public static void setErrorMessage(TextInputLayout textInputLayout, String message) {
        textInputLayout.setError(message);
        textInputLayout.setErrorEnabled(!message.isEmpty());
    }
}
