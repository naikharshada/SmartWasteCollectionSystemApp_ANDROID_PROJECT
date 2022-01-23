package com.example.smartwastecollectionsystem.ui.localuser;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartwastecollectionsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout fullname_var, email_var, phonenumber_var, password_var;
    private CircularProgressButton registerbtn;
    private TextView signin_btn;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();


        fullname_var = findViewById(R.id.textInputName);
        email_var = findViewById(R.id.textInputEmail);
        phonenumber_var = findViewById(R.id.textInputMobile);
        password_var = findViewById(R.id.textInputPassword);
        registerbtn = findViewById(R.id.cirRegisterButton);
        signin_btn = findViewById(R.id.sign_in);
        auth = FirebaseAuth.getInstance();

        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fullname_ = fullname_var.getEditText().getText().toString();
                final String email_ = email_var.getEditText().getText().toString();
                final String password_ = password_var.getEditText().getText().toString();
                final String phonenumber_ = phonenumber_var.getEditText().getText().toString();


                if (TextUtils.isEmpty(fullname_) || TextUtils.isEmpty(email_) || TextUtils.isEmpty(phonenumber_) || TextUtils.isEmpty(password_)) {
                    Snackbar snackbar = Snackbar.make(view, "All fields are required",
                            Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.black));
                    snackbar.show();
                 } else {
                    if (!email_.matches("^[\\w.+\\-]+@" +"[^-][A-Za-z]+\\.com$")) {
                        Snackbar snackbar = Snackbar.make(view, "Invalid Email",
                                Snackbar.LENGTH_LONG);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.black));
                        snackbar.show();
                    } else {
                        if (!phonenumber_.matches("^\\d{10}$")) {
                            Snackbar snackbar = Snackbar.make(view, "Enter ten digit number",
                                    Snackbar.LENGTH_LONG);
                            View snackbarView = snackbar.getView();
                            snackbarView.setBackgroundColor(getResources().getColor(R.color.black));
                            snackbar.show();
                        } else {
                            register(fullname_, email_, phonenumber_, password_);
                        }
                    }
                }
            }

});

    }

    private void register(String fullname_, String email_, String phonenumber_, String password_) {
        auth.createUserWithEmailAndPassword(email_, password_).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser rUser = auth.getCurrentUser();
                    assert rUser != null;
                    String userId = rUser.getUid();
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("userID", userId);
                    hashMap.put("username", fullname_);
                    hashMap.put("email", email_);
                    hashMap.put("phone", phonenumber_);
                    hashMap.put("password", password_);
                    databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {


                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                Toast.makeText(RegisterActivity.this,"Registered Successfully" + (task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            } else {
                                Toast.makeText(RegisterActivity.this, (task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this,  (task.getException()).getMessage(), Toast.LENGTH_SHORT).show();

                }

            }


        });
    }


    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }
}








