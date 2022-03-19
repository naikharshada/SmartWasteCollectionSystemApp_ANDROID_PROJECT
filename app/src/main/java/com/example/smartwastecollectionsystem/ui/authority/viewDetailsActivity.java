package com.example.smartwastecollectionsystem.ui.authority;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartwastecollectionsystem.FcmNotificationsSender;
import com.example.smartwastecollectionsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

public class viewDetailsActivity extends AppCompatActivity {

    private ImageView backtodetails, gotomap;
    private CircularProgressButton acceptRequest;
    private TextView addrs, cat, eml, pho, rd, rt, latitude, longitude, branch_name, m_eid, msg, token, title;
    String La, Lo;
    private FirebaseFirestore dbroot;
    private FirebaseAuth auth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        changeStatusBarColor();

        backtodetails = findViewById(R.id.back_details);
        gotomap = findViewById(R.id.location_detail);
        acceptRequest = findViewById(R.id.cirAcceptButton);
        addrs = findViewById(R.id.address1);
        latitude = findViewById(R.id.lat);
        longitude = findViewById(R.id.lon);
        cat = findViewById(R.id.Category);
        rd = findViewById(R.id.request_date);
        rt = findViewById(R.id.request_time);
        eml = findViewById(R.id.detail_Id);
        pho = findViewById(R.id.detail_phone);
        auth = FirebaseAuth.getInstance();
        dbroot = FirebaseFirestore.getInstance();
        branch_name = findViewById(R.id.m_name);
        m_eid = findViewById(R.id.m_id);
        msg = findViewById(R.id.msg);
        token = findViewById(R.id.tokn);
        title = findViewById(R.id.title);
        auth = FirebaseAuth.getInstance();

        userID = auth.getCurrentUser().getUid();

        DocumentReference documentReference  = dbroot.collection("Municipal").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                branch_name.setText(value.getString("BranchName"));
                m_eid.setText(value.getString("memail"));
            }
        });

        addrs.setText(getIntent().getStringExtra("Address"));
        latitude.setText(getIntent().getStringExtra("Latitude"));
        longitude.setText(getIntent().getStringExtra("Longitude"));
        rd.setText(getIntent().getStringExtra("rdate"));
        rt.setText(getIntent().getStringExtra("rtime"));
        cat.setText(getIntent().getStringExtra("Category_waste"));
        eml.setText(getIntent().getStringExtra("emailID"));
        token.setText(getIntent().getStringExtra("tok"));
        pho.setText(getIntent().getStringExtra("phonenumber"));

        La = latitude.getText().toString();
        Lo = longitude.getText().toString();

        gotomap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(viewDetailsActivity.this,"Opening Google Map...", Toast.LENGTH_SHORT).show();
                Uri mapUri = Uri.parse("google.navigation:q="+La+","+Lo);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        backtodetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(viewDetailsActivity.this, DetailsActivity.class));
            }
        });

        acceptRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(token.getText().toString(),
                        title.getText().toString(),msg.getText().toString(),
                        getApplicationContext(),viewDetailsActivity.this);
                notificationsSender.SendNotifications();

                acceptRequest.setEnabled(true);
                acceptRequest.setEnabled(false);

                String saveCurrentDate, saveCurrentTime;
                Calendar calForDate = Calendar.getInstance();

                SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
                saveCurrentDate = currentDate.format(calForDate.getTime());

                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                saveCurrentTime = currentTime.format(calForDate.getTime());

                userID = auth.getCurrentUser().getUid();
                Map<String,Object> User = new HashMap<>();
                User.put("acceptDate", saveCurrentDate);
                User.put("acceptTime", saveCurrentTime);
                User.put("wasteLocation", addrs.getText().toString());
                User.put("wasteCategory", cat.getText().toString());
                User.put("userMail", eml.getText().toString());
                User.put("memail", m_eid.getText().toString());
                User.put("BranchName", branch_name.getText().toString());
                dbroot.collection("acceptList").add(User).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                     Toast.makeText(viewDetailsActivity.this, "Request accepted successfully", Toast.LENGTH_SHORT).show();
                    }
                });

                dbroot.collection("Municipal").document(userID).collection("aList").add(User).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                       //Toast.makeText(viewDetailsActivity.this, "Request accepted successfully", Toast.LENGTH_SHORT).show();
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