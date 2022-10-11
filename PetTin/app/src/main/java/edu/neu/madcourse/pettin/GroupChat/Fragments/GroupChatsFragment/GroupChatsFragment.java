package edu.neu.madcourse.pettin.GroupChat.Fragments.GroupChatsFragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;

import edu.neu.madcourse.pettin.Classes.GroupChat;
import edu.neu.madcourse.pettin.Classes.User;
import edu.neu.madcourse.pettin.R;

// COMPLETED
public class GroupChatsFragment extends Fragment {

    private static final String TAG = "GroupFragment ";

    private ArrayList<GroupChat> listOfGroups;

    private RecyclerView usersGroupsRv;
    private GroupAdapter usersGroupsAdapter;
    private FirebaseUser currentUser;

    // get db instance
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference userCollectionReference = db.collection("users");
    private CollectionReference groupCollectionReference = db.collection("groups");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_chats, container, false);

        // TODO: add list of groups user is a member in to group fragments - rv, adapter, interface
        // TODO: create group chat layout, message functionality and receiving messages, and displaying

        // instantiate the current user
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // set up the recycler view
        usersGroupsRv = view.findViewById(R.id.group_chat_rv);
        usersGroupsRv.setHasFixedSize(true);
        usersGroupsRv.setLayoutManager(new LinearLayoutManager(getContext()));

        // array will store the list of groups that the user is a member of
        listOfGroups = new ArrayList<>();
        retrieveDocuments();
        return view;
    }

    private void retrieveDocuments() {

        groupCollectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                listOfGroups.clear();
                // get the current user document
                ArrayList<User> currentUserRetrieved = new ArrayList<>();
                // for each document in the collection
                currentUser = FirebaseAuth.getInstance().getCurrentUser();

                for (QueryDocumentSnapshot snapshot : value) {
                    // get each group chat from the groups collection
                    GroupChat groupChat = snapshot.toObject(GroupChat.class);
                    // iterate through each user in the list of group members
                    for (User user : groupChat.getListOfGroupMembers()) {
                        if (user.getUserId().equals(currentUser.getUid())  && !listOfGroups.contains(groupChat)) {
                            listOfGroups.add(groupChat);
                        }
//                        if (groupChat.getListOfGroupMembers().contains(user) && !listOfGroups.contains(groupChat)) {
//
//                        }
                        usersGroupsAdapter = new GroupAdapter(getContext(), listOfGroups, groupChat.getId());
                        usersGroupsRv.setAdapter(usersGroupsAdapter);
                    }
                    Log.v(TAG, " groups " + groupChat.getGroup());

                }
            }
        });
    }

}