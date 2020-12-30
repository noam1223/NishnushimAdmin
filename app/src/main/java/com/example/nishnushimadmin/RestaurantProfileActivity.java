package com.example.nishnushimadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nishnushimadmin.helpClasses.Restaurant;

public class RestaurantProfileActivity extends AppCompatActivity {

    TextView restaurantNameHeadLineTextView, addressRestaurantTextView, openHoursRestaurantTextView, deliveryTimeTextView, amountOfDeliveryTextView, phoneNumberRestaurantTextView;
    ImageButton backImgBtn;
    Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_profile);

        restaurantNameHeadLineTextView = findViewById(R.id.head_line_text_view_restaurant_profile_activity);
        addressRestaurantTextView = findViewById(R.id.full_address_restaurant_profile_activity);
        openHoursRestaurantTextView = findViewById(R.id.open_hours_restaurant_profile_activity);
        deliveryTimeTextView = findViewById(R.id.time_delivery_restaurant_profile_activity);
        amountOfDeliveryTextView = findViewById(R.id.amount_delivery_restaurant_profile_activity);
        phoneNumberRestaurantTextView = findViewById(R.id.phone_number_restaurant_profile_activity);

        backImgBtn = findViewById(R.id.back_btn_restaurant_profile_activity);
        backImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getIntent().getSerializableExtra("restaurant") != null){

            restaurant = (Restaurant) getIntent().getSerializableExtra("restaurant");

            restaurantNameHeadLineTextView.setText(restaurant.getName());
            addressRestaurantTextView.setText(restaurant.getMyAddress().getCityName() + "," + restaurant.getMyAddress().getStreetName() + " " + restaurant.getMyAddress().getHouseNumber());
            deliveryTimeTextView.setText(restaurant.getDeliveryTime());
            amountOfDeliveryTextView.setText(String.valueOf(restaurant.getMinAmountToDeliver()));
            phoneNumberRestaurantTextView.setText(restaurant.getPhoneNumber());

        }

    }



    private StringBuilder getFullHourString(String openHour, String closeHour) {

        StringBuilder fullOpenHourString = new StringBuilder();


        String[] openMinute = openHour.split(":");
        String[] closeMinute = closeHour.split(":");

        if (Integer.parseInt(openMinute[1]) == 0){
            fullOpenHourString.append(openHour).append("0").append(" - ");
        }


        if (Integer.parseInt(closeMinute[1]) == 0){
            fullOpenHourString.append(closeHour).append("0");
        }

        return fullOpenHourString;
    }


}