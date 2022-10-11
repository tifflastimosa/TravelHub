package edu.neu.madcourse.pettin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.neu.madcourse.pettin.Classes.Post;
import edu.neu.madcourse.pettin.Classes.User;


public class PostActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;
    private RecyclerView recyclerView;
    private PostAdapter.RecyclerViewClickListener listener;
    private ImageView addButton;

    FirebaseFirestore db;
    FirebaseAuth auth;
    private PostAdapter postAdapter;
    private ArrayList<Post> postList;
    FirebaseUser curUser;
    String userName;


    @Override
    protected void onPause() {
        super.onPause();
        bottomNav.setSelectedItemId(R.id.nav_post);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        curUser = auth.getCurrentUser();


        if (curUser != null) {
            String userId = curUser.getUid();
            DocumentReference userRef = db.collection("users").document(userId);
            userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User user = documentSnapshot.toObject(User.class);
                    userName = user.getUsername();
                }
            });
        }
        recyclerView = findViewById(R.id.postList);
        addButton = findViewById(R.id.add);
        postList = new ArrayList<>();

        setPostItemOnClickListener();
        postAdapter = new PostAdapter(this, postList, listener);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(postAdapter);

        getPosts();

        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.updatePost);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postList.clear();
                getPosts();
                pullToRefresh.setRefreshing(false);
            }
        });


        // add post activity
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostActivity.this, AddPostActivity.class);
                startActivity(intent);
            }
        });


        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_post);
        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_post:
                    return true;
                case R.id.nav_playdate:
                    startActivity(new Intent(getApplicationContext(), PlayDateActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_chat:
                    startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_profile:
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_restaurant:
                    startActivity(new Intent(getApplicationContext(), RestaurantsNearByMeActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });



        // add post activity
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostActivity.this, AddPostActivity.class);
                intent.putExtra("username", userName);
                startActivity(intent);
            }
        });

    }

    private void getPosts() {
        CollectionReference postRef = db.collection("posts");
        postRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document: task.getResult()) {
                        postList.add(document.toObject(Post.class));
                        postAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.d("fetch post", "failed", task.getException());
                }
            }
        });
    }

    /**
     * The click listener for each item in the recycler list
     */
    private void setPostItemOnClickListener() {
        listener = new PostAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Toast.makeText(PostActivity.this, "Title is:" + postList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                Intent toPostDetailPage = new Intent(getApplicationContext(), ShowPostDetailActivity.class);
                toPostDetailPage.putExtra("postId",  postList.get(position).getPost_id());
                toPostDetailPage.putExtra("image", postList.get(position).getImage());
                startActivity(toPostDetailPage);
            }
        };
    }

}