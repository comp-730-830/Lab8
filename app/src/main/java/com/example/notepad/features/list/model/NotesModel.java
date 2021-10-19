package com.example.notepad.features.list.model;

import com.example.notepad.data.Note;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class NotesModel {
    private String userId;
    private CollectionReference notesReference;

    public NotesModel() {
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        notesReference = FirebaseFirestore.getInstance().collection("notes");
    }

    public Query getNotes() {
        return notesReference.whereEqualTo("user_id", userId);
    }

    public Task<Void> deleteNote(Note note) {
        return notesReference.document(note.getId()).delete();
    }
}
