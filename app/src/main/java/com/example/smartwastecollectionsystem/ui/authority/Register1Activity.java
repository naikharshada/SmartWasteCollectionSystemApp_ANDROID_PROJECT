package com.example.smartwastecollectionsystem.ui.authority;

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

import java.util.HashMap;
import java.util.Map;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


public class Register1Activity extends AppCompatActivity {

    private TextInputLayout branchname_var, memail_var, mphonenumber_var, mpassword_var;
    private CircularProgressButton registerbtn;
    private TextView signin_btn;
    private FirebaseAuth auth;
    private FirebaseFirestore dbroot;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        changeStatusBarColor();

        branchname_var = findViewById(R.id.textInputName);
        memail_var = findViewById(R.id.textInputEmail);
        mphonenumber_var = findViewById(R.id.textInputMobile);
        mpassword_var = findViewById(R.id.textInputPassword);
        registerbtn = findViewById(R.id.cirRegisterButton);
        signin_btn = findViewById(R.id.sign_in);
        auth = FirebaseAuth.getInstance();
        dbroot = FirebaseFirestore.getInstance();

        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register1Activity.this, Login1Activity.class));
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String branchname_ = branchname_var.getEditText().getText().toString();
                final String memail_ = memail_var.getEditText().getText().toString();
                final String mpassword_ = mpassword_var.getEditText().getText().toString();
                final String mphonenumber_ = mphonenumber_var.getEditText().getText().toString();
                
                   if (TextUtils.isEmpty(branchname_) || TextUtils.isEmpty(memail_) || TextUtils.isEmpty(mphonenumber_) || TextUtils.isEmpty(mpassword_)) {
                    Snackbar snackbar = Snackbar.make(view, "All fields are required",
                            Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.black));
                    snackbar.show();
                 } else {
                       if (!memail_.matches("^[\\w.+\\-]+@" +"[^-][A-Za-z]+(\\.gov+)*(\\.in)$")) {
                        Snackbar snackbar = Snackbar.make(view, "Invalid Email",
                                Snackbar.LENGTH_LONG);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.black));
                        snackbar.show();
                    } else {
                        if (!mphonenumber_.matches("^\\d{8}$")) {
                            Snackbar snackbar = Snackbar.make(view, "Enter eight digit Telephone number",
                                    Snackbar.LENGTH_LONG);
                            View snackbarView = snackbar.getView();
                            snackbarView.setBackgroundColor(getResources().getColor(R.color.black));
                            snackbar.show();
                        } else {
                            register(branchname_, memail_, mphonenumber_, mpassword_);
                        }
                    }
                }
            
            }
        });
    }

    private void register(String branchname_, String memail_, String mphonenumber_, String mpassword_) {
        auth.createUserWithEmailAndPassword(memail_ , mpassword_).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                userID = auth.getCurrentUser().getUid();
                DocumentReference documentReference = dbroot.collection("Municipal").document(userID);
                Map<String,Object> User = new HashMap<>();
                User.put("userID", userID);
                User.put("BranchName", branchname_);
                User.put("memail", memail_);
                User.put("mphone", mphonenumber_);
                User.put("mpassword", mpassword_);
                documentReference.set(User).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Register1Activity.this,"Registered Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register1Activity.this, Login1Activity.class);
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