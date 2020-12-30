package com.example.nishnushimadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.ArrayList;
import java.util.List;

public class AddRestaurantActivity extends AppCompatActivity implements View.OnClickListener {

    EditText restaurantNameEditText, cityEditText, streetEditText, numberStreetEditText, deliveryCostEditText, timeDeliveryEditText, phoneNumberEditText;
    Button addRestaurantBtn, hoursForRestaurantBtn, watchAreasForDeliveryBtn, setAreasForDeliveryBtn;
    ImageView logoImageView, profileImageView;
    List<String> openHoursList = new ArrayList<>();
    List<String> closeHoursList = new ArrayList<>();

    boolean fromOrTo = false; // false - from , true - to

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

        addRestaurantBtn = findViewById(R.id.add_restaurant_btn_add_restaurant_activity);
        hoursForRestaurantBtn = findViewById(R.id.open_set_hours_dialog_add_restaurant_activity);
        watchAreasForDeliveryBtn = findViewById(R.id.watch_area_for_delivery_list_add_restaurant_activity);
        setAreasForDeliveryBtn = findViewById(R.id.add_area_for_delivery_add_restaurant_activity);


        hoursForRestaurantBtn.setOnClickListener(this);
        watchAreasForDeliveryBtn.setOnClickListener(this);
        setAreasForDeliveryBtn.setOnClickListener(this);



        db = FirebaseFirestore.getInstance();
    }




    @Override
    public void onClick(View v) {

        int id = v.getId();



        if (id == R.id.open_set_hours_dialog_add_restaurant_activity){


            View popUpView = getLayoutInflater().inflate(R.layout.open_hours_dialog_pop_up_window, null);
            Dialog hoursDialog = new Dialog(this);
            hoursDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            hoursDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            hoursDialog.setContentView(popUpView);


            Button finishFullFillPopUpBtn = popUpView.findViewById(R.id.finish_btn_open_hours_dialog_pop_up_window);
            EditText[] rishonEditTextArray = { (EditText) popUpView.findViewById(R.id.rishon_hour_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.rishon_minute_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.rishon_minute_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.rishon_minute_of_close_hour_edit_text_open_hour_dialog) };
            EditText[] sheniEditTextArray = { (EditText) popUpView.findViewById(R.id.sheni_hour_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.sheni_minute_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.sheni_hour_of_close_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.sheni_minute_of_close_hour_edit_text_open_hour_dialog) };
            EditText[] shlishiEditTextArray = { (EditText) popUpView.findViewById(R.id.shlishi_hour_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.shlishi_minute_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.shlishi_hour_of_close_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.shlishi_minute_of_close_hour_edit_text_open_hour_dialog) };
            EditText[] reveiEditTextArray = { (EditText) popUpView.findViewById(R.id.revei_hour_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.revei_minute_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.revei_hour_of_close_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.revei_minute_of_close_hour_edit_text_open_hour_dialog) };
            EditText[] hamishiEditTextArray = { (EditText) popUpView.findViewById(R.id.hamishi_hour_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.hamishi_minute_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.hamishi_hour_of_close_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.hamishi_minute_of_close_hour_edit_text_open_hour_dialog) };
            EditText[] shishiEditTextArray = { (EditText) popUpView.findViewById(R.id.shishi_hour_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.shishi_minute_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.shishi_hour_of_close_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.shishi_minute_of_close_hour_edit_text_open_hour_dialog) };
            EditText[] shabatEditTextArray = { (EditText) popUpView.findViewById(R.id.shabat_hour_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.shabat_minute_of_open_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.shabat_hour_of_close_hour_edit_text_open_hour_dialog), popUpView.findViewById(R.id.shabat_minute_of_close_hour_edit_text_open_hour_dialog) };


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
                                && !editTexts.get(i)[2].getText().toString().isEmpty() && !editTexts.get(i)[3].getText().toString().isEmpty()){

                            openHoursList.add(editTexts.get(i)[0] + ":" + editTexts.get(i)[1]);
                            closeHoursList.add(editTexts.get(i)[2] + ":" + editTexts.get(i)[3]);
                        }
                    }

                    hoursDialog.dismiss();
                }
            });


            hoursDialog.show();


        } else if (id == R.id.watch_area_for_delivery_list_add_restaurant_activity){




        }else if (id == R.id.add_area_for_delivery_add_restaurant_activity){




        } else if (id == R.id.add_restaurant_btn_add_restaurant_activity){

            if (!restaurantNameEditText.getText().toString().isEmpty() && !cityEditText.getText().toString().isEmpty() && !streetEditText.getText().toString().isEmpty()
                    && !numberStreetEditText.getText().toString().isEmpty() &&  !deliveryCostEditText.getText().toString().isEmpty() && !timeDeliveryEditText.getText().toString().isEmpty()
                    && !phoneNumberEditText.getText().toString().isEmpty()) {


                if (openHoursList.size() == 7 && closeHoursList.size() == 7){




                }


            }

        }



    }




    public void takePic(View view) {





    }


}
