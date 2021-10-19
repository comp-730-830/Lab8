package com.example.notepad.list;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notepad.App;
import com.example.notepad.R;
import com.example.notepad.data.Database;
import com.example.notepad.data.Note;
import com.example.notepad.edit.EditNoteActivity;
import com.example.notepad.navigation.NavigationActivity;
import com.example.notepad.navigation.Screens;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class NotesListActivity extends NavigationActivity implements NotesAdapter.OnDeleteListener, NotesAdapter.OnEditListener {

    private RecyclerView listView;
    private FloatingActionButton createButton;
    private NotesAdapter adapter;
    private Database database = Database.getInstance();
    private ListenerRegistration registration = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        initListView();
        initCreateButton();
        observeData();
    }

    private void initListView() {
        listView = findViewById(R.id.listView);
        adapter = new NotesAdapter();
        adapter.setDeleteListener(this);
        adapter.setEditListener(this);
        listView.setAdapter(adapter);
    }

    private void initCreateButton() {
        createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(v -> onCreateClick());
    }

    private void observeData() {
        registration = database.getNotes().addSnapshotListener(new EventListener<QuerySnapshot>() {
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
    public void onEditClick(Note note) {
        App.getAppNavigator().forward(Screens.editNote(note));
    }

    @Override
    public void onDeleteClick(Note note) {
        new AlertDialog.Builder(this)
            .setTitle("Delete Note")
            .setMessage("Are you sure you want to delete the note? This can't be undone.")
            .setPositiveButton("Delete", (dialog, which) -> onConfirmDeleteClick(note))
            .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
            .setCancelable(true)
            .show();
    }

    private void onConfirmDeleteClick(Note note) {
        database.deleteNote(note);
    }

    private void onCreateClick() {
        App.getAppNavigator().forward(Screens.editNote(new Note()));
    }

    @Override
    protected void onDestroy() {
        if (registration != null) {
            // Unsubscribe to prevent memory leaks
            registration.remove();
        }
        super.onDestroy();
    }
}