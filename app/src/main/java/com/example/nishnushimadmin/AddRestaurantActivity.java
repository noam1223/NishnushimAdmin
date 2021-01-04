package com.example.nishnushimadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nishnushimadmin.adapters.AreaForDeliveryAdapter;
import com.example.nishnushimadmin.helpClasses.AreasForDelivery;
import com.example.nishnushimadmin.helpClasses.Menu;
import com.example.nishnushimadmin.helpClasses.MyAddress;
import com.example.nishnushimadmin.helpClasses.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddRestaurantActivity extends AppCompatActivity implements View.OnClickListener {

    EditText restaurantNameEditText, cityEditText, streetEditText, numberStreetEditText, deliveryCostEditText, timeDeliveryEditText, phoneNumberEditText;
    Button addRestaurantBtn, hoursForRestaurantBtn, watchAreasForDeliveryBtn, setAreasForDeliveryBtn, addClassificationBtn;
    ImageView logoImageView, profileImageView;
    RadioGroup kosherRadioGroup, discountRadioGroup;
    Spinner spinnerClassification;


    List<String> openHoursList = new ArrayList<>();
    List<String> closeHoursList = new ArrayList<>();
    List<Integer> classificationHandleList = new ArrayList<>();
    List<AreasForDelivery> areasForDeliveries = new ArrayList<>();
    String isKosherString = "", discountString = "";
    Uri logoImageUri, profileImageUri;
    boolean fromOrTo = false; // false - from , true - to

    String[] classificationArray = {"פיצה", "המבורגר", "בשר", "סושי", "אסיאתי", "סלט", "קינוח", "איטלקי", "חומוס", "סנדוויץ", "מקסיקני", "ג׳חנון/בורקסים", "דגים", "כשר", "ים תיכוני", "ארוחות בוקר", "פירות ים", "מרק", "יפני", "נודלס"};

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);


        restaurantNameEditText = findViewById(R.id.restaurant_name_edit_text_add_restaurant_activity);
        cityEditText = findViewById(R.id.city_restaurant_name_edit_text_add_restaurant_activity);
        streetEditText = findViewById(R.id.street_restaurant_name_edit_text_add_restaurant_activity);
        numberStreetEditText = findViewById(R.id.number_of_street_restaurant_name_edit_text_add_restaurant_activity);
        deliveryCostEditText = findViewById(R.id.cost_of_delivery_add_restaurant_activity);
        timeDeliveryEditText = findViewById(R.id.time_of_delivery_add_restaurant_activity);
        phoneNumberEditText = findViewById(R.id.phone_number_restaurant_add_restaurant_activity);

        logoImageView = findViewById(R.id.logo_image_view_add_restaurant_activity);
        profileImageView = findViewById(R.id.profile_image_view_add_restaurant_activity);

        spinnerClassification = findViewById(R.id.classification_spinner_add_restaurant_activity);
        ArrayAdapter<String> classificationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, classificationArray);
        classificationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClassification.setAdapter(classificationAdapter);

        spinnerClassification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                classificationHandleList.add(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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


        addRestaurantBtn.setOnClickListener(this);
        hoursForRestaurantBtn.setOnClickListener(this);
        watchAreasForDeliveryBtn.setOnClickListener(this);
        setAreasForDeliveryBtn.setOnClickListener(this);
        addClassificationBtn.setOnClickListener(this);


        db = FirebaseFirestore.getInstance();
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
            EditText[] rishonEditTextArray = {(EditText) popUpView.findViewById(R.id.rishon_hour_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.rishon_minute_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.rishon_minute_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.rishon_minute_of_close_hour_edit_text_open_hour_dialog)};
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


            finishFullFillPopUpBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (int i = 0; i < editTexts.size(); i++) {

                        if (!editTexts.get(i)[0].getText().toString().isEmpty() && !editTexts.get(i)[1].getText().toString().isEmpty()
                                && !editTexts.get(i)[2].getText().toString().isEmpty() && !editTexts.get(i)[3].getText().toString().isEmpty()) {

                            openHoursList.add(editTexts.get(i)[0].getText().toString() + ":" + editTexts.get(i)[1].getText().toString());
                            closeHoursList.add(editTexts.get(i)[2].getText().toString() + ":" + editTexts.get(i)[3].getText().toString());
                        }
                    }

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

            EditText areaNameEditText = popUpView.findViewById(R.id.area_name_edit_text_add_area_pop_up_window);
            EditText deliveryCostEditText = popUpView.findViewById(R.id.delivery_cost_edit_text_add_area_pop_up_window);
            EditText minToDeliverEditText = popUpView.findViewById(R.id.min_to_deliver_edit_text_add_area_pop_up_window);
            EditText timeOfDeliveryEditText = popUpView.findViewById(R.id.time_of_delivery_edit_text_add_area_pop_up_window);

            Button addAreaForDeliver = popUpView.findViewById(R.id.add_area_for_delivery_btn_add_area_pop_up_window);

            addAreaForDeliver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String areaName = areaNameEditText.getText().toString();
                    String deliveryCost = deliveryCostEditText.getText().toString();
                    String minToDeliver = minToDeliverEditText.getText().toString();
                    String timeOfDelivery = timeOfDeliveryEditText.getText().toString();


                    if (!areaName.isEmpty() && !deliveryCost.isEmpty()
                            && !minToDeliver.isEmpty() && !timeOfDelivery.isEmpty()) {
                        areasForDeliveries.add(new AreasForDelivery(areaName, Integer.parseInt(deliveryCost), Integer.parseInt(minToDeliver), Integer.parseInt(timeOfDelivery)));
                        areaDialog.dismiss();
                    }
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

            String deliveryCost = deliveryCostEditText.getText().toString();
            String deliveryTime = timeDeliveryEditText.getText().toString();


            if (!name.isEmpty() && !city.isEmpty() && !street.isEmpty()
                    && !streetNumber.isEmpty() && !deliveryCost.isEmpty() && !deliveryTime.isEmpty()
                    && !phone.isEmpty()) {


                if (openHoursList.size() == 7 && closeHoursList.size() == 7) {

                    //TODO: WORK ON  logoImageUri != null && profileImageUri != null &&

                    if (!areasForDeliveries.isEmpty() && !isKosherString.isEmpty() && !discountString.isEmpty() && !classificationHandleList.isEmpty()) {

                        if (isKosherString.equals("כשר"))
                            isKosher = true;

                        if (discountString.equals("תומך בהנחה"))
                            isDiscount = true;


                        MyAddress myAddress = new MyAddress(city, street, streetNumber);

                        Geocoder geocoder = new Geocoder(getApplicationContext());
                        String address = "ישראל, " + myAddress.getCityName() + "," +
                                myAddress.getStreetName() + " " + myAddress.getHouseNumber();

                        try {
                            List<Address> addresses = geocoder.getFromLocationName(address, 1);

                            if (addresses.size() > 0) {


                                myAddress.setLatitude(addresses.get(0).getLatitude());
                                myAddress.setLongitude(addresses.get(0).getLongitude());


                                Date todayDate = new Date();
                                String dateOfAddRestaurant = new SimpleDateFormat("dd/MM/yyyy").format(todayDate);


                                Restaurant restaurant = new Restaurant(name, myAddress, areasForDeliveries, phone, openHoursList, closeHoursList, dateOfAddRestaurant, deliveryTime, logoImageUri, profileImageUri, isKosher, isDiscount, classificationHandleList, new Menu());
                                db.collection(getResources().getString(R.string.RESTAURANTS_PATH)).add(restaurant).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {

                                        if (task.isSuccessful()) {
                                            Toast.makeText(AddRestaurantActivity.this, "המסעדה נוספה בהצלחה!", Toast.LENGTH_SHORT).show();
                                            finish();

                                        }


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AddRestaurantActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else
                                Toast.makeText(this, "לא נמצאה כתובת זו אנא נסה שוב", Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else
                        Toast.makeText(this, "אנא הוסף איזורי שליחה או/ו 2 תמונות למסעדה", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, "חובה להכניס 7 שעות פתיחה ושעות סגירה", Toast.LENGTH_SHORT).show();

            } else
                Toast.makeText(this, "יש צורך למלא את הפרטים שבתבניות", Toast.LENGTH_SHORT).show();
        }
    }


    public void takePic(View view) {


    }


}
