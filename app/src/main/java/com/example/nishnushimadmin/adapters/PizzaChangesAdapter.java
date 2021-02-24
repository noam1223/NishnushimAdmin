package com.example.nishnushimadmin.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.nishnushimadmin.R;
import com.example.nishnushimadmin.helpClasses.Dish;
import com.example.nishnushimadmin.helpClasses.UIHelp.ChangePizzaHelper;
import com.example.nishnushimadmin.helpClasses.menuChanges.Changes;
import com.example.nishnushimadmin.helpClasses.menuChanges.PizzaChange;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class PizzaChangesAdapter extends BaseAdapter {

    //WORK AS TWO SEPERATE LISTS VIEW


    Context context;
    ChangePizzaHelper changePizzaType;
    Changes changes;
    LayoutInflater layoutInflater;

    ListView pizzaChangeAddedLisView;

    boolean isAddedList;


    public PizzaChangesAdapter(Context context, Changes changes) {
        this.context = context;
        this.changes = changes;
        this.changePizzaType = new ChangePizzaHelper();
        this.isAddedList = true;
        this.layoutInflater = LayoutInflater.from(this.context);
    }


    public PizzaChangesAdapter(Context context, Changes changes, ListView pizzaChangeAddedLisView) {
        this.context = context;
        this.changes = changes;
        this.changePizzaType = new ChangePizzaHelper();
        this.isAddedList = false;
        this.pizzaChangeAddedLisView = pizzaChangeAddedLisView;

        this.layoutInflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        //TODO: CHECK LATER

        if (isAddedList){
            return changes.getChangesByTypesList().size();
        }

        return changePizzaType.getChangePizzaTypes().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.pizza_change_item, null);


        ImageView changeImageView = convertView.findViewById(R.id.pizza_change_image_view_pizza_change_item);
        TextView changeNameTextView = convertView.findViewById(R.id.pizza_change_name_text_view_pizza_dish_change_item);
        TextView dishCostTextView = convertView.findViewById(R.id.pizza_change_cost_text_view_pizza_change_item);
        LinearLayout addChangeAreaLinearLayout = convertView.findViewById(R.id.add_change_pizza_to_pizza_linear_layout_area_pizza_change_item);




        if (isAddedList){

            addChangeAreaLinearLayout.setVisibility(View.GONE);
            PizzaChange pizzaChange;

            try {
                Map<String, Object> map = (HashMap<String, Object>) changes.getChangesByTypesList().get(position);
                pizzaChange = new Gson().fromJson(new Gson().toJson(map), PizzaChange.class);
            }catch (Exception e){
                pizzaChange = (PizzaChange) changes.getChangesByTypesList().get(position);
            }



            for (int i = 0; i < changePizzaType.getChangePizzaTypes().size(); i++) {

                if (changePizzaType.getChangePizzaTypes().get(i).getId() == pizzaChange.getId()){

                    changeImageView.setImageDrawable(ContextCompat.getDrawable(context, changePizzaType.getChangePizzaTypes().get(i).getSrcImg()));
                    break;
                }

            }




            changeNameTextView.setText(pizzaChange.getName());

            //TODO: SHOW SET PRICE POP UP
            if (pizzaChange.getCost() != -1){
                dishCostTextView.setText(String.valueOf(pizzaChange.getCost()));
            }else dishCostTextView.setText("מחיר");


            dishCostTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    View popUpView = LayoutInflater.from(context).inflate(R.layout.cost_pizza_change_pop_up_window, null);
                    Dialog pizzaChangeCostDialog = new Dialog(context);
                    pizzaChangeCostDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    pizzaChangeCostDialog.setContentView(popUpView);


                    EditText costChangeEditText = popUpView.findViewById(R.id.change_cost_edit_text_cost_pizza_change_pop_up_window);
                    Button addBtn = popUpView.findViewById(R.id.add_btn_cost_pizza_change_pop_up_window);
                    Button cancelBtn = popUpView.findViewById(R.id.cancel_btn_cost_pizza_change_pop_up_window);



                    addBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String cost = costChangeEditText.getText().toString();

                            if (!cost.isEmpty()){
                                ((PizzaChange) changes.getChangesByTypesList().get(position)).setCost(Integer.parseInt(cost));
                                costChangeEditText.setText(cost);
                            }else
                                Toast.makeText(context, "אנא הכנס מספר", Toast.LENGTH_SHORT).show();
                        }
                    });



                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            pizzaChangeCostDialog.dismiss();

                        }
                    });


                    pizzaChangeCostDialog.create();
                    pizzaChangeCostDialog.show();

                }
            });




        }else {

            PizzaChange pizzaChange;

            pizzaChange = new PizzaChange(changePizzaType.getChangePizzaTypes().get(position).getId());
            pizzaChange.setName(changePizzaType.getChangePizzaTypes().get(position).getName());


            dishCostTextView.setVisibility(View.GONE);
            changeImageView.setImageDrawable(ContextCompat.getDrawable(context, changePizzaType.getChangePizzaTypes().get(position).getSrcImg()));
            changeNameTextView.setText(changePizzaType.getChangePizzaTypes().get(position).getName());




            addChangeAreaLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    for (int i = 0; i < changes.getChangesByTypesList().size(); i++) {
                        if (((PizzaChange)changes.getChangesByTypesList().get(i)).getId() == pizzaChange.getId()){
                            Toast.makeText(context, "אין אפשרות להוסיף פעמיים מאותו שינוי", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }


                    changes.getChangesByTypesList().add(pizzaChange);
//                    if (pizzaChangeAddedLisView.getAdapter() != null){
//                        pizzaChangeAddedLisView.setAdapter(null);
//                    }

                    if (pizzaChangeAddedLisView.getAdapter() != null){
                        pizzaChangeAddedLisView.setAdapter(null);
                    }

                    pizzaChangeAddedLisView.setAdapter(new PizzaChangesAdapter(context, changes));



                }
            });
        }


        return convertView;
    }
}
