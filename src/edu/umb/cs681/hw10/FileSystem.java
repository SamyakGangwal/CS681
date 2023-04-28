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

    public static FileSystem 
    getFileSystem() {
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

        ArrayList<Thread> threads = new ArrayList<>(15);

        ArrayList<String> threadNames = new ArrayList<>();

        for (int i = 0;i < 15;i ++) {
            final int idx = i;

            threads.add(idx, new Thread(() -> {
                threadNames.add(Thread.currentThread().getName());
                Directory root = fs.getRootDirs().getFirst();
                Directory apps = root.getSubDirectories().getFirst();

                File f = new File(apps, Thread.currentThread().getName(), 100);
                 
                Link l = new Link(root, "Link:" + Thread.currentThread().getName(), f);
                
            }));
            threads.get(idx).start();

        }

        for (Thread t : threads) {
            t.interrupt();
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println("THREAD INTERRUPTION DETECTED!");
            }
        }

        LinkedList<File> elements = fs.getRootDirs().getFirst().getSubDirectories().getFirst().getFiles();

        System.out.println("FILES:\n");

        for (File e : elements) {
            System.out.println(e.getName());
        }

        LinkedList<Link> elements1 = fs.getRootDirs().getFirst().getSubDirectories().getFirst().getLinks();

        System.out.println("LINKS:\n");

        for (Link e : elements1) {
            System.out.println(e.getName());
        }

    }

}
