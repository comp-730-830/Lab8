package com.example.notepad.data;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper class for Firebase FireStore and Auth
 */
public class Database {
    private static final Object LOCK = new Object();
    private static Database INSTANCE;

    private FirebaseAuth auth;
    private FirebaseFirestore database;
    private CollectionReference notesReference;

    private Database() {
        database = FirebaseFirestore.getInstance();
        notesReference = database.collection("notes");
        auth = FirebaseAuth.getInstance();
    }

    public static Database getInstance() {
        synchronized (LOCK) {
            if (INSTANCE == null) {
                INSTANCE = new Database();
            }
            return INSTANCE;
        }
    }

    public Query getNotes() {
        return notesReference.whereEqualTo("user_id", getCurrentUserId());
    }

    public String getCurrentUserId() {
        return auth.getCurrentUser().getUid();
    }

    public Task<AuthResult> signIn(String email, String password) {
        return auth.signInWithEmailAndPassword(email, password);
    }

    public boolean isUserSignedIn() {
        return auth.getCurrentUser() != null;
    }

    public Task<DocumentReference> createNote(Note note) {
        Map<String, Object> fields = new HashMap<>();
        fields.put("content", note.getContent());
        fields.put("user_id", getCurrentUserId());
        fields.put("created_at", FieldValue.serverTimestamp());
        return notesReference.add(fields);
    }

    public Task<Void> updateNote(Note note) {
        Map<String, Object> fields = new HashMap<>();
        fields.put("content", note.getContent());
        return notesReference.document(note.getId()).update(fields);
    }

    public Task<Void> deleteNote(Note note) {
        return notesReference.document(note.getId()).delete();
    }
}
