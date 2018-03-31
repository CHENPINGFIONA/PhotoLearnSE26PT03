package sg.edu.nus.se26pt03.photolearn.utility;

import android.text.format.DateUtils;
import android.util.Log;

import java.io.Console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by part time team 3  on 2018/3/17.
 */

public class DateConversionHelper {
    static final SimpleDateFormat yymmddFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date convertStringToDate(String date) {
        if (date == null) return null;
        Date result = null;
        try {
            result = yymmddFormat.parse(date);
        } catch (ParseException e) {
            Log.e("ErrorParsingDate", "Error date:" + date.toString(), e);
        }
        return result;
    }

    public static String convertDateToString(Date date) {
        return yymmddFormat.format(date);
    }

    public static Date stripDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(dateFormat.format(date));
        } catch (ParseException e) {
            return null;
        }
    }
}
