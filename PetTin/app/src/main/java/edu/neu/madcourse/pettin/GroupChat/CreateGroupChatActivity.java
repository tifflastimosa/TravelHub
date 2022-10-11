package edu.neu.madcourse.pettin.GroupChat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.stream.Collectors;

import edu.neu.madcourse.pettin.ChatActivity;
import edu.neu.madcourse.pettin.Classes.GroupChat;
import edu.neu.madcourse.pettin.Classes.User;
import edu.neu.madcourse.pettin.GroupChat.UserMatches.GroupListenerInterface;
import edu.neu.madcourse.pettin.GroupChat.UserMatches.MatchedUsersAdapter;
import edu.neu.madcourse.pettin.GroupChat.UserMatches.UserAdapter;
import edu.neu.madcourse.pettin.GroupChat.UserMatches.UserListenerInterface;
import edu.neu.madcourse.pettin.R;

// COMPLETED
public class CreateGroupChatActivity extends AppCompatActivity implements GroupListenerInterface, UserListenerInterface {

    private static final String TAG = "CreateGroupChatActivity ";
    private EditText groupName;
    private ImageView backButton;

    // get the current year
    private FirebaseUser currentUser;

    // recycler view for all matched users
    private RecyclerView matchedUsersRV;
    private MatchedUsersAdapter matchedUsersAdapter;

    private RecyclerView addedMembersRv;
    private UserAdapter addedMembersAdapter;

    private ArrayList<User> listOfUsers = new ArrayList<>();
    private ArrayList<User> groupMembers = new ArrayList<>();

    // get db instance
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference groupCollectionReference = db.collection("groups");

    private Intent intent;

    private Button createGroupBttn;
    private EditText nameOfGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group_chat);

        backButton();

        // recycler view to display matched users to add to group
        matchedUsersRV = findViewById(R.id.create_group_matched_users);
        addedMembersRv = findViewById(R.id.added_members);


        // need to build the group chat with title, id, participants
        retrieveMatchedUsers();
        displayAddedMembers();

        addGroupToFirestore();

    }

    private void backButton() {
        backButton = findViewById(R.id.create_group_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG + "groupMembers size ", String.valueOf(groupMembers.size()));
                startActivity(new Intent(CreateGroupChatActivity.this, ChatActivity.class));
            }
        });
    }

    private void displayAddedMembers() {
        addedMembersRv.setHasFixedSize(true);
        addedMembersRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        addedMembersAdapter = new UserAdapter(CreateGroupChatActivity.this, this.groupMembers, this);
        new ItemTouchHelper(itemCallback).attachToRecyclerView(addedMembersRv);
        addedMembersRv.setAdapter(addedMembersAdapter);
    }
    private void retrieveMatchedUsers() {

        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

//        matchedUsersRV = findViewById(R.id.create_group_matched_users);
        matchedUsersRV.setHasFixedSize(true);
        matchedUsersRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        matchedUsersAdapter = new MatchedUsersAdapter(CreateGroupChatActivity.this, this.listOfUsers, this);
        new ItemTouchHelper(memberCallback).attachToRecyclerView(matchedUsersRV);

        DocumentReference currentUserDocRef = db.collection("users").document(currentUser.getUid());
        currentUserDocRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && value.exists()) {
                    User user = value.toObject(User.class);
                    for (User matchedUser : user.getMatchedUsers()) {
                        listOfUsers.add(matchedUser);
                    }
                    matchedUsersAdapter.notifyDataSetChanged();
                }
            }
        });
        matchedUsersRV.setAdapter(matchedUsersAdapter);
    }


    @Override
    public void onItemClick(int position) {
        User user = listOfUsers.get(position);
        Log.v(TAG + "onItemClick", user.getUsername());
        this.listOfUsers.remove(user);
        this.groupMembers.add(user);
        matchedUsersAdapter.notifyDataSetChanged();
        addedMembersAdapter.notifyDataSetChanged();
        Log.v(TAG + "onItemClick - listOfUsers", String.valueOf(listOfUsers.size()));
        Log.v(TAG + "onItemClick - groupMembers", String.valueOf(groupMembers.size()));

    }

    ItemTouchHelper.SimpleCallback memberCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAbsoluteAdapterPosition();
            String username = listOfUsers.get(position).getUsername();
            matchedUsersAdapter.notifyDataSetChanged();
            Log.v(TAG + "onSwiped ", username);
            listOfUsers.remove(position);
            matchedUsersAdapter.notifyDataSetChanged();
        }
    };


    @Override
    public void userItemClick(int position) {
        User user = groupMembers.get(position);
        Log.v(TAG + "onItemClick", user.getUsername());
        if (!user.getUserId().equals(currentUser.getUid())) {
            this.groupMembers.remove(user);
            this.listOfUsers.add(user);
        }
        addedMembersAdapter.notifyDataSetChanged();
        matchedUsersAdapter.notifyDataSetChanged();
        Log.v(TAG + "onItemClick - listOfUsers", String.valueOf(listOfUsers.size()));
        Log.v(TAG + "onItemClick - groupMembers", String.valueOf(groupMembers.size()));
    }

    ItemTouchHelper.SimpleCallback itemCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

    private void addGroupToFirestore() {
        createGroupBttn = findViewById(R.id.create_group_button);
        nameOfGroup = findViewById(R.id.editTextTextPersonName2);
        DocumentReference currentUserDoc = db.collection("users").document(currentUser.getUid());

        // gets the current user
        currentUserDoc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && value.exists()) {
                    // Getting data (custom Object)
                    User currentUser = value.toObject(User.class);
                    // current user will always be at the end of the list
                    groupMembers.add(currentUser);
                    createGroupBttn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (groupMembers.isEmpty() || groupMembers.size() == 1) {
                                // TODO : Logic to notify current user that list is empty
                            } else {
                                String newGroupName = nameOfGroup.getText().toString();
                                GroupChat groupToAdd;
                                if (newGroupName.isEmpty()) {
                                    groupToAdd = new GroupChat(newGroupName, groupMembers);
                                } else {
                                    groupToAdd = new GroupChat(newGroupName, groupMembers);
                                }

                                groupCollectionReference
                                        .add(groupToAdd)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                String id = documentReference.getId();
                                                groupCollectionReference.document(id)
                                                        .update("id", id)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                startActivity(new Intent(CreateGroupChatActivity.this, ChatActivity.class));
                                                            }
                                                        });
                                            }
                                        });
                            }

                        }
                    });


                }
            }
        });



    }

}