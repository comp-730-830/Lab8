package com.example.notepad.navigation;

import android.app.Activity;
import android.os.Bundle;

public class Screen {
    private Class<? extends Activity> activityClass;
    private Bundle extras;

    public Screen(Class<? extends Activity> activityClass) {
        this.activityClass = activityClass;
        extras = new Bundle();
    }

    public Screen(Class<? extends Activity> activityClass, Bundle extras) {
        this.activityClass = activityClass;
        this.extras = extras;
    }

    public Class<? extends Activity> getActivityClass() {
        return activityClass;
    }

    public Bundle getExtras() {
        return extras;
    }
}
