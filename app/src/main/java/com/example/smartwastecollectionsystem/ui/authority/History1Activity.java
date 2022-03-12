package com.example.smartwastecollectionsystem.ui.authority;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smartwastecollectionsystem.R;

public class History1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history1);

        Toolbar tb =(Toolbar) findViewById(R.id.toolb);
        setSupportActionBar(tb);

      // getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_img);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.muhistory_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id = item.getItemId();

        if(item_id == R.id.myRequest){
            Intent intent = new Intent(History1Activity.this, acceptedActivity.class);
            startActivity(intent);
        } else if (item_id == R.id.list){
            Intent intent1 = new Intent(History1Activity.this, DetailsActivity.class);
            startActivity(intent1);

        }
        return true;
    }
}