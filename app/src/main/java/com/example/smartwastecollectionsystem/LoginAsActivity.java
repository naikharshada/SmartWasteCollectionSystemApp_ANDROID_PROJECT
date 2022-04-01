package com.example.smartwastecollectionsystem;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartwastecollectionsystem.ui.authority.Login1Activity;
import com.example.smartwastecollectionsystem.ui.localuser.LoginActivity;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class LoginAsActivity extends AppCompatActivity {

    private CircularProgressButton localbtn, municipalbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_as);
        changeStatusBarColor();

        localbtn = findViewById(R.id.cirLocalButton);
        municipalbtn = findViewById(R.id.cirAuthButton);


        localbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginAsActivity.this, LoginActivity.class));
            }
        });

        municipalbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(LoginAsActivity.this, Login1Activity.class));
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