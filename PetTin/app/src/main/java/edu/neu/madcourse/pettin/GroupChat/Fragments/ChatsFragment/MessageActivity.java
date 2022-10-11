package edu.neu.madcourse.pettin.GroupChat.Fragments.ChatsFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.neu.madcourse.pettin.ChatActivity;
import edu.neu.madcourse.pettin.Classes.CurrentChats;
import edu.neu.madcourse.pettin.Classes.Message;
import edu.neu.madcourse.pettin.Classes.User;
import edu.neu.madcourse.pettin.R;

// COMPLETED
public class MessageActivity extends AppCompatActivity {

    private static final String TAG = "MessageActivity ";

    private TextView username;
    private ImageView imageView;

    // get the current user
    private FirebaseUser currentUser;

    // get db instance
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    // get the users collection
    private CollectionReference usersCollectionReference = db.collection("users");
    private CollectionReference chatsCollectionReference = db.collection("chats");
    private CollectionReference currentChatsCollection = db.collection("current_chats");
    private DocumentReference documentReference;

    private Intent intent;
    private String userId;

    // setting the view for the current user's functionality
    private EditText userTextToSend;
    private ImageButton sendButton;
    private ImageView backButton;

    private ArrayList<Message> listOfMessages;
    private MessageAdapter messageAdapter;
    private RecyclerView messagesRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        // get rid of the action bar at the top
        getSupportActionBar().hide();

        username = findViewById(R.id.toolbar_username);
        imageView = findViewById(R.id.toolbar_image);
        messagesRecyclerView = findViewById(R.id.messages);
        userTextToSend = findViewById(R.id.text_to_send);
        sendButton = findViewById(R.id.send_button_image);
        backButton = findViewById(R.id.toolbar_back_button);

        // RecyclerView
        messagesRecyclerView = findViewById(R.id.messages);
        messagesRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        messagesRecyclerView.setLayoutManager(linearLayoutManager);

        // getting the previous id from the prev action
        intent = getIntent();
        userId = intent.getStringExtra("userId");

        // get the current user of the application
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Log.v(TAG + "currentUser ", currentUser.toString());

        // get the document of the receiving user - used to get messagedUser
        documentReference = usersCollectionReference.document(userId);

        // display username on the toolbar of the message activity
        displayingText();

        // back button click action
        backButtonAction();

        // sending message to user
        sendButton();

    }

    private void sendButton() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = userTextToSend.getText().toString();
                if (message != null) {
                    sendMessageFunctionality(currentUser.getUid(), userId, message);
                } else {
                    return;
                }
                userTextToSend.setText("");
            }
        });
    }

    // iterating through all the messages in the chats collection
    // condition to only add messages that contains the ides of the user and the sender
    private void readMessages(String id1, String id2, String image) {
        listOfMessages = new ArrayList<>();
        // gets the document

        // this approach allows for real time update on chats
        chatsCollectionReference
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        listOfMessages.clear();
                        for (QueryDocumentSnapshot snapshot : value) {
                            Message message = snapshot.toObject(Message.class);
                            if (message.getReceiver().equals(id1) && message.getSender().equals(id2)
                                    || message.getReceiver().equals(id2) && message.getSender().equals(id1)) {
                                Log.v(TAG, message.getMessage());
                                Log.v(TAG, "message retrieved from Firestore");
                                listOfMessages.add(message);
                            }
                            messageAdapter = new MessageAdapter(MessageActivity.this, listOfMessages, image);
                            messagesRecyclerView.setAdapter(messageAdapter);
                        }
                        for (Message message : listOfMessages) {
                            Log.v(TAG + "Message in listOfMessages", message.getMessage());
                        }
                    }
                });


    }


    private void backButtonAction() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MessageActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });
    }


    private void sendMessageFunctionality(String sender, String receiver, String message) {
        String id = "";
        Message messageToSend = new Message(sender, receiver, message);
        Log.v(TAG, "Generated Id " + id);


        // add message to database

        chatsCollectionReference
                .add(messageToSend)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.v(TAG, "generated id " + documentReference.getId());
                        String id = documentReference.getId();
                        // updates the chat
                        chatsCollectionReference.document(id)
                                .update("id", id)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.v(TAG, id);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.v(TAG, e.toString());
                                    }
                                });

                        CurrentChats currentChats = new CurrentChats(sender, receiver);
                        currentChatsCollection.add(currentChats)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        currentChatsCollection.document(documentReference.getId())
                                                .update("id", documentReference.getId())
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Log.v(TAG + "currentChatsCollection - receiver ", receiver);

                                                    }
                                                });
                                    }
                                });

                    }
                });

        // used to get in progress chats



    }

    /**
     * Method gets the user that was clicked on and sets the activity.
     */
    private void displayingText() {
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                User user = value.toObject(User.class);
                username.setText(user.getUsername());


                readMessages(currentUser.getUid(), user.getUserId(), "");

                // TODO : Replace default image to image of dog; use the Glide library
            }
        });
    }


}