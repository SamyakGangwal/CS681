package edu.umb.cs681.hw07;

public class Link extends FSElement{
	private FSElement target;

	public Link(Directory parent, String name, FSElement target) {
		super(parent, name, 0);
		this.target = target;
	}

	public FSElement getTarget() {
		return target;
	}

	@Override
	public boolean isDirectory() {
		// TODO Auto-generated method stub
		return false;
	}
}
