package sg.edu.nus.se26pt03.photolearn.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.R;

/**
 * Created by chen ping on 11/3/2018.
 */

public class LearningTitleListAdapter extends RecyclerView.Adapter<LearningTitleListAdapter.LearningTitleViewHolder> {
    public List<LearningTitle> learningTitleList;
    public LearningTitleViewHolderClick learningTitleViewHolderClick;

    public LearningTitleListAdapter(List<LearningTitle> learningTitleList, LearningTitleViewHolderClick learningTitleViewHolderClick) {
        this.learningTitleList = learningTitleList;
        this.learningTitleViewHolderClick = learningTitleViewHolderClick;
    }

    @Override
    public LearningTitleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learning_title, parent, false);
        final LearningTitleViewHolder learningTitleViewHolder = new LearningTitleViewHolder(itemView);
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
                        if (clickDuration < MAX_CLICK_DURATION) {
                            if (learningTitleViewHolder.itemView.isClickable())
                                learningTitleViewHolderClick.onItemClick(learningTitleViewHolder);
                        }
                        break;
                }
                return false;
            }
        });
        return learningTitleViewHolder;
    }

    @Override
    public void onBindViewHolder(LearningTitleViewHolder holder, int position) {
        LearningTitle learningTitle = learningTitleList.get(position);
        holder.tvTitle.setText(learningTitle.getTitle());
    }

    @Override
    public int getItemCount() {
        return learningTitleList.size();
    }

    public interface LearningTitleViewHolderClick {
        void onItemClick(LearningTitleViewHolder viewHolder);
    }

    public class LearningTitleViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;

        public LearningTitleViewHolder(final View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_learningTitle_title);
        }
    }
}
