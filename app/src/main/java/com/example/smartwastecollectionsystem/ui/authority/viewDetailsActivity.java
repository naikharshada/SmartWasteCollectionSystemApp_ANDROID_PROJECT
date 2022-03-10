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

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartwastecollectionsystem.R;

public class viewDetailsActivity extends AppCompatActivity {

    private ImageView backtodetails, gotomap;
    private TextView addrs, cat, eml, pho, rd, rt, latitude, longitude;
    String La, Lo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        changeStatusBarColor();

        backtodetails = findViewById(R.id.back_details);
        gotomap = findViewById(R.id.location_detail);
        addrs = findViewById(R.id.address1);
        latitude = findViewById(R.id.lat);
        longitude = findViewById(R.id.lon);
        cat = findViewById(R.id.Category);
        rd = findViewById(R.id.request_date);
        rt = findViewById(R.id.request_time);
        eml = findViewById(R.id.detail_Id);
        pho = findViewById(R.id.detail_phone);


      addrs.setText(getIntent().getStringExtra("Address"));
        latitude.setText(getIntent().getStringExtra("Latitude"));
        longitude.setText(getIntent().getStringExtra("Longitude"));
        rd.setText(getIntent().getStringExtra("rdate"));
        rt.setText(getIntent().getStringExtra("rtime"));
        cat.setText(getIntent().getStringExtra("Category_waste"));
        eml.setText(getIntent().getStringExtra("emailID"));
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