package edu.neu.madcourse.pettin.GroupChat.UserMatches;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.neu.madcourse.pettin.Classes.User;
import edu.neu.madcourse.pettin.R;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private static final String TAG = "UserAdapter";

    private Context context;
    private ArrayList<User> listOfUsers;
    private User user;

    final UserListenerInterface userListenerInterface;

    public UserAdapter(Context context, ArrayList<User> listOfUsers, UserListenerInterface userListenerInterface) {
        this.context = context;
        this.listOfUsers = listOfUsers;
        this.userListenerInterface = userListenerInterface;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(context).inflate(R.layout.user_card_chat, null), userListenerInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        user = listOfUsers.get(position);
        holder.username.setText(user.getUsername());
        holder.userId = user.getUserId();




    }


    @Override
    public int getItemCount() {
        return listOfUsers.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        private TextView username;
        private String userId;

        public UserViewHolder(@NonNull View itemView, UserListenerInterface userListenerInterface) {
            super(itemView);
            username = itemView.findViewById(R.id.username);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.v(TAG + " user clicked", username.getText().toString());
                    Log.v(TAG + " username onClick: ", userId);
                    if (UserAdapter.this.userListenerInterface != null) {
                        int position = getAbsoluteAdapterPosition();
                        Log.v(TAG + "position " , String.valueOf(position));


                        if (position != RecyclerView.NO_POSITION) {
                            UserAdapter.this.userListenerInterface.userItemClick(position);
                        }
                    }
//                    Log.v(TAG + " user clicked", username.getText().toString());
//                    Log.v(TAG + " username onClick: ", userId);
//                    Intent intent = new Intent(context, MessageActivity.class);
//                    // need id - need to pass the id of the user
//                    intent.putExtra("userId", userId);
//                    Log.v(TAG, "Starting Message Activity " + userId);
//                    context.startActivity(intent);
                }
            });


        }
    }
}