package sg.edu.nus.se26pt03.photolearn.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.enums.AccessMode;

/**
 * Created by chen ping on 11/3/2018.
 */

public class LearningTitleListAdapter extends ArrayAdapter<LearningTitle> {
    private Context context;
    private List<LearningTitle> titles = new ArrayList<>();
    private int sessionId;
    private int mode;
    private int userId;

    public LearningTitleListAdapter(Context context, int sessionId, int mode, int userId) {
        super(context, R.layout.title_row_layout);
        this.sessionId = sessionId;
        this.mode = mode;
        this.userId = userId;
    }

    public void refreshLearningTitles() {
        LearningSession session = new LearningSession();

        titles.clear();
        titles.addAll(session.getLearningTitles(sessionId, mode, userId));
        notifyDataSetChanged();
    }
}
