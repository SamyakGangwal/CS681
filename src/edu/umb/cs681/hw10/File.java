package edu.umb.cs681.hw10;

public class File extends FSElement {

    public File(Directory parent, String name, int size) {
        super(parent, name, size);
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public boolean isLink() {
        return false;
    }

}
