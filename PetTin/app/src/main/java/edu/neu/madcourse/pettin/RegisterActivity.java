package edu.neu.madcourse.pettin;

import static android.view.View.OnClickListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.neu.madcourse.pettin.Classes.User;

public class RegisterActivity extends AppCompatActivity {
    // data input
    private EditText registerUsername;
    private EditText registerEmail;
    private EditText registerPassword;
    private Button registerButton;
    private TextView textLogin;
    private ProgressBar progressBar;

    // data output
    String userName;
    String email;
    String pw;

    // FireStore
    private FirebaseAuth auth;
    FirebaseFirestore db;
    String userId;



    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerUsername = findViewById(R.id.registerUsername);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        textLogin = findViewById(R.id.textLogin);
        registerButton = findViewById(R.id.registerButton);

        progressBar = findViewById(R.id.registerProgressBar);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        registerButton.setOnClickListener(view -> {

            userName = registerUsername.getText().toString();
            email = registerEmail.getText().toString();
            pw = registerPassword.getText().toString();

            if (userName.isEmpty()) {
                registerUsername.setError("Username is required");
            }

            else if (email.isEmpty()) {
                registerEmail.setError("Email is required");
            }
            else if (pw.isEmpty()) {
                registerPassword.setError("Password is required");
            }

            else {
                SignUpUser();
//                reference.child(user).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (snapshot.exists()) {
//                            Toast.makeText(RegisterActivity.this, "Duplicate username", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            progressBar.setVisibility(view.VISIBLE);
//
//                            //Register the user in the firebase
//                            auth.createUserWithEmailAndPassword(email, pw).addOnCompleteListener(task -> {
//                                if (task.isSuccessful()) {
//                                    User newUser = new User(user, email);
//                                    reference.child(user).setValue(newUser);
//                                    Toast.makeText(RegisterActivity.this, "Register Successfully,  " + user, Toast.LENGTH_SHORT).show();
//                                    Intent toMainPage = new Intent(RegisterActivity.this, PlayDateActivity.class);
//                                    toMainPage.putExtra("username", user);
//                                    startActivity(toMainPage);
//                                    progressBar.setVisibility(INVISIBLE);
//                                }
//                                else {
//                                    progressBar.setVisibility(INVISIBLE);
//                                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//
//                                }
//
//                            });
//                        }
//
//                    }
//
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });

            }


        });

        textLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

            }
        });
    }

    private void SignUpUser() {
        progressBar.setVisibility(View.VISIBLE);
        registerButton.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(email, pw).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Sign Up Successful!", Toast.LENGTH_SHORT).show();
                    Log.w("sign up activity", "createUserWithEmail:failure", task.getException());
                    userId = auth.getCurrentUser().getUid();
                    DocumentReference documentRef = db.collection("users").document(userId);
                    User user = new User(userName, email, userId);
//                    Map<String, Object> user = new HashMap<>();
//                    user.put("username", userName);
//                    user.put("email", email);
//                    user.put("following", new HashMap<>());
//                    user.put("posts", new HashMap<>());
//                    user.put("dogs", new ArrayList<>());
//                    user.put("mathcedUsers", new ArrayList<>());
//                    user.put("dislikeDog", new ArrayList<>());
                    documentRef.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("signupUser", "on success, userName" + userId);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("signupUser", "failed "+ userId);
                                }
                            });
                }
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

}