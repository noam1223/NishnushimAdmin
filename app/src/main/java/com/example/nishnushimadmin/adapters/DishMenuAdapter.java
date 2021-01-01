package com.example.nishnushimadmin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nishnushimadmin.R;

public class DishMenuAdapter extends RecyclerView.Adapter<DishMenuAdapter.DishMenuViewHolder> {




    @NonNull
    @Override
    public DishMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.classification_menu_item, parent, false);
        return new DishMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishMenuViewHolder holder, int position) {


    }






    @Override
    public int getItemCount() {
        return 0;
    }



    public class DishMenuViewHolder extends RecyclerView.ViewHolder{



        public DishMenuViewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }
}
