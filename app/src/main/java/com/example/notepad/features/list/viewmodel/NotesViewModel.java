package com.example.notepad.features.list.viewmodel;

import androidx.annotation.Nullable;

import com.example.notepad.App;
import com.example.notepad.data.Note;
import com.example.notepad.features.list.model.NotesModel;
import com.example.notepad.features.list.view.NotesAdapter;
import com.example.notepad.features.list.viewmodel.state.DeleteConfirmationState;
import com.example.notepad.features.list.viewmodel.state.ErrorState;
import com.example.notepad.features.list.viewmodel.state.NormalState;
import com.example.notepad.features.list.viewmodel.state.NotesListState;
import com.example.notepad.navigation.Screens;
import com.example.notepad.observable.Observable;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NotesViewModel implements NotesAdapter.OnDeleteListener, NotesAdapter.OnEditListener {
    private NotesModel model;

    public Observable<List<Note>> notes = new Observable<>();
    public Observable<NotesListState> state = new Observable<>(new NormalState());

    private ListenerRegistration registration = null;

    public NotesViewModel() {
        this.model = new NotesModel();

        observeData();
    }

    private void observeData() {
        registration = model.getNotes().addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    notes.newValue(new ArrayList<>());
                    state.newValue(new ErrorState(error));
                    return;
                }
                if (value != null) {
                    List<Note> notesList = value.toObjects(Note.class);
                    notes.newValue(notesList);
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
        state.newValue(new DeleteConfirmationState(note));
    }

    public void onConfirmDeleteClick(Note note) {
        model.deleteNote(note);
    }

    public void onDestroy() {
        // Unsubscribe to prevent memory leaks
        registration.remove();
        state.unregisterAll();
        notes.unregisterAll();
    }
}
