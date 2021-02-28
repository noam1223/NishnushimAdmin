package com.example.nishnushimadmin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nishnushimadmin.R;
import com.example.nishnushimadmin.helpClasses.AreasForDelivery;


import java.util.List;

public class AreasForDeliveryAdapter extends BaseAdapter {

    Context context;
    List<AreasForDelivery> areasForDeliveries;
    LayoutInflater layoutInflater;

    public AreasForDeliveryAdapter(Context context, List<AreasForDelivery> areasForDeliveries) {
        this.context = context;
        this.areasForDeliveries = areasForDeliveries;

        this.layoutInflater = LayoutInflater.from(this.context);
    }



    @Override
    public int getCount() {
        return areasForDeliveries.size() + 1;
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

        if (position == 0) {

            convertView = layoutInflater.inflate(R.layout.restaurant_area_for_deliver_title_item, null);

            return convertView;
        }


        convertView = layoutInflater.inflate(R.layout.restaurant_area_for_deliver_detail_item, null );
        TextView areaForDeliverTextView = convertView.findViewById(R.id.area_for_delivery_name_text_view_area_for_delivery_details_item);
        TextView deliveryCostTextView = convertView.findViewById(R.id.delivery_cost_text_view_area_for_delivery_details_item);
        TextView minToDeliverTextView = convertView.findViewById(R.id.min_to_deliver_text_view_area_for_delivery_details_item);
        TextView deliveryTimeTextView = convertView.findViewById(R.id.delivery_time_text_view_area_for_delivery_details_item);


        areaForDeliverTextView.setText(areasForDeliveries.get(position - 1).getAreaName());
        deliveryCostTextView.setText(String.valueOf(areasForDeliveries.get(position - 1).getDeliveryCost()));
        minToDeliverTextView.setText(String.valueOf(areasForDeliveries.get(position - 1).getMinToDeliver()));
        deliveryTimeTextView.setText(String.valueOf(areasForDeliveries.get(position - 1).getTimeOfDelivery()));


        return convertView;

    }



}
