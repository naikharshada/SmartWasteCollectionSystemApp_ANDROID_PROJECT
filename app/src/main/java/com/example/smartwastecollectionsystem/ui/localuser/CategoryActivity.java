package com.example.smartwastecollectionsystem.ui.localuser;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartwastecollectionsystem.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class CategoryActivity extends AppCompatActivity {

    private ImageView backtomap;
    private TextView u_id, u_ph, u_eid, u_token;
    private CircularProgressButton submit;
    private RadioGroup radioGroup;
    private FirebaseFirestore dbroot;
    private FirebaseAuth auth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        changeStatusBarColor();

        backtomap = findViewById(R.id.back_Map);
        u_id = findViewById(R.id.userid);
        u_ph = findViewById(R.id.userphone);
        u_token = findViewById(R.id.usertoken);
        u_eid = findViewById(R.id.usermail);
        submit = findViewById(R.id.cirSubmitButton);
        radioGroup = findViewById(R.id.radiogroup);
        auth = FirebaseAuth.getInstance();

        dbroot = FirebaseFirestore.getInstance();

        userID = auth.getCurrentUser().getUid();

        DocumentReference documentReference  = dbroot.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                u_id.setText(value.getString("userID"));
                u_ph.setText(value.getString("phone"));
                u_eid.setText(value.getString("email"));
                u_token.setText(value.getString("userToken"));
            }
        });

        backtomap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CategoryActivity.this, MapActivity.class));
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton selected_category = radioGroup.findViewById(selectedId);

                if (selected_category == null) {
                    Snackbar snackbar = Snackbar.make(view, "Select Category",
                            Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.black));
                    snackbar.show();
                } else {
                    final String category = selected_category.getText().toString();
                    send(category);
                }
            }
        });
    }

    private void send(String category) {
        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        userID = auth.getCurrentUser().getUid();
        Map<String,Object> User = new HashMap<>();
        User.put("Category",category);
        User.put("requestTime", saveCurrentTime);
        User.put("requestDate", saveCurrentDate);
        User.put("email", u_eid.getText().toString());
        User.put("userID", u_id.getText().toString());
        User.put("phone", u_ph.getText().toString());
        User.put("userToken", u_token.getText().toString());
        dbroot.collection("requestList").document(userID).update(User).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Category Selected Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CategoryActivity.this, SuccessActivity.class));

            }
        });

        dbroot.collection("Users").document(userID).collection("rList").document(userID).update(User).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

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
