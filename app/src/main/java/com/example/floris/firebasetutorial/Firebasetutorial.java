package com.example.floris.firebasetutorial;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Floris on 12/7/2016.
 */

public class Firebasetutorial extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
