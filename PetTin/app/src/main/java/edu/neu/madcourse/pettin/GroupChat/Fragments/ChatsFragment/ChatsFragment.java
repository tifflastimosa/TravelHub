package edu.neu.madcourse.pettin.GroupChat.Fragments.ChatsFragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.neu.madcourse.pettin.Classes.CurrentChats;
import edu.neu.madcourse.pettin.Classes.User;
import edu.neu.madcourse.pettin.GroupChat.UserMatches.CurrentChatsAdapter;
import edu.neu.madcourse.pettin.R;

// logic for the chats tab in the ChatActivity
public class ChatsFragment extends Fragment {

    private static final String TAG = "ChatFragment ";

    // the recycler view fo the current chats
    private RecyclerView currentChatRv;
    private CurrentChatsAdapter currentChatsAdapter;

    // the list of current chats to display to the user
    // TODO: implement so that 1st message received from matched appears to the receiver
    private ArrayList<CurrentChats> currentChats;
    private ArrayList<User> listOfUsers;

    // the current user
     private FirebaseUser currentUser;
    // get db instance
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    // get the users collection
    private CollectionReference usersCollectionReference = db.collection("users");
    private CollectionReference chatsCollectionReference = db.collection("chats");
    private CollectionReference currentChatsCollection = db.collection("current_chats");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        currentChatRv = view.findViewById(R.id.single_chats_rv);
        currentChatRv.setHasFixedSize(true);
        currentChatRv.setLayoutManager(new LinearLayoutManager(getContext()));

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        listOfUsers = new ArrayList<>();
        currentChats = new ArrayList<>();

        currentChatsCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                currentChats.clear();
                for (QueryDocumentSnapshot snapshot : value) {
                    CurrentChats currentChat = snapshot.toObject(CurrentChats.class);
                    // add only the Current Chat object if the current user and the sender user match
                    if (currentChat.getSenderId().equals(currentUser.getUid()) || currentChat.getReceiverId().equals(currentUser.getUid())) {
                        currentChats.add(currentChat);
                    }
                }
                retrieveUsers();
            }
        });
        return view;
    }

    /**
     * Method gets the list of matched users and checks with the current chats to load.
     */
    private void retrieveUsers() {
        usersCollectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                listOfUsers.clear();
                // get the current user
                currentUser = FirebaseAuth.getInstance().getCurrentUser();
                // for each user in the collection
                for (QueryDocumentSnapshot snapshot : value) {
                    // get the user object
                    User user = snapshot.toObject(User.class);
                    // for each currentChat in currentChats list
                    for (CurrentChats currentChat : currentChats) {
                        // if the user is equal to the receiver id
                        if(user.getUserId().equals(currentChat.getReceiverId()) && !listOfUsers.contains(user) && !user.getUserId().equals(currentUser.getUid())) {
                            listOfUsers.add(user);
                        }
                    }
                    for (CurrentChats currentChat : currentChats) {
                        if (user.getUserId().equals(currentChat.getSenderId()) && !listOfUsers.contains(user) && !user.getUserId().equals(currentUser.getUid())) {
                            listOfUsers.add(user);
                        }
                    }
                    currentChatsAdapter = new CurrentChatsAdapter(getContext(), listOfUsers, user.getUserId());
                    currentChatRv.setAdapter(currentChatsAdapter);
                }

                for (User user : listOfUsers) {
                    Log.v(TAG, user.getUsername() + " " + user.getUserId());
                }
                Log.v(TAG, "size " + listOfUsers.size());
            }
        });
    }

}
