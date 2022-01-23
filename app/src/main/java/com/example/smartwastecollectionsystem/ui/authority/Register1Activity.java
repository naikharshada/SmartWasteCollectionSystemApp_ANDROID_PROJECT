package com.example.smartwastecollectionsystem.ui.authority;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartwastecollectionsystem.R;
import com.example.smartwastecollectionsystem.ui.localuser.LoginActivity;
import com.example.smartwastecollectionsystem.ui.localuser.RegisterActivity;
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


public class Register1Activity extends AppCompatActivity {

    private TextInputLayout branchname_var, memail_var, mphonenumber_var, mpassword_var;
    private CircularProgressButton registerbtn;
    private TextView signin_btn;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

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
                            Snackbar snackbar = Snackbar.make(view, "Enter Eight digit Telephone number",
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
                if (task.isSuccessful()) {
                    FirebaseUser rUser = auth.getCurrentUser();
                    assert rUser != null;
                    String userId = rUser.getUid();
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("userID", userId);
                    hashMap.put("BranchName", branchname_);
                    hashMap.put("memail", memail_);
                    hashMap.put("mphone", mphonenumber_);
                    hashMap.put("mpassword", mpassword_);
                    databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {


                                Intent intent = new Intent(Register1Activity.this, Login1Activity.class);
                                Toast.makeText(Register1Activity.this,"Registered Successfully" + (task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            } else {
                                Toast.makeText(Register1Activity.this, (task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(Register1Activity.this,  (task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
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