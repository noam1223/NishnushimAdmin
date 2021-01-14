package com.example.nishnushimadmin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nishnushimadmin.R;
import com.example.nishnushimadmin.helpClasses.DishChanges;

import java.util.List;

public class RemoveChangeBaseAdapter extends BaseAdapter {

    Context context;
    List<DishChanges> dishChangesList;
    LayoutInflater layoutInflater;


    public RemoveChangeBaseAdapter(Context context, List<DishChanges> dishChangesList) {
        this.context = context;
        this.dishChangesList = dishChangesList;

        this.layoutInflater = LayoutInflater.from(this.context);
    }



    @Override
    public int getCount() {
        return dishChangesList.size();
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

        convertView = layoutInflater.inflate(R.layout.remove_changes_item, null);

        ImageButton removeImageBtn = convertView.findViewById(R.id.remove_change_btn_remove_changes_item);
        TextView changeTextView = convertView.findViewById(R.id.change_detail_text_view_remove_changes_item);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("פירוט: ").append(dishChangesList.get(position).getChange());
        stringBuilder.append(" - ").append("עלות: ").append(dishChangesList.get(position).getPrice());

        changeTextView.setText(dishChangesList.get(position).getChange());

        removeImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dishChangesList.remove(position);
                notifyDataSetChanged();
            }
        });


        return convertView;
    }
}
