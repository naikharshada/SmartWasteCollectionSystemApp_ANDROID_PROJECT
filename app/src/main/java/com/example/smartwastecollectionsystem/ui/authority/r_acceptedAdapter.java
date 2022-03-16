package com.example.smartwastecollectionsystem.ui.authority;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartwastecollectionsystem.R;

import java.util.ArrayList;

public class r_acceptedAdapter extends RecyclerView.Adapter<r_acceptedAdapter.MyViewHolder> {

    Context context;
    ArrayList<User1Data> u1List;

    public r_acceptedAdapter(Context context, ArrayList<User1Data> u1List) {
        this.context = context;
        this.u1List = u1List;
    }

    @NonNull
    @Override
    public r_acceptedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.r_accepted,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull r_acceptedAdapter.MyViewHolder holder, int position) {

        User1Data user1Data = u1List.get(position);

        holder.r_location.setText(user1Data.getWasteLocation());
        holder.r_cat.setText(user1Data.getWasteCategory());
        holder.rmu_name.setText(user1Data.getBranchName());
        holder.ru_email.setText(user1Data.getUserMail());
        holder.r_acceptDate.setText(user1Data.getAcceptDate());
        holder.r_acceptTime.setText(user1Data.getAcceptTime());

    }

    @Override
    public int getItemCount() {
        return u1List.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

       TextView r_location, r_cat, rmu_name, ru_email, r_acceptDate, r_acceptTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

        r_location = itemView.findViewById(R.id.r_addr);
        r_cat = itemView.findViewById(R.id.r_category);
        rmu_name = itemView.findViewById(R.id.r_name);
        ru_email = itemView.findViewById(R.id.r_email);
        r_acceptDate = itemView.findViewById(R.id.r_date);
        r_acceptTime = itemView.findViewById(R.id.r_time);

        }
    }
}
