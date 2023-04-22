package edu.umb.cs681.hw07;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
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
	public void verifygetRootDirs() throws InterruptedException {

		Runnable fs_runnable1 = () -> assertSame(FileSystem.getFileSystem(), fs.getFileSystem());;
		Runnable fs_runnable2 = () -> assertSame(FileSystem.getFileSystem(), fs.getFileSystem());;
		Runnable fs_runnable3 = () -> assertSame(FileSystem.getFileSystem(), fs.getFileSystem());;

		Thread thread = new Thread(fs_runnable1);

		Thread thread1 = new Thread(fs_runnable2);

		Thread thread2 = new Thread(fs_runnable3);

		thread.start();
		thread1.start();
		thread2.start();

		thread.join();
		thread1.join();
		thread2.join();

	}

	@AfterAll
	public static void cleanUp() {
		fs.getRootDirs().clear();
	}

}
