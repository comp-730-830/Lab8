package com.example.notepad.features.edit.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import com.example.notepad.R;
import com.example.notepad.data.Note;
import com.example.notepad.features.edit.viewmodel.EditNoteState;
import com.example.notepad.features.edit.viewmodel.EditNoteViewModel;
import com.example.notepad.navigation.NavigationActivity;
import com.example.notepad.observable.Observer;
import com.google.android.material.button.MaterialButton;

public class EditNoteActivity extends NavigationActivity {
    public static final String NOTE_EXTRA = "NOTE";

    private Toolbar toolbar;
    private AppCompatEditText editText;
    private MaterialButton saveButton;

    private EditNoteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        Note note = (Note) getIntent().getSerializableExtra(NOTE_EXTRA);
        viewModel = new EditNoteViewModel(note);

        initToolbar();
        initEditText();
        initSaveButton();

        observeData();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> viewModel.onBackClick());
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
                viewModel.onTextChanged(s.toString());
            }
        });
    }

    private void initSaveButton() {
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> viewModel.onSaveClick());
    }

    private void observeData() {
        viewModel.content.registerObserver(new Observer<String>() {
            @Override
            public void notify(String data) {
                editText.setText(data);
            }
        });
        viewModel.state.registerObserver(new Observer<EditNoteState>() {
            @Override
            public void notify(EditNoteState data) {
                if(data == EditNoteState.DISCARD_CONFIRMATION) {
                    showConfirmDiscard();
                }
            }
        });
    }

    public void showConfirmDiscard() {
        new AlertDialog.Builder(this)
            .setTitle("Discard Changes")
            .setMessage("Are you sure you want to discard your changes in the note? ")
            .setPositiveButton("Discard", (dialog, which) -> viewModel.onDiscardClick())
            .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
            .setCancelable(true)
            .show();
    }

    @Override
    public void onBackPressed() {
        viewModel.onBackClick();
    }

    @Override
    protected void onDestroy() {
        viewModel.onDestroy();
        super.onDestroy();
    }
}