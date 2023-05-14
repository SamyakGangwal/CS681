package edu.umb.cs681.hw18;

import java.nio.file.Path;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class AccessCounter {
    private static AccessCounter ac = null;
    private ConcurrentHashMap<Path, AtomicInteger> pathMap = new ConcurrentHashMap<>();
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
        this.pathMap.computeIfAbsent(path, p -> new AtomicInteger(0)).incrementAndGet();
    }

    public int getCount(Path path) {
        return this.pathMap.getOrDefault(path, new AtomicInteger(0)).get();
    }

    public Map<Path, AtomicInteger> getPathMap() {
        return pathMap;
    }
}
