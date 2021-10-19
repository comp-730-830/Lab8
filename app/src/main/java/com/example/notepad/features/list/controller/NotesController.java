package com.example.notepad.features.list.controller;

import com.example.notepad.App;
import com.example.notepad.data.Note;
import com.example.notepad.features.list.model.NotesModel;
import com.example.notepad.features.list.view.NotesAdapter;
import com.example.notepad.features.list.view.NotesView;
import com.example.notepad.navigation.Screens;

public class NotesController implements NotesAdapter.OnDeleteListener, NotesAdapter.OnEditListener {
    private NotesModel model;
    private NotesView view;

    public NotesController(NotesView view, NotesModel model) {
        this.view = view;
        this.model = model;
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
}
