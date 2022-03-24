package com.example.smartwastecollectionsystem.ui.localuser;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartwastecollectionsystem.R;

public class FeedbackActivity extends AppCompatActivity {

    private ImageView backtoclick;
    private TextView femail_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        backtoclick = findViewById(R.id.back_imgC);
        femail_ = findViewById(R.id.feedback_email);

        backtoclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FeedbackActivity.this, ClickPictureActivity.class));
            }
        });

        femail_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open email using client intent
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "swcs@gmail.com", null));
                startActivity(Intent.createChooser(emailIntent, "Send mail"));
            }
        });
    }
}