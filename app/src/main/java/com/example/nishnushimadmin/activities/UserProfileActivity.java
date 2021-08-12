package com.example.nishnushimadmin.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nishnushimadmin.R;
import com.example.nishnushimadmin.model.Restaurant;
import com.example.nishnushimadmin.model.User;

public class UserProfileActivity extends AppCompatActivity {

    TextView headLineTextView, addressTextView, phoneTextView, emailTextView;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        headLineTextView = findViewById(R.id.head_line_text_view_user_profile_activity);
        addressTextView = findViewById(R.id.full_address_user_profile_activity);
        phoneTextView = findViewById(R.id.phone_number_user_profile_activity);
        emailTextView = findViewById(R.id.email_user_profile_activity);


        if (getIntent().getSerializableExtra("user") != null && getIntent().getSerializableExtra("user") != null) {

            user = (User) getIntent().getSerializableExtra("user");

            if (user.getName() != null)
                headLineTextView.setText(user.getName());
            else headLineTextView.setText("שם משתמש : לא צויין");


            if (user.getChosenAddressString() != null)
                addressTextView.setText("כתובת : " + user.getChosenAddress().fullMyAddress());
            else headLineTextView.setText("כתובת : לא צויין");


            if (user.getPhoneNumber() != null)
                phoneTextView.setText("מספר טלפון : " + user.getPhoneNumber());
            else phoneTextView.setText("מספר טלפון : לא צויין");


            if (user.getEmail() != null)
                emailTextView.setText("אימייל : " + user.getEmail());
            else emailTextView.setText("אימייל : לא צויין");

        }

    }
}