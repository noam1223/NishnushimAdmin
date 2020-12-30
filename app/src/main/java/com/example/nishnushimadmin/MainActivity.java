package com.example.nishnushimadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText passwordEditText;
    Button enterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        passwordEditText = findViewById(R.id.password_edit_text_main_activity);
        enterBtn = findViewById(R.id.enter_to_app_btn_activity_main);

        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password = passwordEditText.getText().toString();

                if (!password.isEmpty()){

                    if (password.equals(getResources().getString(R.string.passwordApp))){

                        startActivity(new Intent(MainActivity.this, ChooserActivity.class));

                    }

                }

            }
        });

    }
}