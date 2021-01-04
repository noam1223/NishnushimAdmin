package com.example.nishnushimadmin.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nishnushimadmin.R;
import com.example.nishnushimadmin.RestaurantProfileActivity;
import com.example.nishnushimadmin.helpClasses.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.RestaurantsViewHolder> {


    Context context;
    List<Restaurant> restaurant;
    List<String> keys;


    public RestaurantsAdapter(Context context, List<Restaurant> restaurant, List<String> keys) {
        this.context = context;
        this.restaurant = restaurant;
        this.keys = keys;
    }


    @NonNull
    @Override
    public RestaurantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item, parent, false);
        return new RestaurantsViewHolder(view);
    }





    @Override
    public void onBindViewHolder(@NonNull RestaurantsViewHolder holder, int position) {

        holder.restaurantNameTextView.setText(restaurant.get(holder.getAdapterPosition()).getName());
        holder.restaurantNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, RestaurantProfileActivity.class);
                intent.putExtra("restaurant", restaurant.get(holder.getAdapterPosition()));
                intent.putExtra("key", keys.get(position));
                context.startActivity(intent);

            }
        });

    }





    @Override
    public int getItemCount() {
        return restaurant.size();
    }





    public class RestaurantsViewHolder extends RecyclerView.ViewHolder{

        TextView restaurantNameTextView;

        public RestaurantsViewHolder(@NonNull View itemView) {
            super(itemView);

            restaurantNameTextView = itemView.findViewById(R.id.restaurant_name_item);
        }
    }


}
