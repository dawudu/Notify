package com.example.manthole.notify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecycleNotifications extends AppCompatActivity {

    RecyclerView recyclerView;
    Recycle recycle;
    Register register;

    String usernameNotification;

    GeoLocations geoLocations = new GeoLocations(this);
    Firebase reference1, reference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_notifications);

        Intent intent = getIntent();
        String geoMessage = intent.getStringExtra("message");
//        int geoImage = Integer.parseInt(intent.getStringExtra("image"));

        //Recycler classs and RecycleView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewone);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://notify-6b5fb.firebaseio.com/" + UserDetails.username);
//        reference1 = new Firebase("https://notify-6b5fb.firebaseio.com/" + UserDetails.username + "_" + UserDetails.chatWith);
        ArrayList<Notifications> notificationsArrayList = new ArrayList<>();

        usernameNotification = register.user.toString();

        //Notification1
        Notifications friendrequest1 = new Notifications();

        friendrequest1.setImage1(R.drawable.allnotifications);
        friendrequest1.setUsername(usernameNotification);
//        friendrequest1.setUsername(geoLocations.geofenceMessage + " " + usernameNotification);

        notificationsArrayList.add(friendrequest1);

        recycle = new Recycle(this, notificationsArrayList);
        recyclerView.setAdapter(recycle);


    }
}

//Notification2
//        Notifications friendrequest2 = new Notifications();
//
//        friendrequest2.setImage1(R.drawable.notifications);
//        friendrequest2.setUsername("Odraki Onaso");
//
//        //Notification3
//        Notifications friendrequest3 = new Notifications();
//
//        friendrequest3.setImage1(R.drawable.notifications);
//        friendrequest3.setUsername("Bellemy Agasusu");
//
//        //Notification3
//        Notifications friendrequest4 = new Notifications();
//
//        friendrequest4.setImage1(R.drawable.allnotifications);
//        friendrequest4.setUsername("Viktor Abona");


//        notificationsArrayList.add(friendrequest2);
//        notificationsArrayList.add(friendrequest3);
//        notificationsArrayList.add(friendrequest4);
