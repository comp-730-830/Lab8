package com.example.notepad.observable;

public interface Observer<T> {
    void notify(T data);
}
