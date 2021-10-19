package com.example.notepad.navigation;

import android.app.Activity;
import android.content.Intent;

public class Forward implements Command {
    private Screen screen;

    public Forward(Screen screen) {
        this.screen = screen;
    }

    @Override
    public void execute(Activity activity) {
        Intent intent = new Intent(activity, screen.getActivityClass());
        intent.putExtras(screen.getExtras());
        activity.startActivity(intent);
    }
}
