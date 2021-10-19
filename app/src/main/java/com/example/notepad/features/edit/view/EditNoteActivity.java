package com.example.notepad.features.edit.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import com.example.notepad.R;
import com.example.notepad.data.Note;
import com.example.notepad.features.edit.presenter.EditNotePresenter;
import com.example.notepad.navigation.NavigationActivity;
import com.google.android.material.button.MaterialButton;

public class EditNoteActivity extends NavigationActivity implements EditNoteView {
    public static final String NOTE_EXTRA = "NOTE";

    private Toolbar toolbar;
    private AppCompatEditText editText;
    private MaterialButton saveButton;

    private EditNotePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        initToolbar();
        initEditText();
        initSaveButton();

        Note note = (Note) getIntent().getSerializableExtra(NOTE_EXTRA);
        presenter = new EditNotePresenter(this, note);
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> presenter.onBackClick());
    }

    private void initEditText() {
        editText = findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.onTextChanged(s.toString());
            }
        });
    }

    private void initSaveButton() {
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> presenter.onSaveClick());
    }

    @Override
    public void showNoteContent(String content) {
        editText.setText(content);
    }

    @Override
    public void showConfirmDiscard() {
        new AlertDialog.Builder(this)
            .setTitle("Discard Changes")
            .setMessage("Are you sure you want to discard your changes in the note? ")
            .setPositiveButton("Discard", (dialog, which) -> presenter.onDiscardClick())
            .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
            .setCancelable(true)
            .show();
    }

    @Override
    public void onBackPressed() {
        presenter.onBackClick();
    }

    @Override
    protected void onDestroy() {
        presenter = null;
        super.onDestroy();
    }
}