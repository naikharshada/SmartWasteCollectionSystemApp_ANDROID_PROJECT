package com.example.smartwastecollectionsystem.ui.authority;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.smartwastecollectionsystem.R;
import com.google.android.material.navigation.NavigationView;

public class DetailsActivity extends AppCompatActivity {

   NavigationView nav;
   ActionBarDrawerToggle toggle;
   DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        changeStatusBarColor();

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        nav = (NavigationView)findViewById(R.id.navmenu);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home_menu:
                        Intent intent1 = new Intent(DetailsActivity.this, HomePage1Activity.class);
                        startActivity(intent1);
                        break;

                    case R.id.notify:
                        Intent intent2 = new Intent(DetailsActivity.this, Notify1Activity.class);
                        startActivity(intent2);
                        break;

                    case R.id.feedback:
                        Intent intent3 = new Intent(DetailsActivity.this, Feedback1Activity.class);
                        startActivity(intent3);
                        break;

                    case R.id.about_us:
                        Intent intent4 = new Intent(DetailsActivity.this, About_us1Activity.class);
                        startActivity(intent4);
                        break;



                }
                return false;
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