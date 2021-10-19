package com.example.notepad.features.edit.viewmodel;

import com.example.notepad.App;
import com.example.notepad.data.Note;
import com.example.notepad.features.edit.model.EditNoteModel;
import com.example.notepad.observable.Observable;

public class EditNoteViewModel {
    private EditNoteModel model;

    public Observable<String> content = new Observable();
    public Observable<EditNoteState> state = new Observable(EditNoteState.NORMAL);

    public EditNoteViewModel(Note note) {
        this.model = new EditNoteModel(note);
        content.newValue(model.getContent());
    }

    public void onTextChanged(String text) {
        model.setContent(text);
    }

    public void onSaveClick() {
        if (model.isNew()) {
            model.createNote().addOnSuccessListener(task -> App.getAppNavigator().back());
        } else {
            model.updateNote().addOnSuccessListener(task -> App.getAppNavigator().back());
        }
    }

    public void onBackClick() {
        state.newValue(EditNoteState.DISCARD_CONFIRMATION);
    }

    public void onDiscardClick() {
        App.getAppNavigator().back();
    }

    public void onDestroy() {
        content.unregisterAll();
        state.unregisterAll();
    }
}
