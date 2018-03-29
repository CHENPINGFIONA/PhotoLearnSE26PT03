package sg.edu.nus.se26pt03.photolearn.BAL;

import java.io.Serializable;
import java.util.Date;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.Observable;

/**
 * Created by chen ping on 7/3/2018.
 */

public class LearningItem extends Item implements Serializable,Cloneable {
    public LearningItem(Title title) {
        super(title);
    }
    @Override
    public LearningItem clone() throws CloneNotSupportedException {
        return (LearningItem) super.clone();
    }


}
