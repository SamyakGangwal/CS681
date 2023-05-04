package edu.umb.cs681.hw10;

import java.util.concurrent.atomic.AtomicBoolean;

public class FileSystemRunnable implements Runnable {
    FileSystem fs;
    AtomicBoolean done = new AtomicBoolean(false);

    public void setDone() {
        this.done = new AtomicBoolean(true);
    }

    public FileSystemRunnable(FileSystem fs) {
        this.fs = fs;
    }

    @Override
    public void run() {
        Directory root = fs.getRootDirs().getFirst();
        Directory apps = root.getSubDirectories().getFirst();

        for (int i = 0; i < 15; i++) {
            if (this.done.get()) {
                System.out.println("STOPPED!");
                break;
            }
            File f = new File(apps, Thread.currentThread().getName() + "-" + i, 100);

            Link l = new Link(root, "Link:" + Thread.currentThread().getName() + "-" + i, f);
        }

    }

}
