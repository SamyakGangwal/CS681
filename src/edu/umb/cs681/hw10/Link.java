package edu.umb.cs681.hw10;

import java.util.concurrent.locks.ReentrantLock;

public class Link extends FSElement {
    private FSElement target;
    private static ReentrantLock lock = new ReentrantLock();

    public Link(Directory parent, String name, FSElement target) {
        super(parent, name, 0);
        this.target = target;
    }

    public FSElement getTarget() {
        lock.lock();
        try {
            return target;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isLink() {
        return true;

    }

    @Override
    public boolean isDirectory() {
        return false;
    }
}
