package com.example.notepad.features.list.presenter;

import androidx.annotation.Nullable;

import com.example.notepad.App;
import com.example.notepad.data.Note;
import com.example.notepad.features.list.model.NotesModel;
import com.example.notepad.features.list.view.NotesAdapter;
import com.example.notepad.features.list.view.NotesView;
import com.example.notepad.navigation.Screens;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class NotesPresenter implements NotesAdapter.OnDeleteListener, NotesAdapter.OnEditListener {
    private NotesModel model;
    private NotesView view;

    private ListenerRegistration registration = null;

    public NotesPresenter(NotesView view) {
        this.view = view;
        this.model = new NotesModel();

        observeData();
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
                    view.showNotes(notes);
                }
            }
        });
    }

    public void onCreateClick() {
        App.getAppNavigator().forward(Screens.editNote(new Note()));
    }

    @Override
    public void onEditClick(Note note) {
        App.getAppNavigator().forward(Screens.editNote(note));
    }

    @Override
    public void onDeleteClick(Note note) {
        view.showDeleteConfirmation(note);
    }

    public void onConfirmDeleteClick(Note note) {
        model.deleteNote(note);
    }

    public void onDestroy() {
        // Unsubscribe to prevent memory leaks
        registration.remove();
    }
}
