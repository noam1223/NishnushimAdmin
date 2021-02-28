package com.example.nishnushimadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nishnushimadmin.adapters.AreaForDeliveryAdapter;
import com.example.nishnushimadmin.helpClasses.AreasForDelivery;
import com.example.nishnushimadmin.helpClasses.Classification;
import com.example.nishnushimadmin.helpClasses.Dish;
import com.example.nishnushimadmin.helpClasses.Menu;
import com.example.nishnushimadmin.helpClasses.MyAddress;
import com.example.nishnushimadmin.helpClasses.RecommendationRestaurant;
import com.example.nishnushimadmin.helpClasses.Restaurant;
import com.example.nishnushimadmin.helpClasses.menuChanges.Changes;
import com.example.nishnushimadmin.helpClasses.menuChanges.PizzaChange;
import com.example.nishnushimadmin.helpClasses.menuChanges.RegularChange;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AddRestaurantActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RESULT_LOGO = 9876;
    private static final int RESULT_PROFILE = 6789;


    EditText  streetEditText, numberStreetEditText, phoneNumberEditText, restaurantUserNameEditText, restaurantNameEditText;
    AutoCompleteTextView cityEditText;
    Button addRestaurantBtn, hoursForRestaurantBtn, watchAreasForDeliveryBtn, setAreasForDeliveryBtn, addClassificationBtn, removeClassificationBtn;
    ImageView logoImageView, profileImageView;
    RadioGroup kosherRadioGroup, discountRadioGroup;
    Spinner spinnerClassification, spinnerClassificationAdded;
    ArrayAdapter<String> addedClassificationAdapter, classificationAdapter, citiesAdapter;


    List<String> openHoursList = new ArrayList<>();
    List<String> closeHoursList = new ArrayList<>();
    List<Integer> classificationHandleList = new ArrayList<>();
    List<String> classificationHandleListStrings = new ArrayList<>();
    List<AreasForDelivery> areasForDeliveries = new ArrayList<>();
    String isKosherString = "", discountString = "";
    Uri logoImageUri, profileImageUri;
    boolean fromOrTo = false; // false - from , true - to

    String[] classificationArray = {"נשנושים", "פיצריות", "המבורגרים", "תאילנדי", "בשרים", "דגים/פירות ים", "מרקים", "סנדוויצ׳ים", "קינוחים", "חומוס", "כשר", "אסייתי", "איטלקי", "מקסיקני", "בתי קפה", "טבעוני", "טבעוני", "מאפים", "גלידריות"};
    int[] classificationAddedArray = {};
    String[] spinnerDeliveryTimeArray = {"עד", "בין"};

    int classificationPosition = -1;
    int classificationAddedPosition = -1;

    boolean logoOrProfile = false;

    FirebaseFirestore db;
    private String[] citiesStrings;

    Restaurant restaurant;


    private void initilaizeRestaurantProfile() {

        restaurantNameEditText.setText(restaurant.getName());
        restaurantUserNameEditText.setText(restaurant.getRestaurantUserName());
        cityEditText.setText(restaurant.getMyAddress().getCityName());
        streetEditText.setText(restaurant.getMyAddress().getStreetName());
        numberStreetEditText.setText(restaurant.getMyAddress().getHouseNumber());
        openHoursList.addAll(restaurant.getOpenHour());
        closeHoursList.addAll(restaurant.getCloseHour());
        classificationHandleList.addAll(restaurant.getClassificationList());
        phoneNumberEditText.setText(restaurant.getPhoneNumber());
        areasForDeliveries.addAll(restaurant.getAreasForDelivery());

        if (restaurant.isKosher()){
            kosherRadioGroup.getChildAt(0).performClick();
        }else kosherRadioGroup.getChildAt(1).performClick();

        if (restaurant.isDiscount()){
            discountRadioGroup.getChildAt(0).performClick();
        }else discountRadioGroup.getChildAt(1).performClick();





    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        restaurantNameEditText = findViewById(R.id.restaurant_name_edit_text_add_restaurant_activity);
        cityEditText = findViewById(R.id.city_restaurant_name_edit_text_add_restaurant_activity);
        streetEditText = findViewById(R.id.street_restaurant_name_edit_text_add_restaurant_activity);
        numberStreetEditText = findViewById(R.id.number_of_street_restaurant_name_edit_text_add_restaurant_activity);
        restaurantUserNameEditText = findViewById(R.id.user_restaurant_name_edit_text_add_restaurant_activity);

        phoneNumberEditText = findViewById(R.id.phone_number_restaurant_add_restaurant_activity);

        logoImageView = findViewById(R.id.logo_image_view_add_restaurant_activity);
        profileImageView = findViewById(R.id.profile_image_view_add_restaurant_activity);



        spinnerClassificationAdded = findViewById(R.id.remove_classification_spinner_add_restaurant_activity);
        addedClassificationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, classificationHandleListStrings);
        spinnerClassificationAdded.setAdapter(addedClassificationAdapter);
        spinnerClassificationAdded.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                classificationAddedPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                classificationAddedPosition = -1;
            }
        });




        spinnerClassification = findViewById(R.id.classification_spinner_add_restaurant_activity);
        classificationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, classificationArray);
        classificationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClassification.setAdapter(classificationAdapter);

        spinnerClassification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                classificationPosition = position;
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                classificationPosition = -1;
            }
        });




        kosherRadioGroup = findViewById(R.id.radio_group_kosher_add_restaurant_activity);
        kosherRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (kosherRadioGroup.getCheckedRadioButtonId() != -1) {

                    RadioButton radioButton = findViewById(kosherRadioGroup.getCheckedRadioButtonId());
                    isKosherString = radioButton.getText().toString();
                    Toast.makeText(AddRestaurantActivity.this, radioButton.getText().toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });


        discountRadioGroup = findViewById(R.id.radio_group_discount_price_add_restaurant_activity);
        discountRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (discountRadioGroup.getCheckedRadioButtonId() != -1) {

                    RadioButton radioButton = findViewById(discountRadioGroup.getCheckedRadioButtonId());
                    discountString = radioButton.getText().toString();
                    Toast.makeText(AddRestaurantActivity.this, radioButton.getText().toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });


        addRestaurantBtn = findViewById(R.id.add_restaurant_btn_add_restaurant_activity);
        hoursForRestaurantBtn = findViewById(R.id.open_set_hours_dialog_add_restaurant_activity);
        watchAreasForDeliveryBtn = findViewById(R.id.watch_area_for_delivery_list_add_restaurant_activity);
        setAreasForDeliveryBtn = findViewById(R.id.add_area_for_delivery_add_restaurant_activity);
        addClassificationBtn = findViewById(R.id.add_chosen_classification_btn_add_restaurant_activtiy);
        removeClassificationBtn = findViewById(R.id.remove_chosen_classification_btn_add_restaurant_activtiy);


        addRestaurantBtn.setOnClickListener(this);
        hoursForRestaurantBtn.setOnClickListener(this);
        watchAreasForDeliveryBtn.setOnClickListener(this);
        setAreasForDeliveryBtn.setOnClickListener(this);
        addClassificationBtn.setOnClickListener(this);
        removeClassificationBtn.setOnClickListener(this);


        db = FirebaseFirestore.getInstance();

        readFromFile(this);



        Intent intent = getIntent();
        if (intent.getSerializableExtra("restaurant") != null){
            restaurant = (Restaurant) intent.getSerializableExtra("restaurant");
            initilaizeRestaurantProfile();
        }







    }










    @Override
    public void onClick(View v) {

        int id = v.getId();


        if (id == R.id.open_set_hours_dialog_add_restaurant_activity) {


            View popUpView = getLayoutInflater().inflate(R.layout.open_hours_dialog_pop_up_window, null);
            Dialog hoursDialog = new Dialog(this);
            hoursDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            hoursDialog.setContentView(popUpView);


            Button finishFullFillPopUpBtn = popUpView.findViewById(R.id.finish_btn_open_hours_dialog_pop_up_window);
            EditText[] rishonEditTextArray = {(EditText) popUpView.findViewById(R.id.rishon_hour_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.rishon_minute_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.rishon_hour_of_close_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.rishon_minute_of_close_hour_edit_text_open_hour_dialog)};
            EditText[] sheniEditTextArray = {(EditText) popUpView.findViewById(R.id.sheni_hour_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.sheni_minute_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.sheni_hour_of_close_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.sheni_minute_of_close_hour_edit_text_open_hour_dialog)};
            EditText[] shlishiEditTextArray = {(EditText) popUpView.findViewById(R.id.shlishi_hour_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.shlishi_minute_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.shlishi_hour_of_close_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.shlishi_minute_of_close_hour_edit_text_open_hour_dialog)};
            EditText[] reveiEditTextArray = {(EditText) popUpView.findViewById(R.id.revei_hour_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.revei_minute_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.revei_hour_of_close_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.revei_minute_of_close_hour_edit_text_open_hour_dialog)};
            EditText[] hamishiEditTextArray = {(EditText) popUpView.findViewById(R.id.hamishi_hour_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.hamishi_minute_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.hamishi_hour_of_close_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.hamishi_minute_of_close_hour_edit_text_open_hour_dialog)};
            EditText[] shishiEditTextArray = {(EditText) popUpView.findViewById(R.id.shishi_hour_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.shishi_minute_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.shishi_hour_of_close_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.shishi_minute_of_close_hour_edit_text_open_hour_dialog)};
            EditText[] shabatEditTextArray = {(EditText) popUpView.findViewById(R.id.shabat_hour_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.shabat_minute_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.shabat_hour_of_close_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.shabat_minute_of_close_hour_edit_text_open_hour_dialog)};


            List<EditText[]> editTexts = new ArrayList<>();
            editTexts.add(rishonEditTextArray);
            editTexts.add(sheniEditTextArray);
            editTexts.add(shlishiEditTextArray);
            editTexts.add(reveiEditTextArray);
            editTexts.add(hamishiEditTextArray);
            editTexts.add(shishiEditTextArray);
            editTexts.add(shabatEditTextArray);


            if (!openHoursList.isEmpty() && !closeHoursList.isEmpty()){
                for (int i = 0; i < editTexts.size(); i++) {

                    String[] openHours = openHoursList.get(i).split(":");

                    editTexts.get(i)[0].setText(openHours[0]);
                    editTexts.get(i)[1].setText(openHours[1]);

                    String[] closeHours = closeHoursList.get(i).split(":");

                    editTexts.get(i)[2].setText(closeHours[0]);
                    editTexts.get(i)[3].setText(closeHours[1]);
                }
            }


            finishFullFillPopUpBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openHoursList.clear();
                    closeHoursList.clear();

                    for (int i = 0; i < editTexts.size(); i++) {

                        if (!editTexts.get(i)[0].getText().toString().isEmpty() && !editTexts.get(i)[1].getText().toString().isEmpty()
                                && !editTexts.get(i)[2].getText().toString().isEmpty() && !editTexts.get(i)[3].getText().toString().isEmpty()) {

                            openHoursList.add(editTexts.get(i)[0].getText().toString() + ":" + editTexts.get(i)[1].getText().toString());
                            closeHoursList.add(editTexts.get(i)[2].getText().toString() + ":" + editTexts.get(i)[3].getText().toString());
                        }
                    }

                    Log.i("OPEN HOUR", openHoursList.size() + "");
                    Log.i("OPEN HOUR", closeHoursList.size() + "");

                    hoursDialog.dismiss();
                }
            });


            hoursDialog.create();
            hoursDialog.show();

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(hoursDialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            hoursDialog.getWindow().setAttributes(lp);


        } else if (id == R.id.watch_area_for_delivery_list_add_restaurant_activity) {

            if (!areasForDeliveries.isEmpty()) {

                View popUpView = getLayoutInflater().inflate(R.layout.list_of_area_for_delivery_pop_up_window, null);
                Dialog areaToDeliverListDialog = new Dialog(this);
                areaToDeliverListDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                areaToDeliverListDialog.setContentView(popUpView);

                ListView areasForDeliveryListView = popUpView.findViewById(R.id.list_view_area_for_delivery_list_of_area_pop_up_window);
                AreaForDeliveryAdapter areaForDeliveryAdapter = new AreaForDeliveryAdapter(this, areasForDeliveries);
                areasForDeliveryListView.setAdapter(areaForDeliveryAdapter);
                Button exitForAreasToDeliveryDialog = popUpView.findViewById(R.id.exit_list_of_area_for_delivery_pop_up_window);

                exitForAreasToDeliveryDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        areaToDeliverListDialog.dismiss();

                    }
                });

                areaToDeliverListDialog.create();
                areaToDeliverListDialog.show();

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(areaToDeliverListDialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                areaToDeliverListDialog.getWindow().setAttributes(lp);

            } else Toast.makeText(this, "רשימת אזורי החלוקה הינה ריקה", Toast.LENGTH_SHORT).show();



        } else if (id == R.id.add_area_for_delivery_add_restaurant_activity) {


            View popUpView = getLayoutInflater().inflate(R.layout.add_area_for_delivery_pop_up_window, null);
            Dialog areaDialog = new Dialog(this);
            areaDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            areaDialog.setContentView(popUpView);

            Spinner deliveryTimeSpinner = popUpView.findViewById(R.id.delivery_classification_spinner_add_area_pop_up_window);


            AutoCompleteTextView areaNameEditText = popUpView.findViewById(R.id.area_name_edit_text_add_area_pop_up_window);
            areaNameEditText.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, citiesStrings));
            EditText deliveryCostEditText = popUpView.findViewById(R.id.delivery_cost_edit_text_add_area_pop_up_window);
            EditText minToDeliverEditText = popUpView.findViewById(R.id.min_to_deliver_edit_text_add_area_pop_up_window);
            EditText timeOfDeliveryEditText = popUpView.findViewById(R.id.time_of_delivery_edit_text_add_area_pop_up_window);
            EditText toTimeOfDeliveryEditText = popUpView.findViewById(R.id.to_time_of_delivery_edit_text_add_area_pop_up_window);

            Button addAreaForDeliver = popUpView.findViewById(R.id.add_area_for_delivery_btn_add_area_pop_up_window);

            addAreaForDeliver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String areaName = areaNameEditText.getText().toString();
                    String deliveryCost = deliveryCostEditText.getText().toString();
                    String minToDeliver = minToDeliverEditText.getText().toString();
                    String timeOfDelivery;



                    List<String> cities = Arrays.asList(citiesStrings);
                    if (cities.size() == 0 || !cities.contains(areaName)){
                        Toast.makeText(getApplicationContext(), "בחר עיר מתוך רשימת הערים", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    if (toTimeOfDeliveryEditText.getVisibility() == View.GONE){

                        timeOfDelivery = timeOfDeliveryEditText.getText().toString();

                    }else {

                        timeOfDelivery = timeOfDeliveryEditText.getText().toString() + " - " + toTimeOfDeliveryEditText.getText().toString();

                    }

                    if (deliveryCost.isEmpty()){
                        deliveryCost = "0";
                    }


                    if (!areaName.isEmpty() && !minToDeliver.isEmpty() && !timeOfDelivery.isEmpty()) {
                        areasForDeliveries.add(new AreasForDelivery(areaName, Integer.parseInt(deliveryCost), Integer.parseInt(minToDeliver), Integer.parseInt(timeOfDelivery)));
                        areaDialog.dismiss();
                    }
                }
            });


            ArrayAdapter<String> deliveryClassificationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerDeliveryTimeArray);
            deliveryTimeSpinner.setAdapter(deliveryClassificationAdapter);

            deliveryTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0){

                        toTimeOfDeliveryEditText.setVisibility(View.GONE);

                    }else if (position == 1){

                        toTimeOfDeliveryEditText.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            areaDialog.create();
            areaDialog.show();

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(areaDialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            areaDialog.getWindow().setAttributes(lp);


        } else if (id == R.id.add_restaurant_btn_add_restaurant_activity) {


            boolean isKosher = false;
            boolean isDiscount = false;
            String name = restaurantNameEditText.getText().toString();
            String phone = phoneNumberEditText.getText().toString();

            String city = cityEditText.getText().toString();
            String street = streetEditText.getText().toString();
            String streetNumber = numberStreetEditText.getText().toString();
            String userName = restaurantUserNameEditText.getText().toString();
            String dateOfAddRestaurant = new SimpleDateFormat("dd/MM/yyyy").format(new Date());


            List<String> cities = Arrays.asList(citiesStrings);
            if (cities.size() == 0 || !cities.contains(city)){
                Toast.makeText(this, "בחר עיר מתוך רשימת הערים", Toast.LENGTH_SHORT).show();
                return;
            }


            if (!name.isEmpty() && !city.isEmpty() && !street.isEmpty()
                    && !streetNumber.isEmpty() && !phone.isEmpty() && !userName.isEmpty()) {


                if (openHoursList.size() == 7 && closeHoursList.size() == 7) {


                    if (!areasForDeliveries.isEmpty() && !isKosherString.isEmpty() && !discountString.isEmpty() && !classificationHandleList.isEmpty()) {

                        if (isKosherString.equals("כשר"))
                            isKosher = true;

                        if (discountString.equals("תומך בהנחה"))
                            isDiscount = true;

                        if (profileImageUri != null){
                            //TODO: UPLOAD IMAGE TO DATABASE OF RESTAURANT
                        }



                        if (logoImageUri != null){
                            //TODO: UPLOAD IMAGE TO DATABASE OF RESTAURANT
                        }


                        MyAddress myAddress = new MyAddress(city, street, streetNumber);

                        Geocoder geocoder = new Geocoder(getApplicationContext());
                        String address = "ישראל, " + myAddress.getCityName() + "," +
                                myAddress.getStreetName() + " " + myAddress.getHouseNumber();

                        try {
                            List<Address> addresses = geocoder.getFromLocationName(address, 1);

                            if (addresses.size() > 0) {


                                myAddress.setLatitude(addresses.get(0).getLatitude());
                                myAddress.setLongitude(addresses.get(0).getLongitude());




                                if (restaurant == null) {
                                    Log.i("RESTAURANT NULL", "VOLLEY POST");
                                    restaurant = new Restaurant(name, myAddress, areasForDeliveries, phone, openHoursList, closeHoursList, dateOfAddRestaurant, logoImageUri, profileImageUri, isKosher, isDiscount, classificationHandleList, new Menu(), userName, "123456");
                                    volleyPost(restaurant);
                                }else {
                                    Log.i("RESTAURANT NULL", "VOLLEY PUT");
                                    restaurant = new Restaurant(restaurant.getId(),name,restaurant.getRestaurantUserName(), restaurant.getRestaurantUserPassword(), myAddress, areasForDeliveries, phone, openHoursList, closeHoursList, restaurant.getDateOfAdd(), logoImageUri, profileImageUri,restaurant.getRecommendationRestaurants(), isKosher, isDiscount, classificationHandleList, restaurant.getMenu(), restaurant.getRecommendationAvg(), restaurant.getCreditAmount());
                                    volleyPut(restaurant);
                                }
//                                db.collection(getResources().getString(R.string.RESTAURANTS_PATH)).add(restaurant);
                                Toast.makeText(this, "ADDED", Toast.LENGTH_SHORT).show();
                                finish();

                                //SIGN IN RESTAURANT INTO FIREBASE AND SAVEING ITS DETAILS
//                                FirebaseAuth.getInstance().createUserWithEmailAndPassword(RestaurantSingleton.userFormatted(restaurant.getRestaurantUserName(), getApplicationContext()), restaurant.getRestaurantUserPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<AuthResult> task) {
//                                        if (task.isSuccessful()){
//                                            Toast.makeText(AddRestaurantActivity.this, "המסעדה נוספה בהצלחה!", Toast.LENGTH_SHORT).show();
////                                            RestaurantLogin restaurantLogin = new RestaurantLogin(restaurant.getRestaurantUserName(), restaurant.getRestaurantUserPassword(), task.getResult().getUser().getUid());
//                                            //db.collection("RestaurantsUser").document(restaurant.getRestaurantUserName()).set(restaurantLogin);
//                                            db.collection(getResources().getString(R.string.RESTAURANTS_PATH)).document(task.getResult().getUser().getUid()).set(restaurant);
//                                            finish();
//                                        }else if (task.getException() != null){
//                                            Toast.makeText(AddRestaurantActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Toast.makeText(AddRestaurantActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                                    }
//                                });


                            } else
                                Toast.makeText(this, "לא נמצאה כתובת זו אנא נסה שוב", Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                            myAddress.setLatitude(0);
                            myAddress.setLongitude(0);
                            Restaurant restaurant = new Restaurant(name, myAddress, areasForDeliveries, phone, openHoursList, closeHoursList, dateOfAddRestaurant, logoImageUri, profileImageUri, isKosher, isDiscount, classificationHandleList, new Menu(), userName, "123456");
                            volleyPost(restaurant);
                            Toast.makeText(this, "ADDED", Toast.LENGTH_SHORT).show();
                            finish();
                            Toast.makeText(this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else
                        Toast.makeText(this, "אנא הוסף איזורי שליחה או/ו 2 תמונות למסעדה", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, "חובה להכניס 7 שעות פתיחה ושעות סגירה", Toast.LENGTH_SHORT).show();

            } else
                Toast.makeText(this, "יש צורך למלא את הפרטים שבתבניות", Toast.LENGTH_SHORT).show();



        } else if (id == R.id.add_chosen_classification_btn_add_restaurant_activtiy){

            if (classificationPosition != -1){
                classificationHandleList.add(classificationPosition);
                classificationHandleListStrings.add(classificationArray[classificationPosition]);
                classificationPosition = -1;
            }

            addedClassificationAdapter.notifyDataSetChanged();
            classificationAdapter.notifyDataSetChanged();


        }else if (id == R.id.remove_chosen_classification_btn_add_restaurant_activtiy){

            if (classificationAddedPosition != -1){
                classificationHandleList.remove(Integer.valueOf(classificationAddedPosition));
                classificationHandleListStrings.remove(classificationAddedPosition);

                classificationAddedPosition = -1;
            }

            addedClassificationAdapter.notifyDataSetChanged();
            classificationAdapter.notifyDataSetChanged();







           ///WORKING ON TAKING PICTURES FROM GALLERY TO LOGO IMAGE AND PROFILE IMAGE
        } else if (id == R.id.logo_image_view_add_restaurant_activity){


            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, RESULT_LOGO);


        }else if (id == R.id.profile_image_view_add_restaurant_activity){

            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, RESULT_PROFILE);

        }
    }




    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {

            InputStream imageStream;
            Bitmap selectedImage;

            if (reqCode == RESULT_LOGO){

                try {
                    logoImageUri = data.getData();
                    imageStream = getContentResolver().openInputStream(logoImageUri);
                    selectedImage = BitmapFactory.decodeStream(imageStream);
                    logoImageView.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "משהו השתבש נסה שנית", Toast.LENGTH_LONG).show();
                }

            }else if (reqCode == RESULT_PROFILE){

                try {
                    profileImageUri = data.getData();
                    imageStream = getContentResolver().openInputStream(profileImageUri);
                    selectedImage = BitmapFactory.decodeStream(imageStream);
                    profileImageView.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "משהו השתבש נסה שנית", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(this, "לא נבחרה תמונה",Toast.LENGTH_LONG).show();
            }


            }
    }



    private void readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.getAssets().open("cities_copy.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {

                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }

            citiesStrings = ret.split(",");

            cityEditText.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, citiesStrings));
            cityEditText.setThreshold(1);

        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
    }























































    public void volleyGet(){
        String url = "http://localhost:8080/api/restaurants";
        List<Restaurant> jsonObjectRequest = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null){
                    Log.i("Restaurant", response.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }



    public void volleyPut(Restaurant restaurant){

        String postUrl = "http://10.0.2.2:8080/api/restaurants/" + restaurant.getId();
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = getRestaurantJsonObject(restaurant);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("RESTAURANT PUT", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("RESTAURANT ERROR PUT", error.getMessage());
            }
        });

        requestQueue.add(jsonObjectRequest);
    }





    public void volleyPost(Restaurant restaurant) {
        String postUrl = "http://10.0.2.2:8080/api/restaurants";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = getRestaurantJsonObject(restaurant);
        Log.i("RESTAURANT POST", postData.toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("RESTAURANT ADD", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    Log.i("RESTAURANT ERROR ADD", error.getMessage());
            }
        });

     requestQueue.add(jsonObjectRequest);

    }




    private JSONObject getRestaurantJsonObject(Restaurant restaurant) {
        JSONObject postData = new JSONObject();

        try {
            postData.put("id", restaurant.getId());
            postData.put("name", restaurant.getName());
            postData.put("restaurantUserName", restaurant.getRestaurantUserName());
            postData.put("restaurantUserPassword", restaurant.getRestaurantUserPassword());

            JSONObject myAddressPostData = new JSONObject();
            myAddressPostData.put("cityName", restaurant.getMyAddress().getCityName());
            myAddressPostData.put("streetName", restaurant.getMyAddress().getStreetName());
            myAddressPostData.put("houseNumber", restaurant.getMyAddress().getHouseNumber());
            myAddressPostData.put("floor", restaurant.getMyAddress().getFloor());
            myAddressPostData.put("entry", restaurant.getMyAddress().getEntry());
            myAddressPostData.put("classificationAddress", restaurant.getMyAddress().getClassificationAddress());
            myAddressPostData.put("longitude", restaurant.getMyAddress().getLongitude());
            myAddressPostData.put("latitude", restaurant.getMyAddress().getLatitude());
            myAddressPostData.put("isChosen", restaurant.getMyAddress().isChosen());

            postData.put("myAddress", myAddressPostData);

            JSONArray jsonAreasForDeliveries = new JSONArray();
            for (AreasForDelivery areasForDelivery : restaurant.getAreasForDelivery()){
                JSONObject areasForDeliveryPostData = new JSONObject();
                areasForDeliveryPostData.put("areaName", areasForDelivery.getAreaName());
                areasForDeliveryPostData.put("deliveryCost", areasForDelivery.getDeliveryCost());
                areasForDeliveryPostData.put("minToDeliver", areasForDelivery.getMinToDeliver());
                areasForDeliveryPostData.put("timeOfDelivery", areasForDelivery.getTimeOfDelivery());
                jsonAreasForDeliveries.put(areasForDeliveryPostData);
            }
            postData.put("areasForDeliveries", jsonAreasForDeliveries);

            postData.put("phoneNumber", restaurant.getPhoneNumber());


            JSONArray jsonArrayOpenHour = new JSONArray();
            JSONArray jsonArrayCloseHour = new JSONArray();
            for (int i = 0; i < restaurant.getOpenHour().size(); i++) {

                jsonArrayOpenHour.put(restaurant.getOpenHour().get(i));
                jsonArrayCloseHour.put(restaurant.getCloseHour().get(i));

            }

            postData.put("openHour", jsonArrayOpenHour);
            postData.put("closeHour", jsonArrayCloseHour);
            postData.put("dateOfAdd", restaurant.getDateOfAdd());


            JSONArray jsonObjectsRecommendations = new JSONArray();
            for (RecommendationRestaurant recommendationRestaurant : restaurant.getRecommendationRestaurants()){
                JSONObject jsonObjectsRecommendation = new JSONObject();
                JSONObject jsonObjectsUser = new JSONObject();
                jsonObjectsUser.put("name", recommendationRestaurant.getUser().getName());
                jsonObjectsUser.put("email", "email");
                jsonObjectsUser.put("phoneNumber", recommendationRestaurant.getUser().getPhoneNumber());
                jsonObjectsUser.put("myAddresses", new ArrayList<>());
                jsonObjectsRecommendation.put("user", jsonObjectsUser);
                jsonObjectsRecommendation.put("date", recommendationRestaurant.getDate());
                jsonObjectsRecommendation.put("creditLetter", recommendationRestaurant.getCreditLetter());
                jsonObjectsRecommendation.put("creditStar", recommendationRestaurant.getCreditStar());
                jsonObjectsRecommendations.put(jsonObjectsRecommendation);
            }


            postData.put("recommendationRestaurant", jsonObjectsRecommendations);
            postData.put("kosher", restaurant.isKosher());
            postData.put("discount", restaurant.isDiscount());


            JSONArray jsonClassificationList = new JSONArray();
            for (int i = 0; i < restaurant.getClassificationList().size(); i++) {

                jsonClassificationList.put(restaurant.getClassificationList().get(i));

            }
            postData.put("classificationList", jsonClassificationList);


            JSONObject menuPostData = getMenuJsonObject(restaurant.getMenu());

            postData.put("menu", menuPostData);
            postData.put("creditAmount", restaurant.getCreditAmount());

        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        return postData;
    }


    private JSONObject getMenuJsonObject(Menu menu) {
        JSONObject menuJsonObject = new JSONObject();

        try {

            JSONArray classificationJsonArray = new JSONArray();

            for (Classification classification : menu.getClassifications()) {

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
