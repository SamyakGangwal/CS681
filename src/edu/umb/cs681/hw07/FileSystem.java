package edu.umb.cs681.hw07;

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
		return this.rootDirs;
	}

	public void appendRootDir(Directory root) {
		this.rootDirs.add(root);
	}

	public static void main(String[] args) throws InterruptedException {
		FileSystem fs = Fixture.createFs();

		Runnable r1 = () -> {
			System.out.println("The root folder is for thread 1:");

			System.out.println(fs.getRootDirs().getFirst().getName());
		};

		Runnable r2 = () -> {
			System.out.println("The root folder is for thread 2:");

			System.out.println(fs.getRootDirs().getFirst().getName());
		};


		Thread thread1 = new Thread(r1);
		Thread thread2 = new Thread(r2);

		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();

		System.out.println("The root folder is:");

		System.out.println(fs.getRootDirs().getFirst().getName());
	}

}
