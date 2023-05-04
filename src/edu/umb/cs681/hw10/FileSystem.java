package edu.umb.cs681.hw10;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class FileSystem {
    private LinkedList<Directory> rootDirs = new LinkedList<>();
    private static ReentrantLock lock = new ReentrantLock();

    public FileSystem() {

    }

    private static FileSystem fsInstance = null;

    public static FileSystem getFileSystem() {
        lock.lock();
        try {
            if (fsInstance == null) {
                fsInstance = new FileSystem();
            }
        } finally {
            lock.unlock();
        }

        return fsInstance;
    }

    public LinkedList<Directory> getRootDirs() {
        lock.lock();
        try {
            return this.rootDirs;
        } finally {
            lock.unlock();
        }
    }

    public void appendRootDir(Directory root) {
        lock.lock();
        try {
            this.rootDirs.add(root);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        FileSystem fs = Fixture.createFs();
        FileSystemRunnable fsr = new FileSystemRunnable(fs);
        ArrayList<Thread> threads = new ArrayList<>(15);

        for (int i = 0; i < 15; i++) {
            final int idx = i;

            threads.add(idx, new Thread(fsr));
            threads.get(idx).start();

        }

        for (int i = 0; i < 15; i++) {
            fsr.setDone();
            threads.get(i).interrupt();
            try {
                threads.get(i).join();
            } catch (InterruptedException e) {
                System.out.println("THREAD INTERRUPTION DETECTED!");
            }
        }

    }

}
