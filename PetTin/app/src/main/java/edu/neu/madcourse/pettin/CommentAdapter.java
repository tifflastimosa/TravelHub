package edu.neu.madcourse.pettin;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context context;
    private List<Comment> comments;


    public CommentAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.single_comment_view,parent,false);
        return new CommentViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        if (comments.get(position).getUname()!=null) {
            holder.userIcon.setText(comments.get(position).getUname().substring(0, 1).toUpperCase());
        }

        holder.comment_name.setText(comments.get(position).getUname());
        holder.comment_content.setText(comments.get(position).getContent());
        holder.comment_date.setText(timestampToString((Long) comments.get(position).getTimestamp()));

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{

        TextView userIcon;
        TextView comment_name, comment_content, comment_date;

        public CommentViewHolder(View itemView) {
            super(itemView);
            userIcon = itemView.findViewById(R.id.textView_usericon);
            comment_name = itemView.findViewById(R.id.comment_username);
            comment_content = itemView.findViewById(R.id.comment_content);
            comment_date = itemView.findViewById(R.id.comment_date);
        }
    }



    private String timestampToString(long time) {

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("hh:mm",calendar).toString();
        return date;


    }


}
