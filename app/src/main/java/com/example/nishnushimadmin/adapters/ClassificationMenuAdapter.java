package com.example.nishnushimadmin.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nishnushimadmin.R;
import com.example.nishnushimadmin.helpClasses.Dish;
import com.example.nishnushimadmin.helpClasses.Menu;
import com.example.nishnushimadmin.helpClasses.callbacks.AddMenuItemCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClassificationMenuAdapter extends RecyclerView.Adapter<ClassificationMenuAdapter.ClassificationMenuViewHolder> {

    Context context;
    Menu menu;
    List<DishMenuAdapter> dishMenuAdapters = new ArrayList<>();
    AddMenuItemCallback addMenuItemCallback;


    public ClassificationMenuAdapter(Context context, Menu menu) {
        this.context = context;
        this.menu = menu;


        for (int i = 0; i < menu.getClassifications().size(); i++) {

            dishMenuAdapters.add(null);

        }
    }

    @NonNull
    @Override
    public ClassificationMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.classification_menu_item, parent, false);
        return new ClassificationMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassificationMenuViewHolder holder, int position) {

        holder.headLineTextView.setText(menu.getClassifications().get(position).getClassificationName());


        holder.addDishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                View popUpView = LayoutInflater.from(context).inflate(R.layout.dish_details_pop_up_window, null);
                Dialog areaDialog = new Dialog(context);
                areaDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                areaDialog.setContentView(popUpView);


                EditText dishNameEditText = popUpView.findViewById(R.id.dish_name_edit_text_dish_details_pop_up_window);
                EditText dishDetailsEditText = popUpView.findViewById(R.id.dish_details_edit_text_dish_details_pop_up_window);
                EditText dishPriceEditText = popUpView.findViewById(R.id.dish_price_edit_text_dish_details_pop_up_window);
                Button takeDishPic = popUpView.findViewById(R.id.dish_picture_btn_dish_details_pop_up_window);
                Button addDishBtn = popUpView.findViewById(R.id.add_dish_btn_dish_details_pop_up_window);


                takeDishPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });



                addDishBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String name = dishNameEditText.getText().toString();
                        String details = dishDetailsEditText.getText().toString();
                        String price = dishPriceEditText.getText().toString();

                        Toast.makeText(context, name + " - " + details + " - " + price, Toast.LENGTH_SHORT).show();

                        if (!name.isEmpty() && !details.isEmpty() && !price.isEmpty()){

                            menu.getClassifications().get(position).getDishList().add(new Dish(name, details,new ArrayList<>() ,Integer.parseInt(price)));
//                            dishMenuAdapters.add(null);
//                            dishMenuAdapters.get(position).notifyDataSetChanged();
                            initializeClassificationRecyclerView(holder, position, context);
                            areaDialog.dismiss();

                        } else Toast.makeText(context, "המנה הוספה בהצלחה", Toast.LENGTH_SHORT).show();


                    }
                });


                areaDialog.create();
                areaDialog.show();

            }
        });


        if (dishMenuAdapters.size() >= position + 1 && dishMenuAdapters.get(position) == null) {

            initializeClassificationRecyclerView(holder, position, context);

        } else if (dishMenuAdapters.size() > 0) dishMenuAdapters.get(position).notifyDataSetChanged();

//        if (dishMenuAdapters.size() > position + 1){
//            dishMenuAdapters.get(position).notifyDataSetChanged();
//        }

    }




    private void initializeClassificationRecyclerView(ClassificationMenuViewHolder holder, int position, Context context) {

        holder.dishRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, true);
        holder.dishRecyclerView.setLayoutManager(layoutManager);
        DishMenuAdapter dishMenuAdapter = new DishMenuAdapter(context, menu.getClassifications().get(position));
        dishMenuAdapters.add(dishMenuAdapter);
        holder.dishRecyclerView.setAdapter(dishMenuAdapter);

    }


    @Override
    public int getItemCount() {
        return menu.getClassifications().size();
    }


    public class ClassificationMenuViewHolder extends RecyclerView.ViewHolder {

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
