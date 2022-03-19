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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout fullname_var, email_var, phonenumber_var, password_var;
    private CircularProgressButton registerbtn;
    private TextView signin_btn;
    private FirebaseAuth auth;
    private FirebaseFirestore dbroot;
    String userID, token;

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
        dbroot = FirebaseFirestore.getInstance();

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {

                    if(!task.isSuccessful()){
                        return;
                    }

                    token = task.getResult();
                });

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

                userID = auth.getCurrentUser().getUid();
                DocumentReference documentReference = dbroot.collection("Users").document(userID);
                Map<String,Object> User = new HashMap<>();
                User.put("userID",userID);
                User.put("username", fullname_);
                User.put("email", email_);
                User.put("phone", phonenumber_);
                User.put("password", password_);
                User.put("userToken", token);
                documentReference.set(User).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(RegisterActivity.this,"Registered Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
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
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }
}








