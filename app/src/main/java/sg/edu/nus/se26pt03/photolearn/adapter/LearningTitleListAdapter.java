package sg.edu.nus.se26pt03.photolearn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.application.App;

/**
 * Created by chen ping on 11/3/2018.
 */

public class LearningTitleListAdapter extends RecyclerView.Adapter<LearningTitleListAdapter.LearningTitleViewHolder> {
    private Context context;

    private List<LearningTitle> titles;
    private String sessionId;
    private String userId;
    private int mode;

    public class LearningTitleViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public Button btnEdit;
        public Button btnDelete;

        public LearningTitleViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            btnEdit = (Button) view.findViewById(R.id.btn_edit);
            btnDelete = (Button) view.findViewById(R.id.btn_delete);
        }
    }

    public LearningTitleListAdapter(List<LearningTitle> titles, String sessionId, int mode, String userId) {
        this.titles = titles;
        this.sessionId = sessionId;
        this.mode = mode;
        this.userId = userId;
    }

    public void refreshLearningTitles(String text) {
        titles.clear();
        titles.addAll(App.session.getLearningTitles(this.sessionId, this.userId, this.mode, text));
        notifyDataSetChanged();
    }

    @Override
    public LearningTitleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.title_row_layout, parent, false);

        return new LearningTitleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LearningTitleViewHolder holder, int position) {
        final LearningTitle title = titles.get(position);
        holder.tvTitle.setText(title.title);
        //SET EDIT & DELETE BUTTON EVENT
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(context, title.title + "edit clicked", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                App.session.deleteLearningTitle(title);
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }
}
