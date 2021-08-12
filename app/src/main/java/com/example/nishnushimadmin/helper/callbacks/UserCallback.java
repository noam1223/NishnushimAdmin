package com.example.nishnushimadmin.helper.callbacks;

import com.example.nishnushimadmin.model.User;

import java.util.List;

public interface UserCallback {
    void onUserListCallback(List<User> userList, String error);
}
