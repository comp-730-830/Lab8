package com.example.notepad.edit;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import com.example.notepad.App;
import com.example.notepad.R;
import com.example.notepad.data.Database;
import com.example.notepad.data.Note;
import com.example.notepad.navigation.NavigationActivity;
import com.google.android.material.button.MaterialButton;

public class EditNoteActivity extends NavigationActivity {
    public static final String NOTE_EXTRA = "NOTE";

    private Toolbar toolbar;
    private AppCompatEditText editText;
    private MaterialButton saveButton;

    private Database database = Database.getInstance();

    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        note = (Note) getIntent().getSerializableExtra(NOTE_EXTRA);

        initToolbar();
        initEditText();
        initSaveButton();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackClick());
    }

    private void initEditText() {
        editText = findViewById(R.id.editText);
        editText.setText(note.getContent());
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String newContent = s.toString();
                note.setContent(newContent);
            }
        });
    }

    private void initSaveButton() {
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> onSaveClick());
    }

    private void onSaveClick() {
        if (note.getId().isEmpty()) {
            database.createNote(note).addOnSuccessListener(this, task -> App.getAppNavigator().back());
        } else {
            database.updateNote(note).addOnSuccessListener(this, task -> App.getAppNavigator().back());
        }
    }

    private void onBackClick() {
        new AlertDialog.Builder(this)
            .setTitle("Discard Changes")
            .setMessage("Are you sure you want to discard your changes in the note? ")
            .setPositiveButton("Discard", (dialog, which) -> App.getAppNavigator().back())
            .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
            .setCancelable(true)
            .show();
    }

    @Override
    public void onBackPressed() {
        onBackClick();
    }
}