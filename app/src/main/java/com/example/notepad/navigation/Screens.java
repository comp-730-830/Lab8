package com.example.notepad.navigation;

import android.os.Bundle;

import com.example.notepad.data.Note;
import com.example.notepad.features.edit.view.EditNoteActivity;
import com.example.notepad.features.list.view.NotesListActivity;
import com.example.notepad.features.signin.view.SignInActivity;

public class Screens {
    public static Screen notesList = new Screen(NotesListActivity.class);

    public static Screen singIn = new Screen(SignInActivity.class);

    public static Screen editNote(Note note) {
        Bundle extras = new Bundle();
        extras.putSerializable(EditNoteActivity.NOTE_EXTRA, note);
        return new Screen(EditNoteActivity.class, extras);
    }
}
