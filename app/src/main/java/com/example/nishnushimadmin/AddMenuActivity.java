package com.example.nishnushimadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.nishnushimadmin.adapters.ClassificationMenuAdapter;
import com.example.nishnushimadmin.helpClasses.Classification;
import com.example.nishnushimadmin.helpClasses.Dish;
import com.example.nishnushimadmin.helpClasses.Menu;
import com.example.nishnushimadmin.helpClasses.Restaurant;
import com.example.nishnushimadmin.helpClasses.callbacks.AddMenuItemCallback;
import com.example.nishnushimadmin.helpClasses.menuChanges.Changes;
import com.example.nishnushimadmin.helpClasses.menuChanges.PizzaChange;
import com.example.nishnushimadmin.helpClasses.menuChanges.RegularChange;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddMenuActivity extends AppCompatActivity {

    TextView headLineTextView;
    Button addClassificationBtn, addMenuBtn;
    RecyclerView classificationRecyclerView;
    RecyclerView.Adapter classificationAdapter;

    Restaurant restaurant;


    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        headLineTextView = findViewById(R.id.head_line_activity_add_menu);
        addClassificationBtn = findViewById(R.id.add_classification_btn_add_menu_activity);
        addMenuBtn = findViewById(R.id.add_menu_to_restaurant_btn_add_menu_activity);
        classificationRecyclerView = findViewById(R.id.recycler_view_classification_add_menu_activity);


//        if (getIntent().getSerializableExtra("restaurant") != null && getIntent().getSerializableExtra("key") != null) {

//            restaurant = new Restaurant();
//            restaurant.setMenu(new Menu());

//            key = "134255";

            restaurant = (Restaurant) getIntent().getSerializableExtra("restaurant");


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

                                restaurant.getMenu().getClassifications().add(new Classification(editText.getText().toString(), new ArrayList<>(), new ArrayList<>()));

                                classificationAdapter.notifyDataSetChanged();
                                areaDialog.dismiss();

                            } else
                                Toast.makeText(AddMenuActivity.this, "יש למלא את שם הסיווג", Toast.LENGTH_SHORT).show();
                        }
                    });



                    areaDialog.create();
                    areaDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    areaDialog.show();

                }
            });





            addMenuBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (restaurant.getMenu() != null && restaurant.getMenu().getClassifications().size() > 0) {

                        volleyPostMenu();
                        finish();

//                        Map<String, Object> menuMap = new HashMap<>();
//                        menuMap.put("menu", restaurant.getMenu());
//                        db.collection(getResources().getString(R.string.RESTAURANTS_PATH)).document(restaurant.getId()).update(menuMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//
//                                if (task.isSuccessful()){
//
//                                    Toast.makeText(AddMenuActivity.this, "התפריט נוסף בהצלחה", Toast.LENGTH_SHORT).show();
//                                    finish();
//
//                                } else if (task.isCanceled()){
//
//                                    Toast.makeText(AddMenuActivity.this, "ישנה תקלה, אנא נסה שוב", Toast.LENGTH_SHORT).show();
//
//                                }
//
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//
//                                Toast.makeText(AddMenuActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                            }
//                        });
                    }
                }
            });





//        } else finish();

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


    public void volleyPostMenu() {
        String postUrl = "http://10.0.2.2:8080/api/restaurants/" + restaurant.getId() + "/menu";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject menuJsonObject = getMenuJsonObject();


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, postUrl, menuJsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null && response.length() > 0){
                    Log.i("RESPONSE", response.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("MENU ERROR ADD", error.getMessage());
            }
        }){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));

                    JSONObject result = null;

                    if (jsonString.length() > 0)
                        result = new JSONObject(jsonString);

                    return Response.success(result,
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException | JSONException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    private JSONObject getMenuJsonObject() {
        JSONObject menuJsonObject = new JSONObject();

        try {

            JSONArray classificationJsonArray = new JSONArray();

            for (Classification classification : restaurant.getMenu().getClassifications()) {

                Log.i("Classification ADD MENU", classification.toString());
                JSONObject classificationJsonObject = new JSONObject();
                JSONArray classificationChangesJsonArray = new JSONArray();
                classificationJsonObject.put("classificationName", classification.getClassificationName());
                JSONArray dishJsonArray = new JSONArray();



                for (Dish dish : classification.getDishList()){

                    Log.i("DISH ADD MENU", dish.toString());

                    JSONObject dishJsonObject = new JSONObject();
                    JSONArray dishChangesJsonArray = new JSONArray();
                    dishJsonObject.put("name", dish.getName());
                    dishJsonObject.put("details", dish.getDetails());
                    dishJsonObject.put("price", dish.getPrice());


                    for (Changes changes : dish.getChanges()){
                        JSONObject changeDishJsonObject = new JSONObject();
                        changeDishJsonObject.put("changeName", changes.getChangeName());
                        changeDishJsonObject.put("freeSelection", changes.getFreeSelection());
                        changeDishJsonObject.put("changesTypesEnum", changes.getChangesTypesEnum());

                        JSONArray changesTypeJsonArray = getChangesTypeJsonArray(changes);

                        changeDishJsonObject.put("changesByTypesList", changesTypeJsonArray);
                        dishChangesJsonArray.put(changeDishJsonObject);

                    }
                    dishJsonObject.put("changes", dishChangesJsonArray);
                    dishJsonArray.put(dishJsonObject);
                }



                for (Changes changes : classification.getChangesList()){
                    JSONObject changeClassificationJsonObject = new JSONObject();
                    changeClassificationJsonObject.put("changeName", changes.getChangeName());
                    changeClassificationJsonObject.put("freeSelection", changes.getFreeSelection());
                    changeClassificationJsonObject.put("changesTypesEnum", changes.getChangesTypesEnum());

                    JSONArray changesTypeJsonArray = getChangesTypeJsonArray(changes);

                    changeClassificationJsonObject.put("changesByTypesList", changesTypeJsonArray);
                    classificationChangesJsonArray.put(changeClassificationJsonObject);
                }

                classificationJsonObject.put("dishList", dishJsonArray);
                classificationJsonObject.put("changesList", classificationChangesJsonArray);
                classificationJsonArray.put(classificationJsonObject);

            }

            menuJsonObject.put("classifications", classificationJsonArray);

        }catch (JSONException e){
            e.printStackTrace();
        }
        return menuJsonObject;
    }

    private JSONArray getChangesTypeJsonArray(Changes changes) throws JSONException {
        JSONArray changesTypeJsonArray = new JSONArray();

        for (Object object : changes.getChangesByTypesList()){
            JSONObject changeTypeJsonObject = new JSONObject();

            switch (changes.getChangesTypesEnum()){

                case ONE_CHOICE:
                    RegularChange regularChange = (RegularChange) object;
                    changeTypeJsonObject.put("change", regularChange.getChange());
                    changeTypeJsonObject.put("price",regularChange.getPrice());
                    break;

                case PIZZA:
                    PizzaChange pizzaChange = (PizzaChange) object;
                    changeTypeJsonObject.put("id", pizzaChange.getId());
                    changeTypeJsonObject.put("name", pizzaChange.getName());
                    changeTypeJsonObject.put("cost", pizzaChange.getCost());
                    break;

                case CLASSIFICATION_CHOICE:

                    break;

                case MULTIPLE:
                    RegularChange regularChange1 = (RegularChange) object;
                    changeTypeJsonObject.put("change", regularChange1.getChange());
                    changeTypeJsonObject.put("price",regularChange1.getPrice());

                    break;

                case VOLUME:
                    RegularChange regularChange2 = (RegularChange) object;
                    changeTypeJsonObject.put("change", regularChange2.getChange());
                    changeTypeJsonObject.put("price",regularChange2.getPrice());
                    break;

                case DISH_CHOICE:
                    Dish dish = (Dish) object;
                    changeTypeJsonObject.put("name", dish.getName());
                    changeTypeJsonObject.put("details", dish.getDetails());
                    changeTypeJsonObject.put("price", dish.getPrice());
                    changeTypeJsonObject.put("changes", new ArrayList<>());

                    break;


            }
            changesTypeJsonArray.put(changeTypeJsonObject);
        }
        return changesTypeJsonArray;
    }
}