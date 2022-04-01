package com.example.smartwastecollectionsystem.ui.localuser;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartwastecollectionsystem.LoginAsActivity;
import com.example.smartwastecollectionsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;


import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity {


    private TextInputLayout email_var, password_var;
    private CircularProgressButton loginbtn;
    private TextView signup_btn, forgotpassbtn;
    private ImageView loginAs_;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        changeStatusBarColor();

        email_var = findViewById(R.id.textEmail);
        password_var = findViewById(R.id.textPassword);
        loginbtn = findViewById(R.id.cirLoginButton);
        signup_btn = findViewById(R.id.sign_up);
        loginAs_ = findViewById(R.id.back_loginAs);
        forgotpassbtn = findViewById(R.id.for_pass);
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            //there is some one user logged in
            startActivity(new Intent(LoginActivity.this, ClickPictureActivity.class));
            finish();
        }

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        forgotpassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPassActivity.class));
            }
        });

        loginAs_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, LoginAsActivity.class));
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_ = email_var.getEditText().getText().toString();
                String password_ = password_var.getEditText().getText().toString();

                if (TextUtils.isEmpty(email_) || TextUtils.isEmpty(password_)) {
                    Snackbar snackbar = Snackbar.make(view, "All fields are required",
                            Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.black));
                    snackbar.show();
                } else {
                    if (!email_.matches("^[\\w.+\\-]+@" + "[^-][A-Za-z]+\\.com$")) {
                        Snackbar snackbar = Snackbar.make(view, "Enter registered email",
                                Snackbar.LENGTH_LONG);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.black));
                        snackbar.show();
                    } else {
                        login(email_, password_);
                    }
                }
            }
        });
    }

    private void login(String email_, String password_) {
        auth.signInWithEmailAndPassword(email_, password_).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this,"Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, ClickPictureActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(LoginActivity.this,  (task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }

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




