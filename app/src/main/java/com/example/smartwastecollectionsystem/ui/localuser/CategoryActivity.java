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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.smartwastecollectionsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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
        userID = auth.getCurrentUser().getUid();
        Map<String,Object> User = new HashMap<>();
        User.put("Category",category);
        dbroot.collection("User").document(userID).update(User).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Category Selected Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CategoryActivity.this, ClickPictureActivity.class));

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
