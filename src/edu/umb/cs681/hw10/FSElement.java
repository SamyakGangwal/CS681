package edu.umb.cs681.hw10;

import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantLock;

public abstract class FSElement {
	private int size;
	private String name;
	private LocalDateTime creationTime;
	private Directory parent;
	protected ReentrantLock lock = new ReentrantLock();

	public FSElement(Directory parent, String name, int size) {
		lock.lock();
		try {
			this.parent = parent;
			this.name = name;
			this.size = size;
			this.creationTime = LocalDateTime.now();

			if (this.parent != null) {
				parent.appendChild(this);
			}
		} finally {
			lock.unlock();
		}
	}

	public Directory getParent() {
		lock.lock();
		try {
			return parent;
		} finally {
			lock.unlock();
		}
	}

	public int getSize() {
		lock.lock();
		try {
			return size;
		} finally {
			lock.unlock();
		}
	}

	public String getName() {
		lock.lock();
		try {
			return this.name;
		} finally {
			lock.unlock();
		}

	}

	public LocalDateTime getCreationTime() {
		return creationTime;
	}


	public abstract boolean isDirectory();

	public abstract boolean isLink();
}
