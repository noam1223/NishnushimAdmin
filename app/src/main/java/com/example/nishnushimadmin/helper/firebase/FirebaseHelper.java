package com.example.nishnushimadmin.helper.firebase;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.nishnushimadmin.R;
import com.example.nishnushimadmin.helper.callbacks.RestaurantsCallback;
import com.example.nishnushimadmin.helper.callbacks.UserCallback;
import com.example.nishnushimadmin.model.Restaurant;
import com.example.nishnushimadmin.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class FirebaseHelper {


    Context context;

    public FirebaseFirestore db;
    public StorageReference storageReference;
    public FirebaseAuth auth;
    public String userID;


    public FirebaseHelper(Context context) {

        this.context = context;
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null)
            userID = auth.getCurrentUser().getUid();

        initFirebase();
    }


    public void initFirebase() {

        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

    }


    public boolean writeRestaurant(Restaurant restaurant) {

        if (restaurant == null) {
            return false;
        }


        try {

            if (restaurant.getId() == null)
                db.collection(context.getString(R.string.RESTAURANTS_PATH)).add(restaurant);
            else
                db.collection(context.getString(R.string.RESTAURANTS_PATH)).document(restaurant.getId()).set(restaurant);


            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }





    public void getRestaurantsFromServer(RestaurantsCallback restaurantsCallback) {

        List<Restaurant> restaurants = new ArrayList<>();

        db.collection(context.getString(R.string.RESTAURANTS_PATH)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    if (task.getResult() != null && !task.getResult().isEmpty()) {

                        for (DocumentSnapshot documentSnapshot :
                                task.getResult()) {

                            Restaurant restaurant = documentSnapshot.toObject(Restaurant.class);
                            restaurant.setId(documentSnapshot.getId());
                            restaurants.add(restaurant);

                        }

                        restaurantsCallback.onRestaurantsListCallback(restaurants, null);

                    } else
                        restaurantsCallback.onRestaurantsListCallback(restaurants, null);

                } else if (task.isCanceled() && task.getException() != null) {
                    restaurantsCallback.onRestaurantsListCallback(restaurants, task.getException().getLocalizedMessage());

                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                restaurantsCallback.onRestaurantsListCallback(restaurants, e.getLocalizedMessage());
            }
        });
    }




    public void getUserFromServer(UserCallback userCallback) {

        List<User> userList = new ArrayList<>();

        db.collection(context.getString(R.string.USERS_DB)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    if (task.getResult() != null && !task.getResult().isEmpty()) {

                        for (DocumentSnapshot documentSnapshot :
                                task.getResult()) {

                            User user = documentSnapshot.toObject(User.class);
                            user.setId(documentSnapshot.getId());
                            userList.add(user);

                        }

                        userCallback.onUserListCallback(userList, null);

                    } else
                        userCallback.onUserListCallback(userList, null);


                } else if (task.isCanceled() && task.getException() != null) {
                    userCallback.onUserListCallback(userList, task.getException().getLocalizedMessage());


                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                userCallback.onUserListCallback(userList, e.getLocalizedMessage());

            }
        });
    }









}
