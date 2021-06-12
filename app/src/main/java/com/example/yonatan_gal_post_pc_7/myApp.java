package com.example.yonatan_gal_post_pc_7;

import android.app.Application;

import com.google.firebase.firestore.FirebaseFirestore;

public class myApp extends Application {

    public FirebaseFirestore firestore;

    public myApp()
    {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
