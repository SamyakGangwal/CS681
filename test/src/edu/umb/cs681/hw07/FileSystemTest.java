package edu.umb.cs681.hw07;

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
		FileSystem[] fsList = new FileSystem[10];
		Thread[] threads = new Thread[10];

		for (int i = 0;i < 10;i ++) {
			final int index = i;
			threads[index] = new Thread(
				() -> {
					fsList[index] = FileSystem.getFileSystem();
				}
			);
			threads[i].start();
		}

		for (int i = 0;i < 10;i ++) {
			threads[i].join();
			assertSame(FileSystem.getFileSystem(), fsList[i].getFileSystem());
		}
	}

	@AfterAll
	public static void cleanUp() {
		fs.getRootDirs().clear();
	}

}
