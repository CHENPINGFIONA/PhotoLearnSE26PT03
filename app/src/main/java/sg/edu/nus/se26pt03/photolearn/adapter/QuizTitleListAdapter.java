package sg.edu.nus.se26pt03.photolearn.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.QuizTitle;
import sg.edu.nus.se26pt03.photolearn.R;


/**
 * Created by part time team 3 on 11/3/2018.
 */

public class QuizTitleListAdapter extends RecyclerView.Adapter<QuizTitleListAdapter.QuizTitleViewHolder> {
    public List<QuizTitle> quizTitleList;
    public QuizTitleViewHolderClick quizTitleViewHolderClick;

    public QuizTitleListAdapter(List<QuizTitle> quizTitleList, QuizTitleViewHolderClick quizTitleViewHolderClick) {
        this.quizTitleList = quizTitleList;
        this.quizTitleViewHolderClick = quizTitleViewHolderClick;
    }

    @Override
    public QuizTitleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title, parent, false);
        final QuizTitleViewHolder quizTitleViewHolder = new QuizTitleViewHolder(itemView);
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
                            if (quizTitleViewHolder.itemView.isClickable())
                                quizTitleViewHolderClick.onItemClick(quizTitleViewHolder);
                        }
                        break;
                }
                return false;
            }
        });
        return quizTitleViewHolder;
    }

    @Override
    public void onBindViewHolder(QuizTitleViewHolder holder, int position) {
        QuizTitle quizTitle = quizTitleList.get(position);
        holder.tvTitle.setText(quizTitle.getTitle());
        holder.tvCreatedBy.setText(quizTitle.getCreatedBy());
        //holder.tvCreatedOn.setText(new SimpleDateFormat("dd-MMM-yyyy").format(quizTitle.getTimestamp()).toString());
    }

    @Override
    public int getItemCount() {
        return quizTitleList.size();
    }

    public interface QuizTitleViewHolderClick {
        void onItemClick(QuizTitleViewHolder viewHolder);
    }

    public class QuizTitleViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvCreatedBy;
        private TextView tvCreatedOn;

        public QuizTitleViewHolder(final View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvCreatedBy = itemView.findViewById(R.id.tv_createdBy);
            tvCreatedOn = itemView.findViewById(R.id.tv_createOn);
        }
    }
}
