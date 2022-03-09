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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartwastecollectionsystem.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class CategoryActivity extends AppCompatActivity {

    private ImageView backtomap;
    private CircularProgressButton submit;
    private RadioGroup radioGroup;
    private FirebaseFirestore dbroot;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        changeStatusBarColor();

        backtomap = findViewById(R.id.back_Map);
        submit = findViewById(R.id.cirSubmitButton);
        radioGroup = findViewById(R.id.radiogroup);
        // dry = findViewById(R.id.dry_waste);
        // wet = findViewById(R.id.wet_waste);
        //electronic = findViewById(R.id.electronic_waste);
        //unknown = findViewById(R.id.not_known);
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        dbroot = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


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
                    //Toast.makeText(getApplicationContext(), category, Toast.LENGTH_SHORT).show();
                    //insertdata();
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
        dbroot.collection("Users").document(userID).update(User).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Category Selected Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CategoryActivity.this, SuccessActivity.class));

            }
        });
       /* databaseReference.getRef().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Category").setValue(category).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Category saved successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CategoryActivity.this, ClickPictureActivity.class));
            }
        });*/



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
