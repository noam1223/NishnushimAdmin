package com.example.nishnushimadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.nishnushimadmin.adapters.RestaurantsAdapter;
import com.example.nishnushimadmin.helpClasses.AreasForDelivery;
import com.example.nishnushimadmin.helpClasses.Classification;
import com.example.nishnushimadmin.helpClasses.Dish;
import com.example.nishnushimadmin.helpClasses.Menu;
import com.example.nishnushimadmin.helpClasses.MyAddress;
import com.example.nishnushimadmin.helpClasses.RecommendationRestaurant;
import com.example.nishnushimadmin.helpClasses.Restaurant;

import com.example.nishnushimadmin.helpClasses.User;
import com.example.nishnushimadmin.helpClasses.menuChanges.Changes;
import com.example.nishnushimadmin.helpClasses.menuChanges.PizzaChange;
import com.example.nishnushimadmin.helpClasses.menuChanges.RegularChange;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsListActivity extends AppCompatActivity {

    RecyclerView restaurantRecyclerView;
    RecyclerView.Adapter restaurantAdapter;

    Button addRestaurantBtn;

    FirebaseFirestore db;
    List<Restaurant> restaurants = new ArrayList<>();


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


//        db.collection(getResources().getString(R.string.RESTAURANTS_PATH)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                if (task.isSuccessful()) {
//
//                    if (task.getResult() != null) {
//
//                        for (DocumentSnapshot documentSnapshot :
//                                task.getResult()) {
//
//                            restaurants.add(documentSnapshot.toObject(Restaurant.class));
////                            Log.i("TAG", documentSnapshot.toObject(Restaurant.class).getMenu().toString());
//
//                            keys.add(documentSnapshot.getId());
//                        }
//
//                        restaurantAdapter.notifyDataSetChanged();
//
//
//                    } else
//                        Toast.makeText(RestaurantsListActivity.this, "אין מסעדות כרגע להציג", Toast.LENGTH_SHORT).show();
//
//                } else if (task.isCanceled() && task.getException() != null){
//
//                    Toast.makeText(RestaurantsListActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//
//                }
//
//
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(RestaurantsListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

        volleyGet();

    }


    private void initializeClassificationRecyclerView() {

        restaurantRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        restaurantRecyclerView.setLayoutManager(layoutManager);
        restaurantAdapter = new RestaurantsAdapter(this, restaurants);
        restaurantRecyclerView.setAdapter(restaurantAdapter);

    }


    public void volleyGet() {
        String url = "http://10.0.2.2:8080/api/restaurants";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response != null) {

                    try {

                        for (int i = 0; i < response.length(); i++) {

                            JSONObject restaurantJsonObject = response.getJSONObject(i);

                            String restaurantId = restaurantJsonObject.getString("id");
                            String name = restaurantJsonObject.getString("name");
                            String restaurantUserName = restaurantJsonObject.getString("restaurantUserName");
                            String restaurantUserPassword = restaurantJsonObject.getString("restaurantUserPassword");

                            JSONObject myAddressJsonObject = restaurantJsonObject.getJSONObject("myAddress");

                            String cityName = myAddressJsonObject.getString("cityName");
                            String streetName = myAddressJsonObject.getString("streetName");
                            String floor = myAddressJsonObject.getString("floor");
                            String houseNumber = myAddressJsonObject.getString("houseNumber");
                            String entry = myAddressJsonObject.getString("entry");
                            String classificationAddress = myAddressJsonObject.getString("classificationAddress");
                            double longitude = myAddressJsonObject.getDouble("longitude");
                            double latitude = myAddressJsonObject.getDouble("latitude");
//                            boolean isChosen = myAddressJsonObject.getBoolean("isChosen");

                            MyAddress myAddress = new MyAddress(cityName, streetName, houseNumber, floor, entry, classificationAddress, longitude, latitude, true);

                            List<AreasForDelivery> areasForDeliveries = new ArrayList<>();
                            JSONArray areasForDeliveryJsonArray = restaurantJsonObject.getJSONArray("areasForDeliveries");

                            for (int j = 0; j < areasForDeliveryJsonArray.length(); j++) {
                                JSONObject areaForDeliveryJsonObject = areasForDeliveryJsonArray.getJSONObject(j);
                                String areaName = areaForDeliveryJsonObject.getString("areaName");
                                int deliveryCost = areaForDeliveryJsonObject.getInt("deliveryCost");
                                int minToDeliver = areaForDeliveryJsonObject.getInt("minToDeliver");
                                int timeOfDelivery = areaForDeliveryJsonObject.getInt("timeOfDelivery");

                                areasForDeliveries.add(new AreasForDelivery(areaName, deliveryCost, minToDeliver, timeOfDelivery));
                            }

                            String phoneNumber = restaurantJsonObject.getString("phoneNumber");

                            JSONArray openHourJsonArray = restaurantJsonObject.getJSONArray("openHour");
                            JSONArray closeHourJsonArray = restaurantJsonObject.getJSONArray("closeHour");

                            List<String> openHour = new ArrayList<>();
                            List<String> closeHour = new ArrayList<>();

                            for (int j = 0; j < openHourJsonArray.length(); j++) {
                                openHour.add(openHourJsonArray.getString(j));
                                closeHour.add(closeHourJsonArray.getString(j));
                            }

                            String dateOfAdd = restaurantJsonObject.getString("dateOfAdd");

                            List<RecommendationRestaurant> recommendationRestaurants = new ArrayList<>();
                            JSONArray recommendationRestaurantJsonArray = restaurantJsonObject.getJSONArray("recommendationRestaurants");
                            for (int j = 0; j < recommendationRestaurantJsonArray.length(); j++) {
                                JSONObject recommendationRestaurantJsonObject = recommendationRestaurantJsonArray.getJSONObject(j);
                                JSONObject recommendationUserJsonObject = recommendationRestaurantJsonObject.getJSONObject("user");
                                String userName = recommendationUserJsonObject.getString("name");
                                String email = recommendationUserJsonObject.getString("email");
                                String phoneNumberUser = recommendationUserJsonObject.getString("phoneNumber");
                                List<MyAddress> myAddresses = new ArrayList<>();
                                User user = new User();
                                user.setName(userName);
                                user.setEmail(email);
                                user.setPhoneNumber(phoneNumber);
                                user.setAddresses(myAddresses);

                                String date = recommendationRestaurantJsonObject.getString("date");
                                String creditLetter = recommendationRestaurantJsonObject.getString("creditLetter");
                                int creditStar = recommendationRestaurantJsonObject.getInt("creditStar");

                                recommendationRestaurants.add(new RecommendationRestaurant(user, date, creditLetter, creditStar));

                            }

                            boolean kosher = restaurantJsonObject.getBoolean("kosher");
                            boolean discount = restaurantJsonObject.getBoolean("discount");


                            JSONArray classificationRestaurantJsonArray = restaurantJsonObject.getJSONArray("classificationList");
                            List<Integer> classificationRestaurant = new ArrayList<>();
                            for (int j = 0; j < classificationRestaurantJsonArray.length(); j++) {
                                classificationRestaurant.add(classificationRestaurantJsonArray.getInt(j));
                            }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            Menu menu = new Menu();
                            List<Classification> classificationList = new ArrayList<>();
                            JSONObject menuObject = restaurantJsonObject.getJSONObject("menu");

                            JSONArray classificationJsonArray = menuObject.getJSONArray("classifications");

                            for (int j = 0; j < classificationJsonArray.length(); j++) {
                                Classification classification = new Classification();
                                List<Changes> classificationChangesList = new ArrayList<>();
                                List<Dish> dishList = new ArrayList<>();
                                JSONObject classificationJsonObject = classificationJsonArray.getJSONObject(j);
                                String classificationName = classificationJsonObject.getString("classificationName");
                                JSONArray changesClassificationJsonArray = classificationJsonObject.getJSONArray("changesList");
                                JSONArray dishJsonArray = classificationJsonObject.getJSONArray("dishList");


                                for (int k = 0; k < dishJsonArray.length(); k++) {
                                    Dish dish = new Dish();
                                    List<Changes> changesList = new ArrayList<>();
                                    JSONObject dishJsonObject = dishJsonArray.getJSONObject(k);
                                    JSONArray changesDishJsonArray = dishJsonObject.getJSONArray("changes");
                                    String dishName = dishJsonObject.getString("name");
                                    String dishDetail = dishJsonObject.getString("details");
                                    int price = dishJsonObject.getInt("price");


                                    for (int l = 0; l < changesDishJsonArray.length(); l++) {
                                        Changes changes = new Changes();
                                        JSONObject changeDishJsonObject = changesDishJsonArray.getJSONObject(l);
                                        JSONArray changesTypeListJsonArray = changeDishJsonObject.getJSONArray("changesByTypesList");
                                        changes.setChangeName(changeDishJsonObject.getString("changeName"));
                                        changes.setFreeSelection(changeDishJsonObject.getInt("freeSelection"));
                                        String enumString = changeDishJsonObject.getString("changesTypesEnum");
                                        Changes.ChangesTypesEnum changesTypesEnumSaved = Changes.ChangesTypesEnum.CHOICE_ALL;

                                        for (Changes.ChangesTypesEnum changesTypesEnum : Changes.ChangesTypesEnum.values()){
                                            if (changesTypesEnum.toString().equalsIgnoreCase(enumString)){
                                                changesTypesEnumSaved = changesTypesEnum;
                                            }
                                        }
                                        changes.setChangesTypesEnum(changesTypesEnumSaved);
                                        Log.i("CHANGES ENUM", changesTypesEnumSaved.toString());

                                        for (int m = 0; m < changesTypeListJsonArray.length(); m++) {
                                            JSONObject changeTypeJsonObject = new JSONObject();
                                            switch (changesTypesEnumSaved){
                                                case ONE_CHOICE:
                                                    changeTypeJsonObject = changesTypeListJsonArray.getJSONObject(m);
                                                    RegularChange regularChange = new RegularChange();
                                                    regularChange.setChange(changeTypeJsonObject.getString("change"));
                                                    regularChange.setPrice(changeTypeJsonObject.getInt("price"));
                                                    changes.getChangesByTypesList().add(regularChange);
                                                    break;

                                                case PIZZA:
                                                    changeTypeJsonObject = changesTypeListJsonArray.getJSONObject(m);
                                                    PizzaChange pizzaChange = new PizzaChange();
                                                    pizzaChange.setId(changeTypeJsonObject.getInt("id"));
                                                    pizzaChange.setName(changeTypeJsonObject.getString("name"));
                                                    pizzaChange.setCost(changeTypeJsonObject.getInt("cost"));
                                                    changes.getChangesByTypesList().add(pizzaChange);
                                                    break;

                                                case CLASSIFICATION_CHOICE:
                                                    changeTypeJsonObject = changesTypeListJsonArray.getJSONObject(m);
                                                    Dish dish1 = new Dish();
                                                    dish1.setName(changeTypeJsonObject.getString("name"));
                                                    dish1.setDetails(changeTypeJsonObject.getString("details"));
                                                    dish1.setChanges(new ArrayList<>());
                                                    changes.getChangesByTypesList().add(dish1);
                                                    break;
                                                case MULTIPLE:
                                                    changeTypeJsonObject = changesTypeListJsonArray.getJSONObject(m);
                                                    RegularChange regularChange1 = new RegularChange();
                                                    regularChange1.setChange(changeTypeJsonObject.getString("change"));
                                                    regularChange1.setPrice(changeTypeJsonObject.getInt("price"));
                                                    changes.getChangesByTypesList().add(regularChange1);
                                                    break;
                                                case VOLUME:
                                                    changeTypeJsonObject = changesTypeListJsonArray.getJSONObject(m);
                                                    RegularChange regularChange2 = new RegularChange();
                                                    regularChange2.setChange(changeTypeJsonObject.getString("change"));
                                                    regularChange2.setPrice(changeTypeJsonObject.getInt("price"));
                                                    changes.getChangesByTypesList().add(regularChange2);
                                                    break;
                                                case DISH_CHOICE:
                                                    changeTypeJsonObject = changesTypeListJsonArray.getJSONObject(m);
                                                    Dish dish2 = new Dish();
                                                    dish2.setName(changeTypeJsonObject.getString("name"));
                                                    dish2.setDetails(changeTypeJsonObject.getString("details"));
                                                    dish2.setChanges(new ArrayList<>());
                                                    changes.getChangesByTypesList().add(dish2);
                                                    break;
                                            }
                                        }
                                        changesList.add(changes);
                                    }
                                    dish.setChanges(changesList);
                                    dish.setName(dishName);
                                    dish.setDetails(dishDetail);
                                    dish.setPrice(price);
                                    dishList.add(dish);
                                }


                                for (int k = 0; k < changesClassificationJsonArray.length(); k++) {
                                    Changes changes = new Changes();
                                    JSONObject changeDishJsonObject = changesClassificationJsonArray.getJSONObject(k);
                                    JSONArray changesTypeListJsonArray = changeDishJsonObject.getJSONArray("changesByTypesList");
                                    changes.setChangeName(changeDishJsonObject.getString("changeName"));
                                    changes.setFreeSelection(changeDishJsonObject.getInt("freeSelection"));
                                    String enumString = changeDishJsonObject.getString("changesTypesEnum");
                                    Changes.ChangesTypesEnum changesTypesEnumSaved = Changes.ChangesTypesEnum.CHOICE_ALL;

                                    for (Changes.ChangesTypesEnum changesTypesEnum : Changes.ChangesTypesEnum.values()){
                                        if (changesTypesEnum.toString().equalsIgnoreCase(enumString)){
                                            changesTypesEnumSaved = changesTypesEnum;
                                            Log.i("CHANGES ENUM", changesTypesEnumSaved.toString());
                                            break;
                                        }
                                    }
                                    changes.setChangesTypesEnum(changesTypesEnumSaved);

                                    for (int m = 0; m < changesTypeListJsonArray.length(); m++) {
                                        JSONObject changeTypeJsonObject = new JSONObject();
                                        switch (changesTypesEnumSaved){
                                            case ONE_CHOICE:
                                                changeTypeJsonObject = changesTypeListJsonArray.getJSONObject(m);
                                                RegularChange regularChange = new RegularChange();
                                                regularChange.setChange(changeTypeJsonObject.getString("change"));
                                                regularChange.setPrice(changeTypeJsonObject.getInt("price"));
                                                changes.getChangesByTypesList().add(regularChange);
                                                break;

                                            case PIZZA:
                                                changeTypeJsonObject = changesTypeListJsonArray.getJSONObject(m);
                                                PizzaChange pizzaChange = new PizzaChange();
                                                pizzaChange.setId(changeTypeJsonObject.getInt("id"));
                                                pizzaChange.setName(changeTypeJsonObject.getString("name"));
                                                pizzaChange.setCost(changeTypeJsonObject.getInt("cost"));
                                                changes.getChangesByTypesList().add(pizzaChange);
                                                break;

                                            case CLASSIFICATION_CHOICE:
                                                changeTypeJsonObject = changesTypeListJsonArray.getJSONObject(m);
                                                Dish dish = new Dish();
                                                dish.setName(changeTypeJsonObject.getString("name"));
                                                dish.setDetails(changeTypeJsonObject.getString("details"));
                                                dish.setChanges(new ArrayList<>());
                                                changes.getChangesByTypesList().add(dish);
                                                break;
                                            case MULTIPLE:
                                                changeTypeJsonObject = changesTypeListJsonArray.getJSONObject(m);
                                                RegularChange regularChange1 = new RegularChange();
                                                regularChange1.setChange(changeTypeJsonObject.getString("change"));
                                                regularChange1.setPrice(changeTypeJsonObject.getInt("price"));
                                                changes.getChangesByTypesList().add(regularChange1);
                                                break;
                                            case VOLUME:
                                                changeTypeJsonObject = changesTypeListJsonArray.getJSONObject(m);
                                                RegularChange regularChange2 = new RegularChange();
                                                regularChange2.setChange(changeTypeJsonObject.getString("change"));
                                                regularChange2.setPrice(changeTypeJsonObject.getInt("price"));
                                                changes.getChangesByTypesList().add(regularChange2);
                                                break;
                                            case DISH_CHOICE:
                                                changeTypeJsonObject = changesTypeListJsonArray.getJSONObject(m);
                                                Dish dish1 = new Dish();
                                                dish1.setName(changeTypeJsonObject.getString("name"));
                                                dish1.setDetails(changeTypeJsonObject.getString("details"));
                                                dish1.setChanges(new ArrayList<>());
                                                changes.getChangesByTypesList().add(dish1);
                                                break;
                                        }
                                    }

                                    classificationChangesList.add(changes);
                                }
                                classification.setChangesList(classificationChangesList);
                                classification.setClassificationName(classificationName);
                                classification.setDishList(dishList);
                                classificationList.add(classification);
                            }

                            menu.setClassifications(classificationList);
                            Log.i("GET MENU", menu.toString());


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            Restaurant restaurant = new Restaurant(name, myAddress, areasForDeliveries, phoneNumber, openHour, closeHour, dateOfAdd, null, null, kosher, discount, classificationRestaurant, menu, restaurantUserName, restaurantUserPassword);
                            restaurant.setId(restaurantId);

                            restaurants.add(restaurant);
                        }

                        restaurantAdapter.notifyDataSetChanged();
                        Log.i("RESTAURANT ADD", response.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ERROR", error.getMessage());
            }
        });

        requestQueue.add(jsonArrayRequest);
    }


}