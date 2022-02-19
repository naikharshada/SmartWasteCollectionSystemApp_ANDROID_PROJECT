package com.example.smartwastecollectionsystem.ui.authority;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartwastecollectionsystem.R;
import com.example.smartwastecollectionsystem.ui.localuser.UserData;

import java.util.ArrayList;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<UserData> userDataArrayList;

    public MyAdapter(Context context, ArrayList<UserData> userDataArrayList) {
        this.context = context;
        this.userDataArrayList = userDataArrayList;
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
        holder.image.setImageResource(userData.getImageUrl());

        holder.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, viewDetailsActivity.class);
                intent.putExtra("image",temp.getImageUrl());
                intent.putExtra("Address", temp.getAddress());
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

        TextView Address, Category_waste, emailID, phonenumber;
        ImageView image;
        CircularProgressButton viewDetails;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Address = itemView.findViewById(R.id.address);
            Category_waste = itemView.findViewById(R.id.waste_category);
            viewDetails = itemView.findViewById(R.id.cirViewButton);
        }
    }
}
