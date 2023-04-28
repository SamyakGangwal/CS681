package edu.umb.cs681.hw10;

import java.util.concurrent.locks.ReentrantLock;

public class File extends FSElement {
    private static ReentrantLock lock = new ReentrantLock();

    public File(Directory parent, String name, int size) {
        super(parent, name, size);
    }

    @Override
    public boolean isDirectory() {
        lock.lock();
        try {
            return false;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isLink() {
        lock.lock();
        try {
            return false;
        } finally {
            lock.unlock();
        }
    }

}
