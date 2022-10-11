package edu.neu.madcourse.pettin.GroupChat.UserMatches;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.neu.madcourse.pettin.Classes.User;
import edu.neu.madcourse.pettin.GroupChat.Fragments.ChatsFragment.MessageActivity;
import edu.neu.madcourse.pettin.R;

// COMPLETED
public class CurrentChatsAdapter extends RecyclerView.Adapter<CurrentChatsAdapter.CurrentChatsViewHolder> {

    private static final String TAG = "CurrentChatsAdapter";

    private Context context;
    private ArrayList<User> listOfUsers;
    private User user;

    public CurrentChatsAdapter(Context context, ArrayList<User> listOfUsers, String userId) {
        this.context = context;
        this.listOfUsers = listOfUsers;
    }

    @NonNull
    @Override
    public CurrentChatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_chat_message_card, parent, false);
        return new CurrentChatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentChatsViewHolder holder, int position) {
        user = listOfUsers.get(position);
        holder.username.setText(user.getUsername());
        holder.userId = user.getUserId();
    }

    @Override
    public int getItemCount() {
        return listOfUsers.size();
    }

    public class CurrentChatsViewHolder extends RecyclerView.ViewHolder {

        private TextView username;
        private String userId;

        public CurrentChatsViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username_current);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MessageActivity.class);
                    // need id - need to pass the id of the user
                    intent.putExtra("userId", userId);
                    Log.v(TAG, "Starting Message Activity " + userId);
                    context.startActivity(intent);
                }
            });
        }

    }

}
