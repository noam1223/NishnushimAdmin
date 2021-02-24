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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nishnushimadmin.helpClasses.Restaurant;

public class RestaurantProfileActivity extends AppCompatActivity {

    TextView restaurantNameHeadLineTextView, addressRestaurantTextView, phoneNumberRestaurantTextView;
    ImageButton backImgBtn;
    Button addMenuBtn, previewRestaurantBtn, editRestaurantProfileBtn;

    LinearLayout areaOfDeliveryLinearLayout;
    TextView areaOfDeliveryTextView;

    Restaurant restaurant;

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
        areaOfDeliveryTextView = findViewById(R.id.area_of_delivery_text_view_restaurant_profile_activity);


        areaOfDeliveryLinearLayout = findViewById(R.id.linear_layout_area_of_delivery_restaurant_profile_activity);
        previewRestaurantBtn = findViewById(R.id.preview_restaurant_btn_restaurant_profile_activity);

        TextView[] daysOpenCloseHourTextView =  {findViewById(R.id.day_one_open_close_hour_text_view_restaurant_profile_activity),
                findViewById(R.id.day_two_open_close_hour_text_view_restaurant_profile_activity),
                findViewById(R.id.day_three_open_close_hour_text_view_restaurant_profile_activity),
                findViewById(R.id.day_four_open_close_hour_text_view_restaurant_profile_activity),
                findViewById(R.id.day_five_open_close_hour_text_view_restaurant_profile_activity),
                findViewById(R.id.day_six_open_close_hour_text_view_restaurant_profile_activity),
                findViewById(R.id.day_seven_open_close_hour_text_view_restaurant_profile_activity)};





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

            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < daysOpenCloseHourTextView.length; i++) {

                stringBuilder = new StringBuilder();
                stringBuilder.append(daysOfWeek[i]).append(": ");
                stringBuilder.append("פתוח ").append(restaurant.getOpenHour().get(i)).append(" - ");
                stringBuilder.append("סגור ").append(restaurant.getCloseHour().get(i));
                stringBuilder.append("\n");
                daysOpenCloseHourTextView[i].setText(stringBuilder.toString());
            }


            stringBuilder = new StringBuilder();

            for (int i = 0; i < restaurant.getAreasForDeliveries().size(); i++) {

                stringBuilder.append("איזור חלוקה").append(" - ").append(restaurant.getAreasForDeliveries().get(i).getAreaName());
                stringBuilder.append("\n");
                stringBuilder.append("עלות משלוח").append(" - ").append(restaurant.getAreasForDeliveries().get(i).getDeliveryCost());
                stringBuilder.append("\n");
                stringBuilder.append("מינימום לזמנה").append(" - ").append(restaurant.getAreasForDeliveries().get(i).getMinToDeliver());
                stringBuilder.append("\n");
                stringBuilder.append("זמן משלוח").append(" - ").append(restaurant.getAreasForDeliveries().get(i).getTimeOfDelivery());
                stringBuilder.append("\n");

            }

            areaOfDeliveryTextView.setText(stringBuilder);



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