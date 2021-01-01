package com.example.nishnushimadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nishnushimadmin.adapters.ClassificationMenuAdapter;
import com.example.nishnushimadmin.helpClasses.Dish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddMenuActivity extends AppCompatActivity {

    TextView headLineTextView;
    Button addClassificationBtn;
    RecyclerView classificationRecyclerView;
    RecyclerView.Adapter classificationAdapter;

    Map<String, List<Dish>> dishMap = new HashMap<>();
    List<String> dishKeyString = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        addClassificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View popUpView = getLayoutInflater().inflate(R.layout.classification_name_pop_up_window, null);
                Dialog areaDialog = new Dialog(getApplicationContext());
                areaDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                areaDialog.setContentView(popUpView);

                EditText editText = popUpView.findViewById(R.id.classification_name_edit_text_classification_pop_up_window);
                Button button = popUpView.findViewById(R.id.add_classification_name_btn_classification_pop_up_window);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!editText.getText().toString().isEmpty()){

                            dishMap.put(editText.getText().toString(), new ArrayList<>());
                            dishKeyString.add(editText.getText().toString());
                            classificationAdapter.notifyDataSetChanged();

                        }

                    }
                });

            }
        });


        initializeClassificationRecyclerView();

    }




    private void initializeClassificationRecyclerView() {

        classificationRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, true);
        classificationRecyclerView.setLayoutManager(layoutManager);
        classificationAdapter = new ClassificationMenuAdapter();
        classificationRecyclerView.setAdapter(classificationAdapter);

    }

}