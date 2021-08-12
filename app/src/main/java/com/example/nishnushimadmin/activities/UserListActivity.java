package com.example.nishnushimadmin.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.nishnushimadmin.R;
import com.example.nishnushimadmin.adapters.restaurants.RestaurantsAdapter;
import com.example.nishnushimadmin.adapters.users.UserAdapter;
import com.example.nishnushimadmin.helper.callbacks.UserCallback;
import com.example.nishnushimadmin.helper.firebase.FirebaseHelper;
import com.example.nishnushimadmin.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity implements UserCallback {

    RecyclerView userRecyclerView;
    RecyclerView.Adapter userAdapter;

    FirebaseHelper firebaseHelper;
    List<User> userList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        userRecyclerView = findViewById(R.id.user_recycler_view_activity_user_list);
        initializeUserRecyclerView();

        firebaseHelper = new FirebaseHelper(this);
        firebaseHelper.getUserFromServer(this);

    }


    private void initializeUserRecyclerView() {

        userRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        userRecyclerView.setLayoutManager(layoutManager);
        userAdapter = new UserAdapter(this, userList);
        userRecyclerView.setAdapter(userAdapter);

    }


    @Override
    public void onUserListCallback(List<User> userList, String error) {

        if (error != null)
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();

        ((UserAdapter)userAdapter).setUsers(userList);
        userAdapter.notifyDataSetChanged();
    }
}