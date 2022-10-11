package edu.neu.madcourse.pettin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

import edu.neu.madcourse.pettin.Classes.User;

import edu.neu.madcourse.pettin.GroupChat.CreateGroupChatActivity;

import edu.neu.madcourse.pettin.GroupChat.ViewPageAdapter;
import edu.neu.madcourse.pettin.GroupChat.Fragments.ChatsFragment.MessageActivity;
import edu.neu.madcourse.pettin.GroupChat.UserMatches.UserAdapter;
import edu.neu.madcourse.pettin.GroupChat.UserMatches.UserListenerInterface;


public class ChatActivity extends AppCompatActivity implements UserListenerInterface {

    private static final String TAG = "ChatActivity";

    private BottomNavigationView bottomNav;
    private RecyclerView recyclerView;
    private ArrayList<User> listOfUsers;
    private UserAdapter userAdapter;
    private FirebaseFirestore db;

    private FirebaseFirestore dbInstance;

    private FirebaseUser currentUser;

    // Tab Layout - Chats | Group Chats
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ViewPageAdapter viewPageAdapter;

    // Setting up group chat
    private FloatingActionButton fabCreateGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // creates the nav bar
        createNavBar();

        // creates the tab layout
        setTabLayout();

        // sets up the float action button to allow user to create a group
        setFabCreateGroup();

        // sets up the users at the top - to be matched users
        retrieveUsers();

    }

    @Override
    protected void onPause() {
        super.onPause();
        bottomNav.setSelectedItemId(R.id.nav_chat);
    }

    private void setFabCreateGroup() {
        fabCreateGroup = findViewById(R.id.create_group);

        fabCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createGroupIntent = new Intent(ChatActivity.this, CreateGroupChatActivity.class);
                startActivity(createGroupIntent);
            }
        });
    }

     /**
     * Method sets up the tab layout.
     */
    private void setTabLayout() {
        tabLayout = findViewById(R.id.chats_tab_layout);
        viewPager2 = findViewById(R.id.view_pager);
        viewPageAdapter = new ViewPageAdapter(this);
        viewPager2.setAdapter(viewPageAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });
    }

    /**
     * Method retrieves users from the FireStore database.
     * TODO: need to update so that it gets matched users only
     */
    private void retrieveUsers() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        recyclerView = findViewById(R.id.matched_users);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        db = FirebaseFirestore.getInstance();
        listOfUsers = new ArrayList<User>();

        // load data from Firestore into array
        DocumentReference currentUserDocRef = db.collection("users").document(currentUser.getUid());
        currentUserDocRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && value.exists()) {
                    User user = value.toObject(User.class);
                    for (User matchedUser : user.getMatchedUsers()) {
                        listOfUsers.add(matchedUser);
                    }
                    userAdapter.notifyDataSetChanged();
                }
            }
        });
        userAdapter = new UserAdapter(ChatActivity.this, this.listOfUsers, this);
        new ItemTouchHelper(userCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(userAdapter);
    }

    /**
     * Method creates the navigation bar to get to the other activities.
     */
    public void createNavBar() {
        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_chat);
        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_chat:
                    return true;
                case R.id.nav_playdate:
                    startActivity(new Intent(getApplicationContext(), PlayDateActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_post:
                    startActivity(new Intent(getApplicationContext(), PostActivity.class));
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
    }

    @Override
    public void userItemClick(int position) {
        User user = listOfUsers.get(position);
//        Log.v(TAG + " user clicked", username.getText().toString());
//        Log.v(TAG + " username onClick: ", userId);
        Intent intent = new Intent(ChatActivity.this, MessageActivity.class);
        // need id - need to pass the id of the user
        intent.putExtra("userId", user.getUserId());
        Log.v(TAG, "Starting Message Activity " + user.getUserId());
        startActivity(intent);
    }

    ItemTouchHelper.SimpleCallback userCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

}