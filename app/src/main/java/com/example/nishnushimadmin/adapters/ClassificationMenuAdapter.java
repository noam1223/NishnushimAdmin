package com.example.nishnushimadmin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nishnushimadmin.R;
import com.example.nishnushimadmin.helpClasses.Dish;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClassificationMenuAdapter extends RecyclerView.Adapter<ClassificationMenuAdapter.ClassificationMenuViewHolder> {

    Context context;
    Map<String, List<Dish>> dishMap;
    List<String> dishKeyString = new ArrayList<>();
    List<DishMenuAdapter> dishMenuAdapters = new ArrayList<>();




    @NonNull
    @Override
    public ClassificationMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.classification_menu_item, parent, false);
        return new ClassificationMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassificationMenuViewHolder holder, int position) {

        holder.headLineTextView.setText(dishKeyString.get(position));


        holder.addDishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });





    }


    private void initializeClassificationRecyclerView(ClassificationMenuViewHolder holder, int position, Context context) {

        holder.dishRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, true);
        holder.dishRecyclerView.setLayoutManager(layoutManager);
        DishMenuAdapter dishMenuAdapter = new DishMenuAdapter();
        dishMenuAdapters.add(dishMenuAdapter);
        holder.dishRecyclerView.setAdapter(dishMenuAdapter);

    }



    @Override
    public int getItemCount() {
        return dishMap.size();
    }



    public class ClassificationMenuViewHolder extends RecyclerView.ViewHolder{


        TextView headLineTextView;
        Button addDishBtn;
        RecyclerView dishRecyclerView;

        public ClassificationMenuViewHolder(@NonNull View itemView) {
            super(itemView);


            headLineTextView = itemView.findViewById(R.id.title_text_view_classification_menu_item);
            addDishBtn = itemView.findViewById(R.id.add_dish_btn_classification_menu_item);
            dishRecyclerView = itemView.findViewById(R.id.dish_recycler_view_classification_menu_dish_item);

        }
    }
}
