package com.example.nishnushimadmin.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nishnushimadmin.R;
import com.example.nishnushimadmin.helpClasses.Classification;
import com.example.nishnushimadmin.helpClasses.Dish;
import com.example.nishnushimadmin.helpClasses.menuChanges.Changes;
import com.example.nishnushimadmin.helpClasses.menuChanges.PizzaChange;
import com.example.nishnushimadmin.helpClasses.menuChanges.RegularChange;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangeDetailByTypeAdapter extends BaseAdapter {

    Context context;
    Changes changes;
    LayoutInflater layoutInflater;


    public ChangeDetailByTypeAdapter(Context context, Changes changes) {
        this.context = context;
        this.changes = changes;

        this.layoutInflater = LayoutInflater.from(this.context);
    }



    @Override
    public int getCount() {


        return changes.getChangesByTypesList().size();
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

        convertView = layoutInflater.inflate(R.layout.detail_change_item, null);

        HashMap<String, Object> map;

        ImageButton removeDetailChangeImageBtn = convertView.findViewById(R.id.remove_change_btn_detail_changes_item);
        TextView detailChangeTextView = convertView.findViewById(R.id.change_detail_text_detail_changes_item);

        StringBuilder stringBuilder = new StringBuilder();
        RegularChange regularChange;

        Log.i("CHANGE - Adapter", changes.getChangesTypesEnum().toString());
        Log.i("CHANGE - Adapter", changes.getChangesByTypesList().get(position).toString());


        switch (changes.getChangesTypesEnum()){

            case CLASSIFICATION_CHOICE:
//                map = (HashMap<String, Object>) changes.getChangesByTypesList().get(position);
//                Dish classification = new Gson().fromJson(new Gson().toJson(map), Classification.class);
//                Classification classification = new Classification();
//                Class classificationClass = classification.getClass();
//                for (Field field : classificationClass.getFields()){
//                    if (map.containsKey(field.getName())){
//                        try {
//                            field.set(classification, map.get(field.getName()));
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }

//                Classification classification = (Classification) changes.getChangesByTypesList().get(position);
                stringBuilder.append("פירוט: ").append(changes.getChangeName());
                stringBuilder.append(" - ").append("לבחירה: ").append(changes.getFreeSelection());
                break;



            case DISH_CHOICE:

                Dish dish;
                try {
                    map = (HashMap<String, Object>) changes.getChangesByTypesList().get(position);
                    dish = new Gson().fromJson(new Gson().toJson(map), Dish.class);
                }catch (Exception e){
                    dish = (Dish) changes.getChangesByTypesList().get(position);
                }

//                Dish dish = new Dish();
//                Class dishClass = dish.getClass();
//                for (Field field : dishClass.getFields()){
//                    if (map.containsKey(field.getName())){
//                        try {
//                            field.set(dish, map.get(field.getName()));
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }

                stringBuilder.append("פירוט: ").append(dish.getName());
                break;



            case ONE_CHOICE:

                try {
                    map = (HashMap<String, Object>) changes.getChangesByTypesList().get(position);
                    regularChange = new Gson().fromJson(new Gson().toJson(map), RegularChange.class);
                }catch (Exception e){
                    regularChange = (RegularChange) changes.getChangesByTypesList().get(position);
                }

//                regularChange = new RegularChange();
//                regularClass = regularChange.getClass();
//                for (Field field : regularClass.getFields()){
//                    if (map.containsKey(field.getName())){
//                        try {
//                            field.set(regularChange, map.get(field.getName()));
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
                stringBuilder.append("פירוט: ").append(regularChange.getChange()).append(" - ").append(regularChange.getPrice()).append(" ₪ ");

                break;



            case MULTIPLE:

                try {
                    map = (HashMap<String, Object>) changes.getChangesByTypesList().get(position);
                    regularChange = new Gson().fromJson(new Gson().toJson(map), RegularChange.class);
                }catch (Exception e){
                    regularChange = (RegularChange) changes.getChangesByTypesList().get(position);
                }

//                regularChange = new RegularChange();
//                regularClass = regularChange.getClass();
//                for (Field field : regularClass.getFields()){
//                    if (map.containsKey(field.getName())){
//                        try {
//                            field.set(regularChange, map.get(field.getName()));
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
                stringBuilder.append("פירוט: ").append(regularChange.getChange()).append(" - ").append(regularChange.getPrice()).append(" ₪ ");

                break;


            case VOLUME:

                try {
                    map = (HashMap<String, Object>) changes.getChangesByTypesList().get(position);
                    regularChange = new Gson().fromJson(new Gson().toJson(map), RegularChange.class);
                }catch (Exception e){
                    regularChange = (RegularChange) changes.getChangesByTypesList().get(position);
                }

//                regularChange = new RegularChange();
//                regularClass = regularChange.getClass();
//                for (Field field : regularClass.getFields()){
//                    if (map.containsKey(field.getName())){
//                        try {
//                            field.set(regularChange, map.get(field.getName()));
//                        } catch (IllegalAccessException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
                stringBuilder.append("פירוט: ").append(regularChange.getChange()).append(" - ").append(regularChange.getPrice()).append(" ₪ ");

                break;






            case PIZZA:

                PizzaChange pizzaChange;

                try {
                    map = (HashMap<String, Object>) changes.getChangesByTypesList().get(position);
                    pizzaChange = new Gson().fromJson(new Gson().toJson(map), PizzaChange.class);
                }catch (Exception e){
                    pizzaChange = (PizzaChange) changes.getChangesByTypesList().get(position);
                }


                stringBuilder.append("פירוט: ").append(pizzaChange.getName()).append(" - ").append(pizzaChange.getCost()).append(" ₪ ");


                break;
        }


        detailChangeTextView.setText(stringBuilder.toString());


        removeDetailChangeImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changes.getChangesByTypesList().remove(position);
                notifyDataSetChanged();
            }
        });


//        stringBuilder.append("פירוט: ").append(changes.getChangesTypes().g);
//        stringBuilder.append(" - ").append("לבחירה: ").append(changesList.get(position).getFreeSelection());
//
//
//        changeTextView.setText(stringBuilder);

        return convertView;
    }
}
