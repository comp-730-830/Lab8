package com.example.notepad.features.signin.presenter;

import androidx.annotation.NonNull;

import com.example.notepad.App;
import com.example.notepad.features.signin.model.SignInModel;
import com.example.notepad.features.signin.view.SignInView;
import com.example.notepad.navigation.Screens;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class SignInPresenter {
    private SignInModel model;
    private SignInView view;

    public SignInPresenter(SignInView view) {
        this.view = view;
        this.model = new SignInModel();
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
