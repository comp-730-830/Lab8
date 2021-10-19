package com.example.notepad.features.edit.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import com.example.notepad.R;
import com.example.notepad.data.Note;
import com.example.notepad.features.edit.controller.EditNoteController;
import com.example.notepad.features.edit.model.EditNoteModel;
import com.example.notepad.navigation.NavigationActivity;
import com.google.android.material.button.MaterialButton;

public class EditNoteActivity extends NavigationActivity implements EditNoteView {
    public static final String NOTE_EXTRA = "NOTE";

    private Toolbar toolbar;
    private AppCompatEditText editText;
    private MaterialButton saveButton;

    private EditNoteModel model;
    private EditNoteController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        Note note = (Note) getIntent().getSerializableExtra(NOTE_EXTRA);
        model = new EditNoteModel(note);
        controller = new EditNoteController(this, model);

        initToolbar();
        initEditText();
        initSaveButton();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> controller.onBackClick());
    }

    private void initEditText() {
        editText = findViewById(R.id.editText);
        editText.setText(model.getContent());
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                controller.onTextChanged(s.toString());
            }
        });
    }

    private void initSaveButton() {
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> controller.onSaveClick());
    }

    @Override
    public void showConfirmDiscard() {
        new AlertDialog.Builder(this)
            .setTitle("Discard Changes")
            .setMessage("Are you sure you want to discard your changes in the note? ")
            .setPositiveButton("Discard", (dialog, which) -> controller.onDiscardClick())
            .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
            .setCancelable(true)
            .show();
    }

    @Override
    public void onBackPressed() {
        controller.onBackClick();
    }

    @Override
    protected void onDestroy() {
        controller = null;
        super.onDestroy();
    }
}