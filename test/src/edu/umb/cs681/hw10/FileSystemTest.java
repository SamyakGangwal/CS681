package edu.umb.cs681.hw10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import java.util.ArrayList;
import java.util.LinkedList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileSystemTest {
    private static FileSystem fs;

    @BeforeAll
    public static void setUpFs() {
        fs = Fixture.createFs();
    }

    @Test
    public void FileSystemsOpsMultipleThreads() throws InterruptedException {
        Thread[] threads = new Thread[15];

        for (int i = 0; i < 15; i++) {
            final int idx = i;

            threads[idx] =  new Thread(() -> {
                Directory root = fs.getRootDirs().getFirst();
                Directory apps = root.getSubDirectories().getFirst();

                File f = new File(apps, Thread.currentThread().getName(), 100);

                Link l = new Link(root, "Link:" + Thread.currentThread().getName(), f);

            });
            threads[idx].start();

        }

        for (Thread t : threads) {
            t.interrupt();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println("THREAD INTERRUPTION DETECTED!");
            }
        }



        assertEquals(0,
                fs.getRootDirs().getFirst().getSubDirectories().getFirst().getLinks().size());
        assertEquals(16,
                fs.getRootDirs().getFirst().getSubDirectories().getFirst().getFiles().size());
    }

    @AfterAll
    public static void cleanUp() {
        fs.getRootDirs().clear();
    }
}
