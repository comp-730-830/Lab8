package com.example.notepad.features.edit.controller;

import com.example.notepad.App;
import com.example.notepad.features.edit.model.EditNoteModel;
import com.example.notepad.features.edit.view.EditNoteView;

public class EditNoteController {
    private EditNoteModel model;
    private EditNoteView view;

    public EditNoteController(EditNoteView view, EditNoteModel model) {
        this.view = view;
        this.model = model;
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
