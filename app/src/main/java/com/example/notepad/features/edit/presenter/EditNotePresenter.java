package com.example.notepad.features.edit.presenter;

import com.example.notepad.App;
import com.example.notepad.data.Note;
import com.example.notepad.features.edit.model.EditNoteModel;
import com.example.notepad.features.edit.view.EditNoteView;

public class EditNotePresenter {
    private EditNoteModel model;
    private EditNoteView view;

    public EditNotePresenter(EditNoteView view, Note note) {
        this.view = view;
        this.model = new EditNoteModel(note);

        view.showNoteContent(model.getContent());
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
        view.showConfirmDiscard();
    }

    public void onDiscardClick() {
        App.getAppNavigator().back();
    }
}
