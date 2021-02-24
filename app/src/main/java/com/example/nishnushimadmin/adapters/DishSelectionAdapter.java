package com.example.nishnushimadmin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.nishnushimadmin.R;
import com.example.nishnushimadmin.helpClasses.Classification;
import com.example.nishnushimadmin.helpClasses.Dish;
import com.example.nishnushimadmin.helpClasses.menuChanges.Changes;

import java.util.ArrayList;
import java.util.List;

public class DishSelectionAdapter extends BaseAdapter {

    Context context;
    Classification classification;
    Changes changes;

    List<Integer> positionList = new ArrayList<>();

    public DishSelectionAdapter(Context context, Classification classification, Changes changes) {
        this.context = context;
        this.classification = classification;
        this.changes = changes;
    }


    @Override
    public int getCount() {
        return classification.getDishList().size();
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

        convertView = LayoutInflater.from(context).inflate(R.layout.dish_change_selection_item, null);

        LinearLayout linearLayout = convertView.findViewById(R.id.linear_layout_dish_change_selection_item);
        linearLayout.setPressed(false);
        TextView textView = convertView.findViewById(R.id.text_view_dish_change_selection_item);


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (linearLayout.isPressed()){
                    linearLayout.setPressed(false);
                    linearLayout.setBackgroundColor(ContextCompat.getColor(context ,R.color.white));
                }else {
                    linearLayout.setPressed(true);
                    linearLayout.setBackgroundColor(ContextCompat.getColor(context ,R.color.custom_yellow));
                }


                if (positionList.size() > 0) {

                    boolean isExist = false;
                    int dishPosition = 0;

                    for (int i = 0; i < positionList.size(); i++) {
                        if (positionList.get(i) == position) {
                            isExist = true;
                            break;
                        }
                    }

                    if (!isExist) {
                        positionList.add(position);
                        changes.getChangesByTypesList().add(classification.getDishList().get(position));
                    } else {
                        positionList.remove(position);
                        changes.getChangesByTypesList().remove(position);
                    }

                }else{
                    positionList.add(position);
                    changes.getChangesByTypesList().add(classification.getDishList().get(position));
                }


            }
        });


        textView.setText(classification.getDishList().get(position).getName());


        return convertView;
    }
}
