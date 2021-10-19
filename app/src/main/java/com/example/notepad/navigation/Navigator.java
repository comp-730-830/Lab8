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
        // TODO add Back command
    }

    public void replace(Screen screen) {
        // TODO implement replace function using Forward and Back commands
    }

    private void executeCommands(Command[] commands) {
        if(activity == null)
            return;
        for (Command command : commands) {
            command.execute(activity);
        }
    }
}
