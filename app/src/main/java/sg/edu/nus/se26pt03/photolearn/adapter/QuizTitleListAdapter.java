package sg.edu.nus.se26pt03.photolearn.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.QuizTitle;
import sg.edu.nus.se26pt03.photolearn.R;


/**
 * Created by chen ping on 11/3/2018.
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learning_title, parent, false);
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
        holder.tvTitle.setText(quizTitle.title);
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

        public QuizTitleViewHolder(final View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_learningTitle_title);
        }
    }
}
