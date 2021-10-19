package com.example.notepad.navigation;

import android.app.Activity;

public class Back implements Command {
    @Override
    public void execute(Activity activity) {
        activity.finish();
    }
}
