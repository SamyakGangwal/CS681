package edu.umb.cs681.hw10;

import java.util.LinkedList;

public class Directory extends FSElement {
    LinkedList<FSElement> children = new LinkedList<>();

    public Directory(Directory parent, String name) {
        super(parent, name, 0);
    }

    public LinkedList<FSElement> getChildren() {
        lock.lock();
        try {
            return children;
        } finally {
            lock.unlock();
        }

    }

    public void appendChild(FSElement child) {
        lock.lock();
        try {
            this.children.add(child);
        } finally {
            lock.unlock();
        }

    }

    public int countChildren() {
        lock.lock();
        try {
            return children.size();
        } finally {
            lock.unlock();
        }

    }

    public LinkedList<Directory> getSubDirectories() {
        lock.lock();
        try {
            LinkedList<Directory> subDirectories = new LinkedList<>();

            for (int i = 0; i < children.size(); i++) {
                if (children.get(i).isDirectory()) {
                    subDirectories.add((Directory) children.get(i));
                }
            }

            return subDirectories;
        } finally {
            lock.unlock();
        }

    }

    public LinkedList<File> getFiles() {
        lock.lock();
        try {
            LinkedList<File> files = new LinkedList<>();

            for (int i = 0; i < this.children.size(); i++) {
                if (!children.get(i).isDirectory() && !children.get(i).isLink()) {
                    files.add((File) children.get(i));
                }
            }
            return files;
        } finally {
            lock.unlock();
        }
    }

    public LinkedList<Link> getLinks() {
        lock.lock();
        try {
            LinkedList<Link> link = new LinkedList<>();

            for (int i = 0; i < this.children.size(); i++) {
                if (children.get(i).isLink()) {
                    link.add((Link) children.get(i));
                }
            }

            return link;
        } finally {
            lock.unlock();
        }
    }

    public int getTotalSize() {
        lock.lock();
        try {
            int totalSize = 0;

            for (int i = 0; i < this.children.size(); i++) {
                if (!this.children.get(i).isDirectory()) {
                    totalSize += this.children.get(i).getSize();
                } else {
                    Directory dir = (Directory) this.children.get(i);
                    totalSize += dir.getTotalSize();
                }
            }

            return totalSize;
        } finally {
            lock.unlock();
        }

    }

    @Override
    public boolean isLink() {
        return false;

    }

    @Override
    public boolean isDirectory() {
        return true;
    }

}
