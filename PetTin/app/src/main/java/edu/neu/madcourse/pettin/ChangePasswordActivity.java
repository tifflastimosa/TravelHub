package edu.neu.madcourse.pettin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText password;
    private EditText confirmPassword;
    private Button savePassword;
    private Button cancelPassword;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private String txtPassword;
    private String txtConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        savePassword = findViewById(R.id.btnSaveChangePassword);
        cancelPassword = findViewById(R.id.btnCancelChangePassword);

        auth = FirebaseAuth.getInstance();

        savePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtPassword = password.getText().toString();
                txtConfirmPassword = confirmPassword.getText().toString();
                user = auth.getCurrentUser();

                if (!txtPassword.isEmpty() && txtPassword.equals(txtConfirmPassword)) {
                    user.updatePassword(txtPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ChangePasswordActivity.this, "Change Password Successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ChangePasswordActivity.this, ProfileActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(ChangePasswordActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        cancelPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangePasswordActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}