package com.example.smartwastecollectionsystem.ui.authority;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smartwastecollectionsystem.R;

public class acceptedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted);

        Toolbar tbl =(Toolbar) findViewById(R.id.btool);
        setSupportActionBar(tbl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.muaccepted_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id = item.getItemId();

        if(item_id == R.id.history){
            Intent intent = new Intent(acceptedActivity.this, History1Activity.class);
            startActivity(intent);
        } else if (item_id == R.id.list_){
            Intent intent1 = new Intent(acceptedActivity.this, DetailsActivity.class);
            startActivity(intent1);

        }
        return true;
    }
}