package sg.edu.nus.se26pt03.photolearn.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningSession;
import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.utility.BindingHelper;

/**
 * Created by part time team 3  on 10/3/18.
 */

public class LearningSessionListAdapter extends RecyclerView.Adapter<LearningSessionListAdapter.LearningSessionViewHolder>  {
    private List<LearningSession> learningSessions;
    private LearningSessionViewHolderClick learningSessionViewHolderClick;

    public LearningSessionListAdapter(List<LearningSession> learningSessions) {
        if (learningSessions == null) learningSessions = new ArrayList<LearningSession>();
        this.learningSessions = learningSessions;
    }

    public void setLearningSessionViewHolderClick(LearningSessionViewHolderClick learningSessionViewHolderClick) {
        this.learningSessionViewHolderClick = learningSessionViewHolderClick;
    }

    public List<LearningSession> getLearningSessions() {
        return learningSessions;
    }

    public void setLearningSessions(List<LearningSession> learningSessions) {
        this.learningSessions = learningSessions;
    }

    @Override
    public LearningSessionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learning_session,parent,false);
        final LearningSessionViewHolder learningSessionViewHolder = new LearningSessionViewHolder(itemView);
        itemView.setOnTouchListener(new View.OnTouchListener() {
            int MAX_CLICK_DURATION = 200;
            long startClickTime;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startClickTime = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_UP:
                        long clickDuration = System.currentTimeMillis() - startClickTime;
                        if(clickDuration < MAX_CLICK_DURATION) {
                            if (learningSessionViewHolder.itemView.isClickable()) learningSessionViewHolderClick.onItemClick(learningSessionViewHolder);
                        }
                    break;
                }
                return false;
            }
        });
        return  learningSessionViewHolder;
    }

    @Override
    public void onBindViewHolder(LearningSessionViewHolder holder, int position) {
        LearningSession learningSession = learningSessions.get(position);
        holder.tv_learningsession_course.setText(learningSession.getCourseName() + " (" + learningSession.getCourseCode() + ")");
        holder.tv_learningsession_module.setText(Integer.toString(learningSession.getModuleNumber()) + ". " + learningSession.getModuleName());
        holder.tv_learningsession_date.setText(BindingHelper.toCourseDateDisplayShort(learningSession.getCourseDate()));
        holder.tv_learningsession_id.setText(learningSession.getLearningSessionId());
    }


    @Override
    public int getItemCount() {
        return learningSessions.size();
    }
    public interface  LearningSessionViewHolderClick {
        void onItemClick(LearningSessionViewHolder viewHolder);
    }
    public class LearningSessionViewHolder extends RecyclerView.ViewHolder  {
        private TextView tv_learningsession_course;
        private TextView tv_learningsession_module;
        private TextView tv_learningsession_id;
        private TextView tv_learningsession_date;
        public LearningSessionViewHolder(final View itemView) {
            super(itemView);
            tv_learningsession_course =  itemView.findViewById(R.id.tv_learningsession_course);
            tv_learningsession_module =  itemView.findViewById(R.id.tv_learningsession_module);
            tv_learningsession_id =  itemView.findViewById(R.id.tv_learningsession_id);
            tv_learningsession_date =  itemView.findViewById(R.id.tv_learningsession_date);
        }
    }
}
