package com.example.smartwastecollectionsystem.ui.authority;

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


public class Login1Activity extends AppCompatActivity {

    private TextInputLayout memail_var, mpassword_var;
    private CircularProgressButton loginbtn;
    private TextView signup_btn, forgotpassbtn;
    private ImageView loginAs_;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
        changeStatusBarColor();

        memail_var = findViewById(R.id.textEmail);
        mpassword_var = findViewById(R.id.textPassword);
        loginbtn = findViewById(R.id.cirLoginButton);
        signup_btn = findViewById(R.id.sign_up);
        forgotpassbtn = findViewById(R.id.for_pass);
        loginAs_ = findViewById(R.id.back_loginAs);
        auth = FirebaseAuth.getInstance();

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login1Activity.this, Register1Activity.class));
            }
        });
        forgotpassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login1Activity.this, ForgotPass1Activity.class));
            }
        });
        loginAs_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login1Activity.this, LoginAsActivity.class));
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String memail_ = memail_var.getEditText().getText().toString();
                String mpassword_ = mpassword_var.getEditText().getText().toString();

                if (TextUtils.isEmpty(memail_) || TextUtils.isEmpty(mpassword_)) {
                    Snackbar snackbar = Snackbar.make(view, "All fields are required",
                            Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.black));
                    snackbar.show();
                } else {
                    if (!memail_.matches("^[\\w.+\\-]+@" +"[^-][A-Za-z]+(\\.gov+)*(\\.in)$")) {
                        Snackbar snackbar = Snackbar.make(view, "Enter Registered Email",
                                Snackbar.LENGTH_LONG);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.black));
                        snackbar.show();

                    } else {
                        login(memail_, mpassword_);
                    }
                }

            }
        });
    }

    private void login(String memail_, String mpassword_) {
        auth.signInWithEmailAndPassword(memail_, mpassword_).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Login1Activity.this,"Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login1Activity.this, DetailsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(Login1Activity.this,  (task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
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