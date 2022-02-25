package com.example.smartwastecollectionsystem.ui.authority;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartwastecollectionsystem.R;

public class viewDetailsActivity extends AppCompatActivity {

    private ImageView backtodetails, wastePicture;
    private TextView addr, cat, eml, pho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        changeStatusBarColor();

        backtodetails = findViewById(R.id.back_details);
        wastePicture = findViewById(R.id.garbage_pic);
        addr = findViewById(R.id.address);
        cat = findViewById(R.id.Category);
        eml = findViewById(R.id.detail_Id);
        pho = findViewById(R.id.detail_phone);


        addr.setText(getIntent().getStringExtra("Address"));
        wastePicture.setImageResource(getIntent().getIntExtra("imageurl", 0));
        //wastePicture.setText(getIntent().getStringExtra("image"));
        cat.setText(getIntent().getStringExtra("Category_waste"));
        eml.setText(getIntent().getStringExtra("emailID"));
        pho.setText(getIntent().getStringExtra("phonenumber"));

       /* wastePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);

            }
        });*/

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