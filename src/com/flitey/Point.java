package com.flitey;

public class Point {

	private int myX;
	private int myY;

	public Point(int x, int y) {
		myX = x;
		myY = y;
	}

	public int getX() {
		return myX;
	}

	public void setX(int x) {
		this.myX = x;
	}

	public int getY() {
		return myY;
	}

	public void setY(int y) {
		this.myY = y;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (myX != other.myX)
			return false;
		if (myY != other.myY)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Point [x=" + myX + ", y=" + myY + "]";
	}
}