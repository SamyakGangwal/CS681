package edu.umb.cs681.hw07;

public class Fixture {
	public static FileSystem createFs() {
		FileSystem fs = FileSystem.getFileSystem();

		Directory root = new Directory(null, "root");

		fs.appendRootDir(root);

		Directory app = new Directory(root, "apps");

		Directory bin = new Directory(root, "bin");

		Directory home = new Directory(root, "home");

		Directory pictures = new Directory(home, "pictures");

		File a = new File(pictures, "a", 100);

		File b = new File(pictures, "b", 100);

		File c = new File(home, "c", 100);

		File x = new File(app, "x", 100);

		File y = new File(bin, "y", 100);
		
		Link d = new Link(root, "d", pictures);

		Link e = new Link(root, "e", x);

		

		return fs;
	}
}
