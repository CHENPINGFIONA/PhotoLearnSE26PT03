package sg.edu.nus.se26pt03.photolearn.utility;

import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.InverseMethod;
import android.support.design.widget.TextInputLayout;
import android.view.View;

import org.w3c.dom.Text;

import java.security.cert.PKIXRevocationChecker;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import sg.edu.nus.se26pt03.photolearn.BAL.QuizOption;
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
        return toCourseDateDisplay(value);
    }

    public static String toCourseDateDisplay(Date value) {
        if (value == null) return null;
        return value.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern(App.getContext().getResources().getString(R.string.format_coursedate_display)));
    }

    public static String toCourseDateDisplayShort(Date value) {
        if (value == null) return null;
        return value.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern(App.getContext().getResources().getString(R.string.format_coursedate_display_short)));
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
    @InverseMethod("setIsAnswer")
    public static Boolean getIsAnswer(List<QuizOption> options, int position) {
        if(options!=null && options.size()>0){
            QuizOption opt= options.get(position);
            return opt.isAnswer();
        }
        return  false;
    }

    public static Boolean setIsAnswer(List<QuizOption> options, int position) {
        if(options!=null && options.size()>0){
            QuizOption opt= options.get(position);
            return opt.isAnswer();
        }
        return  false;
    }
   /* @InverseMethod("setContent")
    public static String getContent(String value,List<QuizOption> options, int position) {
        if(options!=null && options.size()>0){
            QuizOption opt= options.get(position);
            return opt.getContent();
        }
        return  "";
    }
    public static String setContent(String value,List<QuizOption> options, int position) {
        if(options!=null && options.size()>0){
            QuizOption opt= options.get(position);
            opt.setContent(value);
            opt.getContent();
        }
        return  "";
    }*/

    public static String getContentError(List<QuizOption> options, int position) {
        if(options!=null && options.size()>0){
            QuizOption opt= options.get(position);
            return opt.getContentError();
        }
        return  "";
    }

    @BindingAdapter("error")
    public static void setErrorMessage(TextInputLayout textInputLayout, String message) {
        textInputLayout.setError(message);
        textInputLayout.setErrorEnabled(!message.isEmpty());
    }


    public static boolean isValidLearningSessionID(String input) {
        if (input == null) return true;
        return Pattern.compile(App.getContext().getResources().getString(R.string.pattern_learningsessionid), Pattern.CASE_INSENSITIVE).matcher(input).matches();
    }
}
