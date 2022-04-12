package com.example.smartwastecollectionsystem.ui.authority;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartwastecollectionsystem.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class History1Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<User1Data> user1DataArrayList;
    HistoryAdapter myAdapter;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history1);

        Toolbar tb =(Toolbar) findViewById(R.id.toolb);
        setSupportActionBar(tb);

        recyclerView = findViewById(R.id.rview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        user1DataArrayList = new ArrayList<User1Data>();
        myAdapter = new HistoryAdapter(History1Activity.this, user1DataArrayList);

        recyclerView.setAdapter(myAdapter);

        EventChangeListener();
    }

    private void EventChangeListener() {
        db.collection("acceptList").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("Firestore Error", error.getMessage());
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        user1DataArrayList.add(dc.getDocument().toObject(User1Data.class));
                    }
                    myAdapter.notifyDataSetChanged();
                }
            }
        });
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