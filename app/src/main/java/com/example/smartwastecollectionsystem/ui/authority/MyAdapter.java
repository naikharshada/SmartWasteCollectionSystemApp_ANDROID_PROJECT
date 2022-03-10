package com.example.smartwastecollectionsystem.ui.authority;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartwastecollectionsystem.R;
import com.example.smartwastecollectionsystem.ui.localuser.UserData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<UserData> userDataArrayList;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public MyAdapter(Context context, ArrayList<UserData> userDataArrayList) {
        this.context = context;
        this.userDataArrayList = userDataArrayList;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.detail,parent,false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        UserData userData = userDataArrayList.get(position);

        final UserData temp= userDataArrayList.get(position);

        holder.Address.setText(userData.getAddress());
        holder.Category_waste.setText(userData.getCategory());
       // holder.emailID.setText(userData.getEmail());
        //holder.phonenumber.setText(userData.getPhone());
        holder.Latitude.setText(userData.getLatitude()+"");
        holder.Longitude.setText(userData.getLongitude()+"");
        Glide.with(holder.imageurl.getContext()).load(userDataArrayList.get(position).getImageurl()).into(holder.imageurl);

        holder.Lat = holder.Latitude.getText().toString();
        holder.Lon = holder.Longitude.getText().toString();

        holder.Address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Opening Google Map...", Toast.LENGTH_SHORT).show();
                Uri mapUri = Uri.parse("google.navigation:q="+holder.Lat+","+holder.Lon);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
            }
        });


        holder.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //holder.acceptDetails.setClickable(false);
               // holder.viewDetails.setEnabled(false);
                Intent intent = new Intent(context, viewDetailsActivity.class);
                intent.putExtra("Address", temp.getAddress());
                intent.putExtra("rdate", temp.getRequestDate());
                intent.putExtra("rtime", temp.getRequestTime());
                intent.putExtra("Latitude",temp.getLatitude()+"");
                intent.putExtra("Longitude", temp.getLongitude()+"");
                intent.putExtra("Category_waste", temp.getCategory());
                intent.putExtra("emailID", temp.getEmail());
                intent.putExtra("phonenumber",temp.getPhone());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userDataArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Address, Category_waste, emailID, phonenumber, Latitude, Longitude, rdate, rtime;
        ImageView imageurl;
        CircularProgressButton viewDetails;
        String Lat, Lon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Address = itemView.findViewById(R.id.addr);
            Category_waste = itemView.findViewById(R.id.waste_category);
            viewDetails = itemView.findViewById(R.id.cirDetailsButton);
            imageurl = (ImageView)itemView.findViewById(R.id.imageView);
            Latitude = itemView.findViewById(R.id.lati);
            Longitude = itemView.findViewById(R.id.longi);
        }
    }
}
