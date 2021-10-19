package com.example.notepad;

import android.app.Application;

import com.example.notepad.navigation.Navigator;
import com.google.firebase.FirebaseApp;

public class App extends Application {
    private static App INSTANCE;

    private Navigator appNavigator;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        FirebaseApp.initializeApp(this);
        appNavigator = new Navigator();
    }

    public static App getInstance() {
        return INSTANCE;
    }

    public static Navigator getAppNavigator() {
        return getInstance().appNavigator;
    }
}
