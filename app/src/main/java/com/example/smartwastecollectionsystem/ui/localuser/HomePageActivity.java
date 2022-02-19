package com.example.smartwastecollectionsystem.ui.localuser;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartwastecollectionsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class HomePageActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firestore;
    private CircularProgressButton updatebtn;
    private DatabaseReference databaseReference;
    private EditText na, em, ph, pa;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        changeStatusBarColor();

        na = findViewById(R.id.db_Name);
        em = findViewById(R.id.db_Email);
        ph = findViewById(R.id.db_Mobile);
        pa = findViewById(R.id.db_Password);
        updatebtn = findViewById(R.id.cirUpdateButton);
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();

        userID = auth.getCurrentUser().getUid();


        DocumentReference documentReference  = firestore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                na.setText(value.getString("username"));
                em.setText(value.getString("email"));
                ph.setText(value.getString("phone"));
                pa.setText(value.getString("password"));

            }
        });

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String na_ = na.getText().toString();
                String em_ = em.getText().toString();
                String ph_ = ph.getText().toString();
                String pa_ = pa.getText().toString();

                if((na_.equals(na)) || (em_.equals(em)) || (ph_.equals(ph)) || (pa_.equals(pa))){
                    Snackbar snackbar = Snackbar.make(view, "Data is same and can not be updated",
                            Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.black));
                    snackbar.show();

                } else {
                    if (!em_.matches("^[\\w.+\\-]+@" +"[^-][A-Za-z]+\\.com$")) {
                        Snackbar snackbar = Snackbar.make(view, "Invalid Email",
                                Snackbar.LENGTH_LONG);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(getResources().getColor(R.color.black));
                        snackbar.show();

                    } else {
                        if (!ph_.matches("^\\d{10}$")) {
                            Snackbar snackbar = Snackbar.make(view, "Enter ten digit number",
                                    Snackbar.LENGTH_LONG);
                            View snackbarView = snackbar.getView();
                            snackbarView.setBackgroundColor(getResources().getColor(R.color.black));
                            snackbar.show();
                        } else {

                            updatedata(na_, em_, ph_, pa_);
                        }
                    }
                }
            }
        });


    }

    private void updatedata(String na_, String em_, String ph_, String pa_) {
        Map<String, Object> User = new HashMap();
        User.put("username",na_);
        User.put("email",em_);
        User.put("phone",ph_);
        User.put("password",pa_);
        firestore.collection("Users").document(userID).update(User).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){


                    Toast.makeText(HomePageActivity.this,"Successfully Updated",Toast.LENGTH_SHORT).show();

                }else {

                    Toast.makeText(HomePageActivity.this,"Failed to Update",Toast.LENGTH_SHORT).show();

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