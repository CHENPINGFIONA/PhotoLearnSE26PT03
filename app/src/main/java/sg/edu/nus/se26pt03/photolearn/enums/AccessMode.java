package sg.edu.nus.se26pt03.photolearn.enums;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by chen ping on 11/3/2018.
 */

public class AccessMode {
    public static final int VIEW = 0;
    public static final int EDIT = 1;

    public AccessMode(@Mode int mode) {
    }

    @IntDef({VIEW, EDIT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {}
}
