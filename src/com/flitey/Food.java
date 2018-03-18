package com.flitey;

public class Food {
	Point myLocation;

	public Food(int x, int y) {
		myLocation = new Point(x, y);
	}

	public Point getLocation() {
		return myLocation;
	}

	@Override
	public String toString() {
		return "Food [myLocation=" + myLocation + "]";
	}

}
