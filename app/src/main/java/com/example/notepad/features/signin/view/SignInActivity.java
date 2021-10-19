package com.example.notepad.features.signin.view;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import com.example.notepad.R;
import com.example.notepad.features.signin.viewmodel.SignInState;
import com.example.notepad.features.signin.viewmodel.SignInViewModel;
import com.example.notepad.navigation.NavigationActivity;
import com.example.notepad.observable.Observer;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class SignInActivity extends NavigationActivity implements Observer<SignInState> {

    private TextInputEditText emailText;
    private TextInputEditText passwordText;
    private MaterialButton signInButton;

    private SignInViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        viewModel = new SignInViewModel();

        signInButton = findViewById(R.id.signInButton);
        passwordText = findViewById(R.id.passwordText);
        emailText = findViewById(R.id.emailText);

        signInButton.setOnClickListener(v -> onSignInCLick());

        viewModel.state.registerObserver(this);
    }

    private void onSignInCLick() {
        if (emailText.getText() == null || passwordText.getText() == null)
            return;

        String email = emailText.getText().toString();
        String pass = passwordText.getText().toString();

        if (email.isEmpty() || pass.isEmpty())
            return;

        viewModel.onSignInCLick(email, pass);
    }

    @Override
    public void notify(SignInState data) {
        if (data == SignInState.ERROR) {
            showSignInError();
        }
    }

    public void showSignInError() {
        new AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage("Please try again")
            .setPositiveButton("Cancel", (dialog, which) -> dialog.dismiss())
            .setCancelable(true)
            .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.onResume();
    }

    @Override
    protected void onDestroy() {
        viewModel.onDestroy();
        super.onDestroy();
    }
}