package edu.neu.madcourse.pettin.GroupChat.Fragments.GroupChatsFragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import edu.neu.madcourse.pettin.Classes.GroupMessage;
import edu.neu.madcourse.pettin.R;

// COMPLETED
public class GroupMessageAdapter extends RecyclerView.Adapter<GroupMessageAdapter.GroupMessageViewHolder> {

    private static final String TAG = "GroupMessageAdapter ";

    private Context context;
    private ArrayList<GroupMessage> listOfMessages;
    private String userImage;

    public static final int MSG_TYPE_RECEIVER = 0;
    public static final int MSG_TYPE_SENDER = 1;

    // Firebase
    private FirebaseUser currentUser;

    public GroupMessageAdapter(Context context, ArrayList<GroupMessage> listOfMessages, String userImage) {
        this.context = context;
        this.listOfMessages = listOfMessages;
        this.userImage = userImage;
    }

    @NonNull
    @Override
    public GroupMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_SENDER) {
            View view = LayoutInflater.from(context).inflate(R.layout.sender_layout, parent, false);
            return new GroupMessageViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.receiver_layout, parent, false);
            return new GroupMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull GroupMessageViewHolder holder, int position) {
        GroupMessage currentMessage = listOfMessages.get(position);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (!currentMessage.getSender().equals(currentUser.getUid())) {
            holder.username.setText(currentMessage.getUsername());
            Glide.with(context)
                    .load(currentMessage.getImage())
                    .transform(new FitCenter())
                    .into(holder.image);
            if (currentMessage.getImage() != null) {
                holder.image.setBackgroundResource(0);
            }


        }
        Log.v(TAG + "onBindViewHolder - current position", String.valueOf(position));
        holder.messageToShow.setText(currentMessage.getMessage());

        Log.v(TAG + "onBindViewHolder - currentMessage Object", currentMessage.getMessage());
        Log.v(TAG + "onBindViewHolder - after setText", holder.messageToShow.getText().toString());

    }

    @Override
    public int getItemCount() {
        return listOfMessages.size();
    }

    public class GroupMessageViewHolder extends RecyclerView.ViewHolder {

        private TextView messageToShow;
        private TextView username;
        private ImageView image;

        public GroupMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageToShow = itemView.findViewById(R.id.message_display);
            username = itemView.findViewById(R.id.username_display);
            image = itemView.findViewById(R.id.receiver_profileimage);
        }
    }

    @Override
    public int getItemViewType(int position) {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (listOfMessages.get(position).getSender().equals(currentUser.getUid())) {
            return MSG_TYPE_SENDER;
        } else {
            return MSG_TYPE_RECEIVER;
        }
    }
}
