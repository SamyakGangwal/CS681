package edu.umb.cs681.hw07;

import java.time.LocalDateTime;

public abstract class FSElement {
	private int size;
	private String name;
	private LocalDateTime creationTime;
	private Directory parent;

	public FSElement(Directory parent, String name, int size) {
		this.parent = parent;
		this.name = name;
		this.size = size;
		this.creationTime = LocalDateTime.now();

		if (this.parent != null) {
			parent.appendChild(this);
		}
	}

	public Directory getParent() {
		return parent;
	}

	public int getSize() {
		return size;
	}
	
	public String getName() {
		return name;
	}

	public LocalDateTime getCreationTime() {
		return creationTime;
	}


	public abstract boolean isDirectory();
}
