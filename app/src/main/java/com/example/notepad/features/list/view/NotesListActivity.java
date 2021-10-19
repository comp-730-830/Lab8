package com.example.notepad.features.list.view;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notepad.R;
import com.example.notepad.data.Note;
import com.example.notepad.features.list.presenter.NotesPresenter;
import com.example.notepad.navigation.NavigationActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NotesListActivity extends NavigationActivity implements NotesView {

    private RecyclerView listView;
    private FloatingActionButton createButton;
    private NotesAdapter adapter;

    private NotesPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        initListView();
        initCreateButton();

        presenter = new NotesPresenter(this);
    }

    private void initListView() {
        listView = findViewById(R.id.listView);
        adapter = new NotesAdapter();
        adapter.setDeleteListener(presenter);
        adapter.setEditListener(presenter);
        listView.setAdapter(adapter);
    }

    private void initCreateButton() {
        createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(v -> presenter.onCreateClick());
    }

    @Override
    public void showNotes(List<Note> notes) {
        adapter.submitList(notes);
    }

    @Override
    public void showDeleteConfirmation(Note note) {
        new AlertDialog.Builder(this)
            .setTitle("Delete Note")
            .setMessage("Are you sure you want to delete the note? This can't be undone.")
            .setPositiveButton("Delete", (dialog, which) -> presenter.onConfirmDeleteClick(note))
            .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
            .setCancelable(true)
            .show();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        presenter = null;
        super.onDestroy();
    }
}