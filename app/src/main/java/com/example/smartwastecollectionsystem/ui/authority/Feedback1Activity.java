package com.example.smartwastecollectionsystem.ui.authority;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartwastecollectionsystem.R;

public class Feedback1Activity extends AppCompatActivity {

    private ImageView backclick;
    private TextView f_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback1);


        backclick = findViewById(R.id.back_imgclick);
        f_email = findViewById(R.id.feedback_email_);


        backclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Feedback1Activity.this, DetailsActivity.class));
            }

        });

        f_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open email using client intent
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "swcs@gmail.com", null));
                startActivity(Intent.createChooser(emailIntent, "Send mail"));
            }

        });
    }
}