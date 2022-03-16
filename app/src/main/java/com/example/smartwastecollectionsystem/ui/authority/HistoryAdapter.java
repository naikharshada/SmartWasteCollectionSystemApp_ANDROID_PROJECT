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

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    Context context;
    ArrayList<User1Data> user1DataArrayList;

    public HistoryAdapter(Context context, ArrayList<User1Data> user1DataArrayList) {
        this.context = context;
        this.user1DataArrayList = user1DataArrayList;
    }

    @NonNull
    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.MyViewHolder holder, int position) {

        User1Data user1Data = user1DataArrayList.get(position);

        holder.h_location.setText(user1Data.getWasteLocation());
        holder.h_cat.setText(user1Data.getWasteCategory());
        holder.hmu_na.setText(user1Data.getBranchName());
        holder.hmu_em.setText(user1Data.getMemail());
        holder.hu_em.setText(user1Data.getUserMail());
        holder.h_accDate.setText(user1Data.getAcceptDate());
        holder.h_accTime.setText(user1Data.getAcceptTime());

    }

    @Override
    public int getItemCount() {
        return user1DataArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView h_location, h_cat, hmu_na, hmu_em, hu_em, h_accDate, h_accTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            h_location = itemView.findViewById(R.id.h_addr);
            h_cat = itemView.findViewById(R.id.h_category);
            hmu_na = itemView.findViewById(R.id.hmu_name);
            hmu_em = itemView.findViewById(R.id.hmu_email);
            hu_em = itemView.findViewById(R.id.h_email);
            h_accDate = itemView.findViewById(R.id.hre_date);
            h_accTime = itemView.findViewById(R.id.hre_time);

        }
    }
}
