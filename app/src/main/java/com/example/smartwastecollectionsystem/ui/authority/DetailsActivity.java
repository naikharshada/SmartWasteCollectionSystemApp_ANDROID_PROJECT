package com.example.smartwastecollectionsystem.ui.authority;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.smartwastecollectionsystem.LoginAsActivity;
import com.example.smartwastecollectionsystem.R;
import com.example.smartwastecollectionsystem.ui.localuser.ClickPictureActivity;
import com.example.smartwastecollectionsystem.ui.localuser.LoginActivity;
import com.example.smartwastecollectionsystem.ui.localuser.UserData;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

   NavigationView nav;
   ActionBarDrawerToggle toggle;
   DrawerLayout drawerLayout;
   RecyclerView recyclerView;
   ArrayList<UserData> userDataArrayList;
   MyAdapter myAdapter;
   FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        changeStatusBarColor();

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        nav = (NavigationView)findViewById(R.id.navmenu);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        db = FirebaseFirestore.getInstance();
        userDataArrayList = new ArrayList<UserData>();
        myAdapter = new MyAdapter(DetailsActivity.this, userDataArrayList);

        recyclerView.setAdapter(myAdapter);

        EventChangeListener();

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

                    case R.id.log_out:
                        logout(this);
                        break;
                }
                return false;
            }

        });

    }

    private void EventChangeListener() {
        db.collection("User").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Log.e("Firestore Error",error.getMessage());
                    return;
                }
                for(DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType() == DocumentChange.Type.ADDED){
                        userDataArrayList.add(dc.getDocument().toObject(UserData.class));
                    }
                    myAdapter.notifyDataSetChanged();
                }

            }
        });
    }

    private void logout(NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Do you want to logout ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //DetailsActivity.this.finishAffinity();
                Toast.makeText(DetailsActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DetailsActivity.this, Login1Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
               // System.exit(0);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               dialogInterface.dismiss();
            }
        });
       builder.show();
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