package com.example.nishnushimadmin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.nishnushimadmin.R;
import com.example.nishnushimadmin.helpClasses.AreasForDeliveries;

import java.util.List;

public class AreaForDeliveryAdapter extends BaseAdapter {

    Context context;
    List<AreasForDeliveries> areasForDeliveries;



    public AreaForDeliveryAdapter(Context context, List<AreasForDeliveries> areasForDeliveries) {
        this.context = context;
        this.areasForDeliveries = areasForDeliveries;
    }



    @Override
    public int getCount() {
        return areasForDeliveries.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return areasForDeliveries.get(position + 1);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.area_for_delivery_item, null);

        TextView areaNameTextView = view.findViewById(R.id.area_name_text_view_area_for_delivery_item);
        TextView deliveryCostTextView = view.findViewById(R.id.delivery_cost_text_view_area_for_delivery_item);
        TextView minToDeliverTextView = view.findViewById(R.id.min_to_deliver_text_view_are_for_delivery_item);
        TextView timeOfDeliveryTextView = view.findViewById(R.id.time_of_delivery_text_view_area_for_delivery_item);
        Button deleteAreaForDeliveryBtn = view.findViewById(R.id.delete_btn_area_for_delivery_item);

        if (position == 0){

            areaNameTextView.setBackground(ContextCompat.getDrawable(context, R.drawable.bottom_line_selector));
            deliveryCostTextView.setBackground(ContextCompat.getDrawable(context, R.drawable.bottom_line_selector));
            minToDeliverTextView.setBackground(ContextCompat.getDrawable(context, R.drawable.bottom_line_selector));
            timeOfDeliveryTextView.setBackground(ContextCompat.getDrawable(context, R.drawable.bottom_line_selector));

            view.setClickable(false);
            deleteAreaForDeliveryBtn.setVisibility(View.INVISIBLE);
            return view;

        }else {

            final int realPosition = position - 1;
            areaNameTextView.setText(areasForDeliveries.get(realPosition).getAreaName());
            deliveryCostTextView.setText(String.valueOf(areasForDeliveries.get(realPosition).getDeliveryCost()));
            minToDeliverTextView.setText(String.valueOf(areasForDeliveries.get(realPosition).getMinToDeliver()));
            timeOfDeliveryTextView.setText(String.valueOf(areasForDeliveries.get(realPosition).getTimeOfDelivery()));

            deleteAreaForDeliveryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    areasForDeliveries.remove(realPosition);
                    notifyDataSetChanged();

                }
            });

        }

        return view;
    }



}
