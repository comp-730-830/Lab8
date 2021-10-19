package com.example.notepad.observable;

import java.util.ArrayList;

public class Observable<T>  {
    protected final ArrayList<Observer<T>> observers = new ArrayList<>();
    private T latestValue = null;

    public Observable() {}

    public Observable(T defaultValue) {
        this.latestValue = defaultValue;
    }

    public void registerObserver(Observer<T> observer) {
        observers.add(observer);
        observer.notify(latestValue);
    }

    public void unregisterObserver(Observer<T> observer) {
        observers.remove(observer);
    }

    public void unregisterAll() {
        observers.clear();
    }

    public void newValue(T data) {
        for(Observer<T> observer : observers) {
            observer.notify(data);
        }
        latestValue = data;
    }

}
