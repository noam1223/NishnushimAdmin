package com.example.nishnushimadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooserActivity extends AppCompatActivity {

    Button restaurantsBtn, usersBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);

        restaurantsBtn = findViewById(R.id.restaurants_btn_chooser_activity);
        usersBtn = findViewById(R.id.users_btn_chooser_activity);

        restaurantsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ChooserActivity.this, RestaurantsListActivity.class));

            }
        });


        usersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

    }

}