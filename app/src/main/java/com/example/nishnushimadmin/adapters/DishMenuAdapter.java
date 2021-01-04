package com.example.nishnushimadmin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nishnushimadmin.R;
import com.example.nishnushimadmin.helpClasses.Classification;
import com.example.nishnushimadmin.helpClasses.Menu;

public class DishMenuAdapter extends RecyclerView.Adapter<DishMenuAdapter.DishMenuViewHolder> {

    Context context;
    Classification classification;


    public DishMenuAdapter(Context context, Classification classification) {
        this.context = context;
        this.classification = classification;
    }


    @NonNull
    @Override
    public DishMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_menu_item, parent, false);
        return new DishMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishMenuViewHolder holder, int position) {

        holder.dishNameTextView.setText(classification.getDishList().get(position).getName());
        holder.dishDetailsTextView.setText(classification.getDishList().get(position).getDetails());
        holder.priceTextView.setText(String.valueOf(classification.getDishList().get(position).getPrice()));

    }




    @Override
    public int getItemCount() {
        return classification.getDishList().size();
    }



    public class DishMenuViewHolder extends RecyclerView.ViewHolder{

        TextView dishNameTextView, dishDetailsTextView, priceTextView;
        ImageView dishImageView;

        public DishMenuViewHolder(@NonNull View itemView) {
            super(itemView);

            dishNameTextView = itemView.findViewById(R.id.dish_name_text_view_dish_menu_item);
            dishDetailsTextView = itemView.findViewById(R.id.dish_detail_text_view_dish_menu_item);
            priceTextView = itemView.findViewById(R.id.dish_price_text_view_dish_menu_item);

            dishImageView = itemView.findViewById(R.id.dish_image_view_dish_menu_item);

        }
    }
}
