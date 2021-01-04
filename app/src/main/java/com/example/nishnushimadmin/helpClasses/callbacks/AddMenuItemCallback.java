package com.example.nishnushimadmin.helpClasses.callbacks;

import com.example.nishnushimadmin.helpClasses.Classification;
import com.example.nishnushimadmin.helpClasses.Dish;
import com.example.nishnushimadmin.helpClasses.Menu;

public interface AddMenuItemCallback {
    public void addMenuItem(Menu menu);
    public void addClassificationCallback(Classification classification);
    public void addDishCallback(Dish dish);
}
