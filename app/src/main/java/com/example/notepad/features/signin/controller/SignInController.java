package com.example.notepad.features.signin.controller;

import androidx.annotation.NonNull;

import com.example.notepad.App;
import com.example.notepad.features.signin.model.SignInModel;
import com.example.notepad.features.signin.view.SignInView;
import com.example.notepad.navigation.Screens;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class SignInController {
    private SignInModel model;
    private SignInView view;

    public SignInController(SignInView view, SignInModel model) {
        this.view = view;
        this.model = model;
    }

    public void onSignInCLick(String email, String password) {
        model.signIn(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        showNotesList();
                    } else {
                        view.showSignInError();
                    }
                }
            });
    }

    public void onResume() {
        if (model.isUserSignedIn()) {
            showNotesList();
        }
    }

    private void showNotesList() {
        App.getAppNavigator().replace(Screens.notesList);
    }
}
