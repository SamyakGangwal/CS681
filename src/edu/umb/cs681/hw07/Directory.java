package edu.umb.cs681.hw07;

import java.util.LinkedList;

public class Directory extends FSElement {
	LinkedList<FSElement> children = new LinkedList<>();

	public Directory(Directory parent, String name) {
		super(parent, name, 0);
	}

	public LinkedList<FSElement> getChildren() {
		return children;
	}

	public void appendChild(FSElement child) {
		this.children.add(child);
	}

	public int countChildren() {
		return children.size();
	}

	public LinkedList<Directory> getSubDirectories() {
		LinkedList<Directory> subDirectories = new LinkedList<>();

		for (int i = 0; i < children.size(); i++) {
			if (children.get(i).isDirectory()) {
				subDirectories.add((Directory) children.get(i));
			}
		}

		return subDirectories;
	}

	public LinkedList<File> getFiles() {
		LinkedList<File> files = new LinkedList<>();

		for (int i = 0;i < this.children.size();i ++) {
			if (!children.get(i).isDirectory()) {
				files.add((File)children.get(i));
			}
		}

		return files;
	}

	public int getTotalSize() {
		int totalSize = 0;

		for (int i = 0;i < this.children.size();i ++) {
			if (!this.children.get(i).isDirectory()) {
				totalSize += this.children.get(i).getSize();
			} else {
				Directory dir = (Directory)this.children.get(i);
				totalSize += dir.getTotalSize();
			}
		}

		return totalSize;
	}

	@Override
	public boolean isDirectory() {
		return true;
	}

}
