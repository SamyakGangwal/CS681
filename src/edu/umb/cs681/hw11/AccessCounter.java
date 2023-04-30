package edu.umb.cs681.hw11;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class AccessCounter {
    private static AccessCounter ac = null;
    private HashMap<Path, Integer> pathMap = new HashMap<>();

    private ReentrantLock lock = new ReentrantLock();
    private static ReentrantLock lock2 = new ReentrantLock();

    private AccessCounter() {

    }

    public static AccessCounter getAc() {
        lock2.lock();
        try {
            if (ac == null) {
                ac = new AccessCounter();
            }
        } finally {
            lock2.unlock();
        }
        return ac;
    }

    public void increment(Path path) {
        lock.lock();
        try {
            if (this.pathMap.containsKey(path)) {
                this.pathMap.merge(path, 1, Integer::sum);
            } else {
                this.pathMap.put(path, 1);
            }
        } finally {
            lock.unlock();
        }
    }

    public int getCount(Path path) {
        lock.lock();
        try {
            if (this.pathMap.containsKey(path)) {
                return this.pathMap.get(path);
            } else {
                return 0;
            }
        } finally {
            lock.unlock();
        }
    }

    public Map<Path, Integer> getPathMap() {
        return pathMap;
    }
}
