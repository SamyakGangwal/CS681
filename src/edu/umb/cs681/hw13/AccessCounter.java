package edu.umb.cs681.hw13;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AccessCounter {
    private static AccessCounter ac = null;
    private HashMap<Path, Integer> pathMap = new HashMap<>();

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static ReentrantReadWriteLock lock2 = new ReentrantReadWriteLock();

    private AccessCounter() {

    }

    public static AccessCounter getAc() {
        lock2.readLock().lock();
        try {
            if (ac == null) {
                ac = new AccessCounter();
            }
        } finally {
            lock2.readLock().unlock();
        }
        return ac;
    }

    public void increment(Path path) {
        lock.writeLock().lock();
        try {
            if (this.pathMap.containsKey(path)) {
                this.pathMap.merge(path, 1, Integer::sum);
            } else {
                this.pathMap.put(path, 1);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public int getCount(Path path) {
        lock.readLock().lock();
        try {
            if (this.pathMap.containsKey(path)) {
                return this.pathMap.get(path);
            } else {
                return 0;
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    public Map<Path, Integer> getPathMap() {
        return pathMap;
    }
}
