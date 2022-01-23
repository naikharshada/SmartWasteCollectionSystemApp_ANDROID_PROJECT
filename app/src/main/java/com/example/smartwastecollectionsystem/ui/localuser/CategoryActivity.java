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
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class CategoryActivity extends AppCompatActivity {

    private ImageView backtomap;
    private CircularProgressButton submit;
    private RadioGroup radioGroup;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;


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

                if(selected_category == null){
                    Snackbar snackbar = Snackbar.make(view, "Select Category",
                            Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.black));
                    snackbar.show();
                } else {
                    final String category = selected_category.getText().toString();
                    //Toast.makeText(getApplicationContext(), category, Toast.LENGTH_SHORT).show();
                   send(category);
                }

                //Toast.makeText(getApplicationContext(), radioButton.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void send(String category) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(auth.getCurrentUser().getUid());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Category", category);
        databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CategoryActivity.this, "Category successfully selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CategoryActivity.this, "Category not selected", Toast.LENGTH_SHORT).show();
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
