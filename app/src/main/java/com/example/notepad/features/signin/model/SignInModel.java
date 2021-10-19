package com.example.notepad.features.signin.model;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInModel {
    private FirebaseAuth auth;

    public SignInModel() {
        this.auth = FirebaseAuth.getInstance();
    }

    public Task<AuthResult> signIn(String email, String password) {
        return auth.signInWithEmailAndPassword(email, password);
    }

    public boolean isUserSignedIn() {
        return auth.getCurrentUser() != null;
    }
}
