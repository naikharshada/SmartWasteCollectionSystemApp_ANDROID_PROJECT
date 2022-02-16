package com.example.smartwastecollectionsystem.ui.authority;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartwastecollectionsystem.R;
import com.example.smartwastecollectionsystem.ui.localuser.UserData;

import java.util.ArrayList;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<UserData> uList;
    Context context;

    public MyAdapter(Context context , ArrayList<UserData> uList) {
        this.uList = uList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.detail, parent, false);
       return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserData userData = uList.get(position);

        holder.Address.setText(userData.getAddress());
        holder.category_waste.setText(userData.getCategory());

    }

    @Override
    public int getItemCount() {
        return uList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Address, category_waste;
        CircularProgressButton viewDetails;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Address = itemView.findViewById(R.id.address);
            category_waste = itemView.findViewById(R.id.waste_category);
            viewDetails = itemView.findViewById(R.id.cirViewButton);
        }
    }
}
