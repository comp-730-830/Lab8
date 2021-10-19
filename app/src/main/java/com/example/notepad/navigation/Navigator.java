package com.example.notepad.navigation;

import android.app.Activity;

public class Navigator {
    private Activity activity;

    public void registerActivity(Activity activity) {
        this.activity = activity;
    }

    public void removeActivity() {
        this.activity = null;
    }

    public void forward(Screen screen) {
        executeCommands(new Command[]{new Forward(screen)});
    }

    public void back() {
        executeCommands(new Command[]{new Back()});
    }

    public void replace(Screen screen) {
        executeCommands(new Command[]{new Forward(screen), new Back()});
    }

    private void executeCommands(Command[] commands) {
        if (activity == null)
            return;
        for (Command command : commands) {
            command.execute(activity);
        }
    }
}
