package com.example.notepad.features.list.viewmodel.state;

public class ErrorState implements NotesListState {
    Throwable error;

    public ErrorState(Throwable error) {
        this.error = error;
    }

    public Throwable getError() {
        return error;
    }
}
