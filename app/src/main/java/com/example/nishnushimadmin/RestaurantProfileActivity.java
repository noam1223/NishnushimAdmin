package com.example.nishnushimadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nishnushimadmin.adapters.AreasForDeliveryAdapter;
import com.example.nishnushimadmin.adapters.OpenCloseRestaurantHoursAdapter;
import com.example.nishnushimadmin.helpClasses.Restaurant;

public class RestaurantProfileActivity extends AppCompatActivity {

    TextView restaurantNameHeadLineTextView, addressRestaurantTextView, phoneNumberRestaurantTextView;
    ImageButton backImgBtn;
    Button addMenuBtn, previewRestaurantBtn, editRestaurantProfileBtn;

    TextView areaOfDeliveryTextView;

    Restaurant restaurant;

    ListView areaForDeliveryListView, openCloseHoursListView;

    String[] daysOfWeek = {"יום ראשון", "יום שני", "יום שלישי", "יום רביעי", "יום חמישי", "יום שישי", "יום שבת"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_profile);

        restaurantNameHeadLineTextView = findViewById(R.id.head_line_text_view_restaurant_profile_activity);
        addressRestaurantTextView = findViewById(R.id.full_address_restaurant_profile_activity);
        phoneNumberRestaurantTextView = findViewById(R.id.phone_number_restaurant_profile_activity);
        addMenuBtn = findViewById(R.id.menu_btn_restaurant_profile_activity);
        editRestaurantProfileBtn = findViewById(R.id.edit_profile_restaurant_btn_restaurant_profile_activity);


        previewRestaurantBtn = findViewById(R.id.preview_restaurant_btn_restaurant_profile_activity);
        openCloseHoursListView = findViewById(R.id.open_close_hours_list_view_restaurant_profile_activity);
        areaForDeliveryListView = findViewById(R.id.area_for_delivery_list_view_restaurant_profile_activity);



        if (getIntent().getSerializableExtra("restaurant") != null && getIntent().getSerializableExtra("restaurant") != null) {

            restaurant = (Restaurant) getIntent().getSerializableExtra("restaurant");


            addMenuBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(), AddMenuActivity.class);
                    intent.putExtra("restaurant", restaurant);
                    startActivity(intent);


                }
            });


            backImgBtn = findViewById(R.id.back_btn_restaurant_profile_activity);
            backImgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });


            restaurantNameHeadLineTextView.setText(restaurant.getName());
            addressRestaurantTextView.setText(restaurant.getMyAddress().fullMyAddress());
            phoneNumberRestaurantTextView.setText(restaurant.getPhoneNumber());

            openCloseHoursListView.setAdapter(new OpenCloseRestaurantHoursAdapter(this, restaurant.getOpenHour(), restaurant.getCloseHour()));
            areaForDeliveryListView.setAdapter(new AreasForDeliveryAdapter(this, restaurant.getAreasForDelivery()));

            previewRestaurantBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    View popUpView = getLayoutInflater().inflate(R.layout.item_restauran_details, null);
                    Dialog restaurantDialogPreview = new Dialog(RestaurantProfileActivity.this);
                    restaurantDialogPreview.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    restaurantDialogPreview.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    restaurantDialogPreview.setContentView(popUpView);


                    ImageView profileImageView = popUpView.findViewById(R.id.restaurant_main_image_view_restaurant_details_item);
                    ImageView logoImageView = popUpView.findViewById(R.id.restaurant_logo_image_view_restaurant_item);

                    TextView restaurantNameTextView = popUpView.findViewById(R.id.restaurant_name_text_view_restaurant_detail_item);
                    TextView restaurantAddressTextView = popUpView.findViewById(R.id.full_address_text_view_restaurant_detail_item);

                    restaurantNameTextView.setText(restaurant.getName());
                    restaurantAddressTextView.setText(restaurant.getMyAddress().fullMyAddress());


                    restaurantDialogPreview.setCanceledOnTouchOutside(true);
                    restaurantDialogPreview.create();
                    restaurantDialogPreview.show();

                }
            });



            editRestaurantProfileBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(RestaurantProfileActivity.this, AddRestaurantActivity.class);
                    intent.putExtra("restaurant", restaurant);
                    startActivityForResult(intent, 3);

                }
            });



        } else finish();
    }





    private StringBuilder getFullHourString(String openHour, String closeHour) {

        StringBuilder fullOpenHourString = new StringBuilder();


        String[] openMinute = openHour.split(":");
        String[] closeMinute = closeHour.split(":");

        if (Integer.parseInt(openMinute[1]) == 0) {
            fullOpenHourString.append(openHour).append("0").append(" - ");
        }


        if (Integer.parseInt(closeMinute[1]) == 0) {
            fullOpenHourString.append(closeHour).append("0");
        }

        return fullOpenHourString;
    }


}