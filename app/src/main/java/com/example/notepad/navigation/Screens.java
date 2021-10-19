package com.example.notepad.navigation;

import android.os.Bundle;

import com.example.notepad.data.Note;
import com.example.notepad.edit.EditNoteActivity;
import com.example.notepad.list.NotesListActivity;
import com.example.notepad.signin.SignInActivity;

public class Screens {
    // TODO add notes list screen

    public static Screen editNote(Note note) {
        Bundle extras = new Bundle();
        extras.putSerializable(EditNoteActivity.NOTE_EXTRA, note);
        return new Screen(EditNoteActivity.class, extras);
    }
}
