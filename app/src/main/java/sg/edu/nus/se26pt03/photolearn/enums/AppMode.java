package sg.edu.nus.se26pt03.photolearn.enums;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by chen ping on 3/10/2018.
 */

public class AppMode {
    public static final int TRAINER = 0;
    public static final int PARTICIPENT = 1;

    public AppMode(@Mode int mode) {
    }

    @IntDef({TRAINER, PARTICIPENT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {}
}
