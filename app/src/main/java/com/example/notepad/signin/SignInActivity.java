package com.example.notepad.signin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.notepad.App;
import com.example.notepad.R;
import com.example.notepad.data.Database;
import com.example.notepad.navigation.NavigationActivity;
import com.example.notepad.navigation.Screens;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;

public class SignInActivity extends NavigationActivity {

    private TextInputEditText emailText;
    private TextInputEditText passwordText;
    private MaterialButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signInButton = findViewById(R.id.signInButton);
        passwordText = findViewById(R.id.passwordText);
        emailText = findViewById(R.id.emailText);

        signInButton.setOnClickListener(v -> onSignInCLick());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Database.getInstance().isUserSignedIn()) {
            showNotesList();
        }
    }

    private void onSignInCLick() {
        if (emailText.getText() == null || passwordText.getText() == null)
            return;

        String email = emailText.getText().toString();
        String pass = passwordText.getText().toString();

        if (email.isEmpty() || pass.isEmpty())
            return;

        Database.getInstance().signIn(email, pass)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        showNotesList();
                    } else {
                        showSignInError();
                    }
                }
            });
    }

    private void showSignInError() {
        new AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage("Please try again")
            .setPositiveButton("Cancel", (dialog, which) -> dialog.dismiss())
            .setCancelable(true)
            .show();
    }

    private void showNotesList() {
        App.getAppNavigator().replace(Screens.notesList);
    }
}