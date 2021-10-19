package com.example.notepad.features.signin.viewmodel;

import androidx.annotation.NonNull;

import com.example.notepad.App;
import com.example.notepad.features.signin.model.SignInModel;
import com.example.notepad.navigation.Screens;
import com.example.notepad.observable.Observable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class SignInViewModel {
    private SignInModel model;

    public Observable<SignInState> state = new Observable<>(SignInState.NORMAL);

    public SignInViewModel() {
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
                        state.newValue(SignInState.ERROR);
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

    public void onDestroy() {
        state.unregisterAll();
    }
}
