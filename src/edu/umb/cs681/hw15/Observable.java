package edu.umb.cs681.hw15;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Observable<T> {
    private LinkedList<Observer<T>> observers = new LinkedList<>();
    private ReentrantLock lockObs = new ReentrantLock();

    public void addObserver(Observer<T> o) {
        lockObs.lock();
        try {
            observers.add(o);
        } finally {
            lockObs.unlock();
        }
    }

    public void clearObservers() {
        lockObs.lock();
        try {
            observers.clear();
        } finally {
            lockObs.unlock();
        }

    }

    public List<Observer<T>> getObservers() {
        lockObs.lock();
        try {
            return observers;
        } finally {
            lockObs.unlock();
        }
    }

    public int countObservers() {
        lockObs.lock();
        try {
            return observers.size();
        } finally {
            lockObs.unlock();
        }

    }

    public void removeObserver(Observer<T> o) {
        lockObs.lock();
        try {
            observers.remove(o);
        } finally {
            lockObs.unlock();
        }
    }

    public void notifyObservers(T event) {
        LinkedList<Observer<T>> observersLocal = new LinkedList<>();
        lockObs.lock();
        try {
            observersLocal = observers;
        } finally {
            lockObs.unlock();
        }

        observersLocal.forEach((observer) -> {
            observer.update(this, event);
        });
    }

}
