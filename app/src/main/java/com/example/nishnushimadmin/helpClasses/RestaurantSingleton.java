package com.example.nishnushimadmin.helpClasses;


import android.content.Context;

import com.example.nishnushimadmin.R;

public class RestaurantSingleton {

    public static String userFormatted(String s, Context context){
        return s + "@" + context.getString(R.string.EMAIL);
    }

}
