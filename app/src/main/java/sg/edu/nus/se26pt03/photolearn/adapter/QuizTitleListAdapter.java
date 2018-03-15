package sg.edu.nus.se26pt03.photolearn.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import sg.edu.nus.se26pt03.photolearn.BAL.LearningTitle;
import sg.edu.nus.se26pt03.photolearn.BAL.QuizTitle;
import sg.edu.nus.se26pt03.photolearn.R;
import sg.edu.nus.se26pt03.photolearn.application.App;

/**
 * Created by chen ping on 11/3/2018.
 */

public class QuizTitleListAdapter extends RecyclerView.Adapter<QuizTitleListAdapter.QuizTitleViewHolder> {
    private Context context;
    private Dialog dialog;

    private List<QuizTitle> titles;
    private String sessionId;

    public class QuizTitleViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public Button btnEdit;
        public Button btnDelete;

        public QuizTitleViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            btnEdit = (Button) view.findViewById(R.id.btn_edit);
            btnDelete = (Button) view.findViewById(R.id.btn_delete);
        }
    }

    public QuizTitleListAdapter(List<QuizTitle> titles, String sessionId) {
        this.titles = titles;
        this.sessionId = sessionId;
    }

    public void refreshQuizTitles() {
        titles.clear();
        titles.addAll(App.session.getQuizTitles(this.sessionId));
        notifyDataSetChanged();
    }

    @Override
    public QuizTitleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.title_row_layout, parent, false);

        return new QuizTitleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QuizTitleViewHolder holder, int position) {
        final QuizTitle title = titles.get(position);
        holder.tvTitle.setText(title.title);
        //SET EDIT & DELETE BUTTON EVENT
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(context, title.title + "edit clicked", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                App.session.deleteQuizTitle(title);
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    private void showDialogue(QuizTitle title) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_title);
        dialog.show();

        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        tvTitle.setText(R.string.quiz_title);

        final EditText etContent = (EditText) dialog.findViewById(R.id.et_content);
        etContent.setHint(R.string.enter_title);

        Button btnSave = (Button) dialog.findViewById(R.id.btn_save);
        Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set title content
                //update title
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
