package com.example.notepad.features.edit.model;

import com.example.notepad.data.Note;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditNoteModel {
    private String userId;
    private CollectionReference notesReference;

    private Note note;

    public EditNoteModel(Note note) {
        this.note = note;
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        notesReference = FirebaseFirestore.getInstance().collection("notes");
    }

    public String getContent() {
        return note.getContent();
    }

    public void setContent(String content) {
        note.setContent(content);
    }

    public boolean isNew() {
        return note.getId().isEmpty();
    }

    public Task<DocumentReference> createNote() {
        Map<String, Object> fields = new HashMap<>();
        fields.put("content", note.getContent());
        fields.put("user_id", userId);
        fields.put("created_at", FieldValue.serverTimestamp());
        return notesReference.add(fields);
    }

    public Task<Void> updateNote() {
        Map<String, Object> fields = new HashMap<>();
        fields.put("content", note.getContent());
        return notesReference.document(note.getId()).update(fields);
    }
}
