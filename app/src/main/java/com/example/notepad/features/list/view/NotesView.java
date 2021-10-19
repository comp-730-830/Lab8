package com.example.notepad.features.list.view;

import com.example.notepad.data.Note;

import java.util.List;

public interface NotesView {
    void showNotes(List<Note> notes);
    void showDeleteConfirmation(Note note);
}
