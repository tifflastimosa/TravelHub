package edu.neu.madcourse.pettin.GroupChat.Fragments.ChatsFragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import edu.neu.madcourse.pettin.Classes.Message;
import edu.neu.madcourse.pettin.R;

// class displays the messages in the message activity
// COMPLETED
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private static final String TAG = "MessageAdapter ";

    private Context context;
    private ArrayList<Message> listOfMessages;
    private String userImage;

    public static final int MSG_TYPE_RECEIVER = 0;
    public static final int MSG_TYPE_SENDER = 1;

    // Firebase
    private FirebaseUser firebaseUser;

    public MessageAdapter(Context context, ArrayList<Message> listOfMessages, String userImage) {
        this.context = context;
        this.listOfMessages = listOfMessages;
        this.userImage = userImage;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_SENDER) {
            View view = LayoutInflater.from(context).inflate(R.layout.sender_layout, parent, false);
            return new MessageAdapter.MessageViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.receiver_layout, parent, false);
            return new MessageAdapter.MessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message currentMessage = listOfMessages.get(position);
        Log.v(TAG + "onBindViewHolder - current position", String.valueOf(position));
        holder.messageToShow.setText(currentMessage.getMessage());
        Log.v(TAG + "onBindViewHolder - currentMessage Object", currentMessage.getMessage());
        Log.v(TAG + "onBindViewHolder - after setText", holder.messageToShow.getText().toString());
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (!currentMessage.getSender().equals(currentUser.getUid())) {
            holder.username.setText("");

        }

        // TODO : display user image - use Glide library
    }

    @Override
    public int getItemCount() {
        return listOfMessages.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        private TextView messageToShow;
        private ImageView userImage;
        private TextView username;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageToShow = itemView.findViewById(R.id.message_display);
            username = itemView.findViewById(R.id.username_display);
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (listOfMessages.get(position).getSender().equals(firebaseUser.getUid())) {
            return MSG_TYPE_SENDER;
        } else {
            return MSG_TYPE_RECEIVER;

        }
    }
}
