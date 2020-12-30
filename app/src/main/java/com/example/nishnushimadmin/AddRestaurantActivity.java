package com.example.nishnushimadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.nishnushimadmin.helpClasses.MyAddress;
import com.example.nishnushimadmin.helpClasses.Restaurant;
import com.example.nishnushimadmin.helpClasses.TimePickerFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.List;

public class AddRestaurantActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    EditText restaurantNameEditText, cityNameEditText, streetNameEditText, costOfDeliveryEditText, timeOfDeliveryEditText, phoneNumberEditText, streetNumberEditText;
    Button fromHourBtn, toHourBtn, addRestaurantBtn;

    boolean fromOrTo = false; // false - from , true - to

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        restaurantNameEditText = findViewById(R.id.restaurant_name_edit_text_add_restaurant_activity);
        cityNameEditText = findViewById(R.id.city_restaurant_name_edit_text_add_restaurant_activity);
        streetNameEditText = findViewById(R.id.street_restaurant_name_edit_text_add_restaurant_activity);
        streetNumberEditText = findViewById(R.id.number_of_street_restaurant_name_edit_text_add_restaurant_activity);
        costOfDeliveryEditText = findViewById(R.id.cost_of_delivery_add_restaurant_activity);
        timeOfDeliveryEditText = findViewById(R.id.time_of_delivery_add_restaurant_activity);
        phoneNumberEditText = findViewById(R.id.phone_number_restaurant_add_restaurant_activity);


        fromHourBtn = findViewById(R.id.from_hour_open_hour_btn_add_restaurant_activity);
        toHourBtn = findViewById(R.id.to_hour_close_hour_btn_add_restaurant_activity);
        addRestaurantBtn = findViewById(R.id.add_restaurant_btn_add_restaurant_activity);


        fromHourBtn.setOnClickListener(this);
        toHourBtn.setOnClickListener(this);
        addRestaurantBtn.setOnClickListener(this);


        db = FirebaseFirestore.getInstance();
    }



    @Override
    public void onClick(View v) {

        int id = v.getId();


        if (id == R.id.from_hour_open_hour_btn_add_restaurant_activity){

            fromOrTo = false;
            DialogFragment dialogFragment = new TimePickerFragment();
            dialogFragment.show(getSupportFragmentManager(), "שעון");


        } else if (id == R.id.to_hour_close_hour_btn_add_restaurant_activity){


            fromOrTo = true;
            DialogFragment dialogFragment = new TimePickerFragment();
            dialogFragment.show(getSupportFragmentManager(), "שעון");


        } else if (id == R.id.add_restaurant_btn_add_restaurant_activity){

            String name = restaurantNameEditText.getText().toString();
            String phone = phoneNumberEditText.getText().toString();

            String city = cityNameEditText.getText().toString();
            String street = streetNameEditText.getText().toString();
            String streetNumber = streetNumberEditText.getText().toString();

            String deliveryCost = costOfDeliveryEditText.getText().toString();
            String deliveryTime = timeOfDeliveryEditText.getText().toString();

            String openHour = fromHourBtn.getText().toString();
            String closeHour = toHourBtn.getText().toString();

            if (!name.isEmpty() && !phone.isEmpty() && !city.isEmpty() && !street.isEmpty()
                    && !streetNumber.isEmpty() && !deliveryCost.isEmpty() && !openHour.isEmpty() && !closeHour.isEmpty() && !deliveryTime.isEmpty()){

                MyAddress myAddress = new MyAddress(city, street, streetNumber);

                Geocoder geocoder = new Geocoder(getApplicationContext());
                String address = "ישראל, " + myAddress.getCityName() + "," +
                        myAddress.getStreetName() + " " + myAddress.getHouseNumber();

                try {
                    List<Address> addresses = geocoder.getFromLocationName(address, 1);

                    if (addresses.size() > 0){


                        myAddress.setLatitude(addresses.get(0).getLatitude());
                        myAddress.setLongitude(addresses.get(0).getLongitude());


                        Restaurant restaurant = new Restaurant();
                        restaurant.setName(name);
                        restaurant.setMyAddress(myAddress);
                        restaurant.setPhoneNumber(phone);
                        restaurant.setOpenHour(openHour);
                        restaurant.setCloseHour(closeHour);
                        restaurant.setDeliveryTime(deliveryTime);
                        restaurant.setAmountOfDelivery(Integer.parseInt(deliveryCost));


                        db.collection(getResources().getString(R.string.RESTAURANTS_PATH)).add(restaurant).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {

                                if (task.isSuccessful()){
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




                    } else Toast.makeText(this, "NOT FOUND ADDRESS", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

        }


    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        if (!fromOrTo){
            fromHourBtn.setText(hourOfDay + ":" + minute);
        }else toHourBtn.setText(hourOfDay + ":" + minute);

    }
}