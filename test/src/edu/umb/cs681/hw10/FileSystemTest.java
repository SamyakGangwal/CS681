package edu.umb.cs681.hw10;

import static org.junit.Assert.assertTrue;
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

        LinkedList<File> elements =
                fs.getRootDirs().getFirst().getSubDirectories().getFirst().getFiles();

        System.out.println("FILES:\n");

        for (File e : elements) {
            System.out.println(e.getName());
        }

        LinkedList<Link> elements1 =
                fs.getRootDirs().getFirst().getSubDirectories().getFirst().getLinks();

        System.out.println("LINKS:\n");

        for (Link e : elements1) {
            System.out.println(e.getName());
        }

        for (int i = 0; i < 15; i++) {
            assertTrue(threads.get(i).isInterrupted());
        }
    }

    @AfterAll
    public static void cleanUp() {
        fs.getRootDirs().clear();
    }
}
