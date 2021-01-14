package com.example.nishnushimadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.nishnushimadmin.adapters.ClassificationMenuAdapter;
import com.example.nishnushimadmin.adapters.RestaurantsAdapter;
import com.example.nishnushimadmin.helpClasses.Restaurant;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsListActivity extends AppCompatActivity {

    RecyclerView restaurantRecyclerView;
    RecyclerView.Adapter restaurantAdapter;

    Button addRestaurantBtn;

    FirebaseFirestore db;
    List<Restaurant> restaurants = new ArrayList<>();
    List<String> keys = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_list);

        addRestaurantBtn = findViewById(R.id.move_to_add_restaurant_btn__restaurant_list_activity);
        restaurantRecyclerView = findViewById(R.id.restaurant_recycler_view_activity_restaurant_list);
        db = FirebaseFirestore.getInstance();

        initializeClassificationRecyclerView();

        addRestaurantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RestaurantsListActivity.this, AddRestaurantActivity.class));

            }
        });



        db.collection(getResources().getString(R.string.RESTAURANTS_PATH)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    if (task.getResult() != null) {

                        for (DocumentSnapshot documentSnapshot :
                                task.getResult()) {

                            restaurants.add(documentSnapshot.toObject(Restaurant.class));

                            keys.add(documentSnapshot.getId());
                        }

                        restaurantAdapter.notifyDataSetChanged();


                    } else
                        Toast.makeText(RestaurantsListActivity.this, "אין מסעדות כרגע להציג", Toast.LENGTH_SHORT).show();

                } else if (task.isCanceled() && task.getException() != null){

                    Toast.makeText(RestaurantsListActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RestaurantsListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void initializeClassificationRecyclerView() {

        restaurantRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        restaurantRecyclerView.setLayoutManager(layoutManager);
        restaurantAdapter = new RestaurantsAdapter(this ,restaurants, keys);
        restaurantRecyclerView.setAdapter(restaurantAdapter);

    }




}