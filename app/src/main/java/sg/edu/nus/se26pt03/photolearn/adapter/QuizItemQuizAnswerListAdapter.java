package sg.edu.nus.se26pt03.photolearn.adapter;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import sg.edu.nus.se26pt03.photolearn.BAL.QuizAnswer;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizItem;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizOption;
import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.utility.AsyncLoadImageHelper;

/**
 * Created by part time team 3  on 29/3/18.
 */

public class QuizItemQuizAnswerListAdapter extends RecyclerView.Adapter<QuizItemQuizAnswerListAdapter.QuizItemQuizAnswerViewHolder> {
    private List<AbstractMap.SimpleEntry<QuizItem, QuizAnswer>> listQuizItemQuizAnswer;

    public QuizItemQuizAnswerListAdapter(List<AbstractMap.SimpleEntry<QuizItem, QuizAnswer>> listQuizItemQuizAnswer) {
        this.listQuizItemQuizAnswer = listQuizItemQuizAnswer;
    }

    public List<AbstractMap.SimpleEntry<QuizItem, QuizAnswer>> getListQuizItemQuizAnswer() {
        return listQuizItemQuizAnswer;
    }

    public void setListQuizItemQuizAnswer(List<AbstractMap.SimpleEntry<QuizItem, QuizAnswer>> listQuizItemQuizAnswer) {
        this.listQuizItemQuizAnswer = listQuizItemQuizAnswer;
    }

    @Override
    public QuizItemQuizAnswerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz_answer,parent,false);
        final QuizItemQuizAnswerViewHolder quizItemQuizAnswerViewHolder = new QuizItemQuizAnswerViewHolder(itemView);
        return  quizItemQuizAnswerViewHolder;
    }

    @Override
    public void onBindViewHolder(QuizItemQuizAnswerViewHolder holder, int position) {
        if(!listQuizItemQuizAnswer.get(position).getKey().getPhotoURL().equals("")) {
            AsyncLoadImageHelper loader = new AsyncLoadImageHelper(holder.img_quizitem_photo, holder.itemView.getContext(), holder.pb_quizitem_photo);
            loader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, listQuizItemQuizAnswer.get(position).getKey().getPhotoURL());
        }
        else {
            holder.img_quizitem_photo.setVisibility(View.GONE);
            holder.pb_quizitem_photo.setVisibility(View.GONE);
        }
        holder.tv_quizitem_content.setText(listQuizItemQuizAnswer.get(position).getKey().getContent());
        holder.ll_quizitem_options.removeAllViews();
        for (QuizOption quizOption : listQuizItemQuizAnswer.get(position).getKey().getQuizOptions()) {
            QuizAnswer quizAnswer = listQuizItemQuizAnswer.get(position).getValue();
            CheckedTextView ctv_quizoption = new CheckedTextView(holder.itemView.getContext());
            ctv_quizoption.setWidth(holder.itemView.getWidth());
            ctv_quizoption.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            if (quizAnswer.getSelectedOptionIds().indexOf(quizOption.getId()) >= 0) {
                ctv_quizoption.setChecked(true);
                ctv_quizoption.setCheckMarkDrawable(R.drawable.ic_check_circle_black_24dp);
                Drawable drawable = ctv_quizoption.getCheckMarkDrawable();
                drawable.mutate().setColorFilter(holder.itemView.getContext().getColor(R.color.correctQuizOptionColor), PorterDuff.Mode.SRC_IN);
                if (!quizOption.isAnswer()) {
                    drawable.mutate().setColorFilter(holder.itemView.getContext().getColor(R.color.incorrectQuizOptionColor), PorterDuff.Mode.SRC_IN);

                }
            }
            else
            {
                ctv_quizoption.setChecked(false);
            }
            ctv_quizoption.setText(quizOption.getContent());
            ctv_quizoption.setCheckMarkTintMode(PorterDuff.Mode.DST_ATOP);
            ctv_quizoption.setClickable(false);
            holder.ll_quizitem_options.addView(ctv_quizoption);
        }
    }

    @Override
    public int getItemCount() {
        return listQuizItemQuizAnswer.size();
    }

    public class QuizItemQuizAnswerViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_quizitem_photo;
        private ProgressBar pb_quizitem_photo;
        private TextView tv_quizitem_content;
        private LinearLayout ll_quizitem_options;
        public QuizItemQuizAnswerViewHolder(View itemView) {
            super(itemView);
            img_quizitem_photo = itemView.findViewById(R.id.img_quizitem_photo);
            pb_quizitem_photo = itemView.findViewById(R.id.pb_quizitem_photo);
            tv_quizitem_content = itemView.findViewById(R.id.tv_quizitem_content);
            ll_quizitem_options = itemView.findViewById(R.id.ll_quizitem_options);
        }
    }
}
