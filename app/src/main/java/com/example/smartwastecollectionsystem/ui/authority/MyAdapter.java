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
import com.example.smartwastecollectionsystem.ui.localuser.requestModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<requestModel> userDataArrayList;

    public MyAdapter(Context context, ArrayList<requestModel> userDataArrayList) {
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

        requestModel requestmodel = userDataArrayList.get(position);

        final requestModel temp= userDataArrayList.get(position);

        holder.Address.setText(requestmodel.getAddress());
        holder.Category_waste.setText(requestmodel.getCategory());
        holder.Latitude.setText(requestmodel.getLatitude()+"");
        holder.status.setText(requestmodel.getMsg());
        holder.Longitude.setText(requestmodel.getLongitude()+"");
        holder.usereid_.setText(requestmodel.getEmail());
        Glide.with(holder.imageurl.getContext()).load(userDataArrayList.get(position).getImageurl()).into(holder.imageurl);

        holder.Lat = holder.Latitude.getText().toString();
        holder.Lon = holder.Longitude.getText().toString();

        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.USEREID = holder.usereid_.getText().toString();
                holder.DeleteData(holder.USEREID);
                notifyDataSetChanged();
            }
        });

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
                intent.putExtra("tok", temp.getUserToken());
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
        
        private FirebaseFirestore db;
        TextView Address, Category_waste, emailID, phonenumber, Latitude, Longitude, rdate, rtime, tok, usereid_, status;
        ImageView imageurl, Delete;
        CircularProgressButton viewDetails;
        String Lat, Lon, USEREID;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Address = itemView.findViewById(R.id.addr);
            Category_waste = itemView.findViewById(R.id.waste_category);
            viewDetails = itemView.findViewById(R.id.cirDetailsButton);
            usereid_ = itemView.findViewById(R.id.user_id);
            imageurl = (ImageView)itemView.findViewById(R.id.imageView);
            Latitude = itemView.findViewById(R.id.lati);
            Longitude = itemView.findViewById(R.id.longi);
            status = itemView.findViewById(R.id.text_msg);
            Delete = itemView.findViewById(R.id.card_delete);
            db = FirebaseFirestore.getInstance();
        }

        public void DeleteData(String userid) {

            db.collection("requestList")
                    .whereEqualTo("email",USEREID)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if(task.isSuccessful() && !task.getResult().isEmpty()){
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        String documentID = documentSnapshot.getId();
                        db.collection("requestList")
                                .document(documentID)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                      Toast.makeText(viewDetails.getContext(), "Request deleted successfully", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

                    }else {

                    }

                }
            });
        }
    }
}
