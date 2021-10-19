package com.example.notepad.data;

import com.google.firebase.firestore.DocumentId;

import java.io.Serializable;

public class Note implements Serializable {
    @DocumentId
    private String id;
    private String content;

    public Note() {
        id = "";
        content = "";
    }

    public Note(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
