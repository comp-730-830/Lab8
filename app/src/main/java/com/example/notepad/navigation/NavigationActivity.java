package com.example.notepad.navigation;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notepad.App;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        App.getAppNavigator().registerActivity(this);
    }

    @Override
    protected void onPause() {
        App.getAppNavigator().removeActivity();
        super.onPause();
    }
}
