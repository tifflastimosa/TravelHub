package edu.neu.madcourse.pettin.GroupChat.UserMatches;

import android.content.Context;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.neu.madcourse.pettin.Classes.User;
import edu.neu.madcourse.pettin.R;

public class MatchedUsersAdapter extends RecyclerView.Adapter<MatchedUsersAdapter.MatchedUserViewHolder> {

    private static final String TAG = "MatchedUserAdapter ";

    private Context context;
    private ArrayList<User> listOfUsers;
    private User user;

    final GroupListenerInterface groupListenerInterface;

    public MatchedUsersAdapter(Context context, ArrayList<User> listOfUsers, GroupListenerInterface groupListenerInterface) {
        this.context = context;
        this.listOfUsers = listOfUsers;
        this.groupListenerInterface = groupListenerInterface;
    }

    @NonNull
    @Override
    public MatchedUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MatchedUserViewHolder(LayoutInflater
                .from(context)
                .inflate(R.layout.activity_create_group_user_card, null)
                , groupListenerInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchedUserViewHolder holder, int position) {
        user = listOfUsers.get(position);
        holder.username.setText(user.getUsername());
        holder.userId = user.getUserId();
    }

    @Override
    public int getItemCount() {
        return listOfUsers.size();
    }

    public class MatchedUserViewHolder extends RecyclerView.ViewHolder {

        private TextView username;
        private String userId;
        private CardView cardView;

        public MatchedUserViewHolder(@NonNull View itemView, GroupListenerInterface groupListenerInterface) {
            super(itemView);

            username = itemView.findViewById(R.id.username_current);
            cardView = itemView.findViewById(R.id.add_user_card);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.v(TAG + " user clicked", username.getText().toString());
                    Log.v(TAG + " username onClick: ", userId);
                    if (groupListenerInterface != null) {
                        int position = getAbsoluteAdapterPosition();
                        Log.v(TAG + "position " , String.valueOf(position));



                        if (position != RecyclerView.NO_POSITION) {
                            groupListenerInterface.onItemClick(position);
                        }


                    }

                }
            });
        }

    }





}