package com.example.nishnushimadmin.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nishnushimadmin.AddMenuActivity;
import com.example.nishnushimadmin.R;
import com.example.nishnushimadmin.helpClasses.Classification;
import com.example.nishnushimadmin.helpClasses.DishChanges;
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


        holder.addChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View popUpView = LayoutInflater.from(context).inflate(R.layout.add_change_pop_up_window, null);
                Dialog changeAddDialog = new Dialog(context);
                changeAddDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                changeAddDialog.setContentView(popUpView);


                EditText changeEditText = popUpView.findViewById(R.id.change_detail_edit_text_add_change_pop_up_window);
                EditText changeSumEditText = popUpView.findViewById(R.id.change_sum_edit_text_add_change_pop_up_window);
                Button addChangeBtn = popUpView.findViewById(R.id.add_change_btn_add_change_pop_up_window);

                addChangeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String change = changeEditText.getText().toString();
                        String sum = changeSumEditText.getText().toString();


                        if (!change.isEmpty()) {

                            int sumInteger = 0;
                            if (!sum.isEmpty()){
                                sumInteger = Integer.parseInt(sum);
                            }


                            classification.getDishList().get(position).getChanges().add(new DishChanges(change, sumInteger));
                            notifyDataSetChanged();
                        }
                    }
                });


                changeAddDialog.show();
            }
        });



        if (!classification.getDishList().get(position).getChanges().isEmpty()) {

            holder.removeChangeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    //TODO: TO THINK OF SOLUTION TO REMOVE CHANGE
                    View popUpView = LayoutInflater.from(context).inflate(R.layout.remove_dish_changes_pop_up_window, null);
                    Dialog removeDialog = new Dialog(context);
                    removeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    removeDialog.setContentView(popUpView);


                    ListView removeChangesListView = popUpView.findViewById(R.id.dish_changes_list_view_remove_dish_changes_pop_up_window);
                    removeChangesListView.setAdapter(new RemoveChangeBaseAdapter(context, classification.getDishList().get(position).getChanges()));

                    removeDialog.show();
                }
            });


            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < classification.getDishList().get(position).getChanges().size(); i++) {

                DishChanges dishChanges = classification.getDishList().get(position).getChanges().get(i);
                stringBuilder.append(dishChanges.getChange());
                stringBuilder.append(" - ");
                stringBuilder.append(dishChanges.getPrice()).append("₪");
                stringBuilder.append(" ,");

            }

            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            holder.changesTextView.setText(stringBuilder);

        }


    }


    @Override
    public int getItemCount() {
        return classification.getDishList().size();
    }


    public class DishMenuViewHolder extends RecyclerView.ViewHolder {

        TextView dishNameTextView, dishDetailsTextView, priceTextView, changesTextView;
        ImageView dishImageView;
        Button addChangeBtn, removeChangeBtn;

        public DishMenuViewHolder(@NonNull View itemView) {
            super(itemView);

            dishNameTextView = itemView.findViewById(R.id.dish_name_text_view_dish_menu_item);
            dishDetailsTextView = itemView.findViewById(R.id.dish_detail_text_view_dish_menu_item);
            priceTextView = itemView.findViewById(R.id.dish_price_text_view_dish_menu_item);

            dishImageView = itemView.findViewById(R.id.dish_image_view_dish_menu_item);

            changesTextView = itemView.findViewById(R.id.changes_text_view_dish_menu_item);
            addChangeBtn = itemView.findViewById(R.id.add_change_btn_dish_menu_item);
            removeChangeBtn = itemView.findViewById(R.id.remove_change_btn_dish_menu_item);

        }
    }
}
