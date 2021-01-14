package com.example.nishnushimadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nishnushimadmin.adapters.ClassificationMenuAdapter;
import com.example.nishnushimadmin.helpClasses.Classification;
import com.example.nishnushimadmin.helpClasses.Menu;
import com.example.nishnushimadmin.helpClasses.Restaurant;
import com.example.nishnushimadmin.helpClasses.callbacks.AddMenuItemCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddMenuActivity extends AppCompatActivity {

    TextView headLineTextView;
    Button addClassificationBtn, addMenuBtn;
    RecyclerView classificationRecyclerView;
    RecyclerView.Adapter classificationAdapter;

    Menu menu;
    Restaurant restaurant;
    String key;


    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        headLineTextView = findViewById(R.id.head_line_activity_add_menu);
        addClassificationBtn = findViewById(R.id.add_classification_btn_add_menu_activity);
        addMenuBtn = findViewById(R.id.add_menu_to_restaurant_btn_add_menu_activity);
        classificationRecyclerView = findViewById(R.id.recycler_view_classification_add_menu_activity);


        if (getIntent().getSerializableExtra("restaurant") != null && getIntent().getSerializableExtra("key") != null) {

            restaurant = (Restaurant) getIntent().getSerializableExtra("restaurant");
            key = (String) getIntent().getStringExtra("key");
            Toast.makeText(this, key, Toast.LENGTH_SHORT).show();


            //UPDATE UI
            headLineTextView.setText(restaurant.getName());
            initializeClassificationRecyclerView();

            //SERVER SIDE
            db = FirebaseFirestore.getInstance();


            addClassificationBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    View popUpView = getLayoutInflater().inflate(R.layout.classification_name_pop_up_window, null);
                    Dialog areaDialog = new Dialog(AddMenuActivity.this);
                    areaDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    areaDialog.setContentView(popUpView);

                    EditText editText = popUpView.findViewById(R.id.classification_name_edit_text_classification_pop_up_window);
                    Button button = popUpView.findViewById(R.id.add_classification_name_btn_classification_pop_up_window);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (!editText.getText().toString().isEmpty()) {

                                restaurant.getMenu().getClassifications().add(new Classification(editText.getText().toString(), new ArrayList<>()));

                                classificationAdapter.notifyDataSetChanged();
                                areaDialog.dismiss();

                            } else
                                Toast.makeText(AddMenuActivity.this, "יש למלא את שם הסיווג", Toast.LENGTH_SHORT).show();
                        }
                    });

                    areaDialog.create();
                    areaDialog.show();

                }
            });


            addMenuBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (restaurant.getMenu() != null && restaurant.getMenu().getClassifications().size() > 0) {

                        Toast.makeText(AddMenuActivity.this, "ADD MENU CLICKED", Toast.LENGTH_SHORT).show();


                        Map<String, Object> menuMap = new HashMap<>();
                        menuMap.put("menu", restaurant.getMenu());
                        db.collection(getResources().getString(R.string.RESTAURANTS_PATH)).document(key).update(menuMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()){

                                    Toast.makeText(AddMenuActivity.this, "התפריט נוסף בהצלחה", Toast.LENGTH_SHORT).show();
                                    finish();

                                } else if (task.isCanceled()){

                                    Toast.makeText(AddMenuActivity.this, "ישנה תקלה, אנא נסה שוב", Toast.LENGTH_SHORT).show();

                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(AddMenuActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            });





        } else finish();

    }


    private void initializeClassificationRecyclerView() {

        classificationRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        classificationRecyclerView.setLayoutManager(layoutManager);
        classificationAdapter = new ClassificationMenuAdapter(this, restaurant.getMenu());
        classificationRecyclerView.setAdapter(classificationAdapter);

    }



//    @Override
//    public void addMenuItem(Menu menu) {
//
//        restaurant.setMenu(menu);
//
//    }
//
//
//
//    @Override
//    public void addClassificationCallback(Menu.Classification classification) {
//
//    }
//
//
//    @Override
//    public void addDishCallback(Menu.Classification.Dish dish) {
//
//    }


}