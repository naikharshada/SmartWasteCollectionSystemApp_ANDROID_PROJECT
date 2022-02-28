package com.example.smartwastecollectionsystem.ui.authority;

import android.content.Context;
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
        holder.emailID.setText(userData.getEmail());
        holder.phonenumber.setText(userData.getPhone());
        Glide.with(holder.imageurl.getContext()).load(userDataArrayList.get(position).getImageurl()).into(holder.imageurl);


        holder.acceptDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent = new Intent(context, viewDetailsActivity.class);
                intent.putExtra("Address", temp.getAddress());
                intent.putExtra("imageurl", temp.getImageurl());
                intent.putExtra("Category_waste", temp.getCategory());
                intent.putExtra("emailID", temp.getEmail());
                intent.putExtra("phonenumber",temp.getPhone());

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);*/
                Toast.makeText(context,"Request accepted successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return userDataArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Address, Category_waste, emailID, phonenumber;
        ImageView imageurl;
        CircularProgressButton acceptDetails;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Address = itemView.findViewById(R.id.addr);
            Category_waste = itemView.findViewById(R.id.waste_category);
            acceptDetails = itemView.findViewById(R.id.cirAcceptButton);
            emailID = itemView.findViewById(R.id.user_Id);
            phonenumber = itemView.findViewById(R.id.user_phone);
            imageurl = (ImageView)itemView.findViewById(R.id.imageView);
        }
    }
}
