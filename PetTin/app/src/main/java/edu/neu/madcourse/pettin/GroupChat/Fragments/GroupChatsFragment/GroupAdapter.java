package edu.neu.madcourse.pettin.GroupChat.Fragments.GroupChatsFragment;

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
import edu.neu.madcourse.pettin.Classes.GroupChat;
import edu.neu.madcourse.pettin.R;

// COMPLETED
public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupCardViewHolder> {

    private static final String TAG = "GroupAdapter ";

    private Context context;
    private ArrayList<GroupChat> listOfGroups;
    private GroupChat groupChatCard;
    private String groupId;

    public GroupAdapter(Context context, ArrayList<GroupChat> listOfGroups, String id) {
        this.context = context;
        this.listOfGroups = listOfGroups;
        this.groupId = id;
    }

    @NonNull
    @Override
    public GroupCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GroupCardViewHolder(LayoutInflater.from(context).inflate(R.layout.group_card, null));
    }

    @Override
    public void onBindViewHolder(@NonNull GroupCardViewHolder holder, int position) {
        groupChatCard = listOfGroups.get(position);
        holder.groupName.setText(groupChatCard.getGroup());
        holder.groupId = groupChatCard.getId();
    }

    @Override
    public int getItemCount() {
        return listOfGroups.size();
    }

    public class GroupCardViewHolder extends RecyclerView.ViewHolder {

        private TextView groupName;
        private String groupId;

        public GroupCardViewHolder(@NonNull View itemView) {
            super(itemView);

            // text view from the group card
            groupName = itemView.findViewById(R.id.group_current);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Log.v(TAG + " groupId", groupId);
                    Intent intent = new Intent(context, GroupMessageActivity.class);
                    intent.putExtra("id", groupId);
                    context.startActivity(intent);

                }
            });
        }
    }



}