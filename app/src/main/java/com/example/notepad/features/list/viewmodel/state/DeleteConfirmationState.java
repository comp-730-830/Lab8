package com.example.notepad.features.list.viewmodel.state;

import com.example.notepad.data.Note;

public class DeleteConfirmationState implements NotesListState {
    Note note;

    public DeleteConfirmationState(Note note) {
        this.note = note;
    }

    public Note getNote() {
        return note;
    }
}
