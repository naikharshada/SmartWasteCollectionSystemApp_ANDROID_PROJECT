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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class acceptedActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private ArrayList<User1Data> u1List;
    r_acceptedAdapter myAdapter;;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted);

        auth = FirebaseAuth.getInstance();
       userID = auth.getCurrentUser().getUid();

        Toolbar tbl =(Toolbar) findViewById(R.id.btool);
        setSupportActionBar(tbl);

        recyclerView = findViewById(R.id.reCview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        u1List = new ArrayList<User1Data>();
        myAdapter = new r_acceptedAdapter(acceptedActivity.this, u1List);

        recyclerView.setAdapter(myAdapter);

        EventChangeListener();

        // getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_img);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void EventChangeListener() {
        db.collection("Municipal").document(userID).collection("aList").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("Firestore Error", error.getMessage());
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        u1List.add(dc.getDocument().toObject(User1Data.class));
                    }
                    myAdapter.notifyDataSetChanged();
                }
            }
        });
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