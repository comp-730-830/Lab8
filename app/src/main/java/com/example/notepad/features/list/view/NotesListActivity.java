package com.example.notepad.features.list.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notepad.R;
import com.example.notepad.data.Note;
import com.example.notepad.features.list.controller.NotesController;
import com.example.notepad.features.list.model.NotesModel;
import com.example.notepad.navigation.NavigationActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class NotesListActivity extends NavigationActivity implements NotesView {

    private RecyclerView listView;
    private FloatingActionButton createButton;
    private NotesAdapter adapter;

    private ListenerRegistration registration = null;

    private NotesModel model;
    private NotesController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        model = new NotesModel();
        controller = new NotesController(this, model);

        initListView();
        initCreateButton();
        observeData();
    }

    private void initListView() {
        listView = findViewById(R.id.listView);
        adapter = new NotesAdapter();
        adapter.setDeleteListener(controller);
        adapter.setEditListener(controller);
        listView.setAdapter(adapter);
    }

    private void initCreateButton() {
        createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(v -> controller.onCreateClick());
    }

    private void observeData() {
        registration = model.getNotes().addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    error.printStackTrace();
                    return;
                }
                if (value != null) {
                    List<Note> notes = value.toObjects(Note.class);
                    adapter.submitList(notes);
                }
            }
        });
    }

    @Override
    public void showDeleteConfirmation(Note note) {
        new AlertDialog.Builder(this)
            .setTitle("Delete Note")
            .setMessage("Are you sure you want to delete the note? This can't be undone.")
            .setPositiveButton("Delete", (dialog, which) -> controller.onConfirmDeleteClick(note))
            .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
            .setCancelable(true)
            .show();
    }

    @Override
    protected void onDestroy() {
        if (registration != null) {
            // Unsubscribe to prevent memory leaks
            registration.remove();
        }
        controller = null;
        super.onDestroy();
    }
}