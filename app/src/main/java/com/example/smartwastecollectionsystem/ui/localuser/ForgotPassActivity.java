package com.example.smartwastecollectionsystem.ui.localuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.smartwastecollectionsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class ForgotPassActivity extends AppCompatActivity {

    private CircularProgressButton resetbtn;
    private ImageView backlogin_;
    private TextInputLayout forgotEmail;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        changeStatusBarColor();

        resetbtn = findViewById(R.id.cirResetButton);
        backlogin_ = findViewById(R.id.back_login);
        forgotEmail = findViewById(R.id.textE);
        auth = FirebaseAuth.getInstance();

        backlogin_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotPassActivity.this, LoginActivity.class));
            }
        });

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.fetchSignInMethodsForEmail(forgotEmail.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if(task.getResult().getSignInMethods().isEmpty()){
                            Toast.makeText(ForgotPassActivity.this, "Enter registered email or create a new account", Toast.LENGTH_SHORT).show();

                        } else {
                            auth.sendPasswordResetEmail(forgotEmail.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(ForgotPassActivity.this, "A reset password email has been sent to your account", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ForgotPassActivity.this, (task.getException()).getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        }
                    }
                });

            }
        });
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.login_bk_color));
        }
    }
}