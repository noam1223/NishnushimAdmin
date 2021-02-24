package com.example.nishnushimadmin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nishnushimadmin.R;
import com.example.nishnushimadmin.helpClasses.menuChanges.Changes;
import com.example.nishnushimadmin.helpClasses.menuChanges.RegularChange;

import java.util.List;

public class RemoveChangeBaseAdapter extends BaseAdapter {

    Context context;
    List<Changes> changesList;
    LayoutInflater layoutInflater;


    public RemoveChangeBaseAdapter(Context context, List<Changes> changesList) {
        this.context = context;
        this.changesList = changesList;

        this.layoutInflater = LayoutInflater.from(this.context);
    }



    @Override
    public int getCount() {
        return changesList.size();
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
        ListView detailChangesListView = convertView.findViewById(R.id.detail_changes_list_view_remove_changes_item);
        detailChangesListView.setAdapter(new ChangeDetailByTypeAdapter(context, changesList.get(position)));


        StringBuilder stringBuilder = new StringBuilder();


        stringBuilder.append("פירוט: ").append(changesList.get(position).getChangeName());
        stringBuilder.append(" - ").append("לבחירה: ").append(changesList.get(position).getFreeSelection());


        changeTextView.setText(stringBuilder.toString());

        removeImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changesList.remove(position);
                notifyDataSetChanged();
            }
        });


        return convertView;
    }
}
