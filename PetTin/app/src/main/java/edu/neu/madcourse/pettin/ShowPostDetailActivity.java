package edu.neu.madcourse.pettin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.neu.madcourse.pettin.Classes.Post;
import edu.neu.madcourse.pettin.Classes.User;

public class ShowPostDetailActivity extends AppCompatActivity {
    Post curPost;
    private String image;

    private String post_id;

    // firebase
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    FirebaseDatabase firebaseDatabase;

    private ImageView postDetailImg;
    private TextView postDetailUsername;
    private TextView postDetailTitle;
    private TextView postDetailContent;
//    private TextView postDetailLikes;
    private TextView postDetailLocation;
    private TextView postDetailTime;
    // comment section
    Button addCommentButton;
    RecyclerView recyclerView_comment;
    CommentAdapter commentAdapter;
    ArrayList<Comment> comments;
    EditText editText_comment;

    String comment_userName;
    String author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post_detail);
        post_id = getIntent().getStringExtra("postId");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        Log.d("userUID", firebaseUser.getUid());

        db = FirebaseFirestore.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        DocumentReference userRef = db.collection("users").document(firebaseUser.getUid());
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                comment_userName = user.getUsername();
            }
        });
//        Log.d("username", comment_userName);

        postDetailImg = findViewById(R.id.postDetailImg);
        postDetailUsername = findViewById(R.id.postDetailUsername);
        postDetailTitle = findViewById(R.id.postDetailTitle);
        postDetailContent = findViewById(R.id.postDetailContent);
//        postDetailLikes = findViewById(R.id.postDetailLikes);
        postDetailLocation = findViewById(R.id.postDetailLocation);
        postDetailTime = findViewById(R.id.postDetailTime);



        DocumentReference postRef = db.collection("posts").document(post_id);
        postRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                curPost = documentSnapshot.toObject(Post.class);

                postDetailUsername.setText(curPost.getUsername());
                postDetailTitle.setText(curPost.getTitle());
                postDetailContent.setText(curPost.getContent());
//                postDetailLikes.setText(curPost.getLikes());
                postDetailLocation.setText(curPost.getLocation());
                postDetailTime.setText(curPost.getTime());
                image = curPost.getImage();
                Glide.with(ShowPostDetailActivity.this).load(image).into(postDetailImg);
            }
        });

        // comment section
        addCommentButton = findViewById(R.id.button_addComment);
        editText_comment = findViewById(R.id.editText_comment);
        recyclerView_comment = findViewById(R.id.recyclerview_comment);


        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference commentReference = firebaseDatabase.getReference("comments").child(post_id).push();
                String comment_content = editText_comment.getText().toString();
                String comment_userId = firebaseUser.getUid();
                Log.d("username", comment_userName);
                Comment comment = new Comment(comment_content, comment_userId, comment_userName);
                commentReference.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(),"comment added", Toast.LENGTH_SHORT).show();
                        editText_comment.setText("");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"fail to add comment", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        initRecyclerViewComment();
    }


    private void initRecyclerViewComment() {
        recyclerView_comment.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference commentRef = firebaseDatabase.getReference("comments").child(post_id);
        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                comments = new ArrayList<>();
                for (DataSnapshot snap:snapshot.getChildren()) {
                    Comment comment = snap.getValue(Comment.class);
                    comments.add(comment) ;
                }
                commentAdapter = new CommentAdapter(getApplicationContext(),comments);
                recyclerView_comment.setAdapter(commentAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}