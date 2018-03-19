package com.flitey;

import java.util.ArrayList;
import java.util.List;

public class Snake {

	private Direction myDirection;
	private int mySpeed;
	private Point myHeadLocation = new Point(0, 0);
	private List<Point> myTail = new ArrayList<Point>();
	private int height;
	private int width;
	private int myBlockSize;
	private boolean myCollidedWithWall = false;

	public Snake(int width, int height, int blockSize) {
		this.width = width;
		this.height = height;
		this.myBlockSize = blockSize;
		this.myDirection = Direction.RIGHT;
	}

	public void snakeUpdate() {

		if (myTail.size() > 0) {
			myTail.remove(myTail.size() - 1);
			myTail.add(0, new Point(myHeadLocation.getX(), myHeadLocation.getY()));
		}

		switch (myDirection) {
		case UP:
			myHeadLocation.setY(myHeadLocation.getY() - myBlockSize);
			if (myHeadLocation.getY() < 0) {
				myCollidedWithWall = true;
				myHeadLocation.setY(0);
			}
			break;

		case DOWN:
			myHeadLocation.setY(myHeadLocation.getY() + myBlockSize);
			if (myHeadLocation.getY() >= height) {
				myCollidedWithWall = true;
				myHeadLocation.setY(height - myBlockSize);
			}
			break;

		case LEFT:
			myHeadLocation.setX(myHeadLocation.getX() - myBlockSize);
			if (myHeadLocation.getX() < 0) {
				myCollidedWithWall = true;
				myHeadLocation.setX(0);
			}
			break;

		case RIGHT:
			myHeadLocation.setX(myHeadLocation.getX() + myBlockSize);
			if (myHeadLocation.getX() >= width) {
				myCollidedWithWall = true;
				myHeadLocation.setX(width - myBlockSize);
			}
			break;

		default:
			break;
		}
	}

	public boolean collidedWithWall() {
		return myCollidedWithWall;
	}

	public boolean collidedWithTail() {
		boolean isCollision = false;

		for (Point tailSegment : myTail) {
			if (myHeadLocation.equals(tailSegment)) {
				isCollision = true;
				break;
			}
		}

		return isCollision;
	}

	public void addTailSegment() {
		myTail.add(0, new Point(myHeadLocation.getX(), myHeadLocation.getY()));
		System.out.println("Add tail segment");
	}

	public Direction getDirection() {
		return myDirection;
	}

	public void setDirection(Direction myDirection) {
		this.myDirection = myDirection;
	}

	public int getSpeed() {
		return mySpeed;
	}

	public void setSpeed(int speed) {
		this.mySpeed = speed;
	}

	public int getBlockSize() {
		return myBlockSize;
	}

	public void setBlockSize(int blockSize) {
		this.myBlockSize = blockSize;
	}

	public void setHeadLocation(int x, int y) {
		myHeadLocation.setX(x);
		myHeadLocation.setY(y);
	}

	public Point getHeadLocation() {
		return myHeadLocation;
	}

	public List<Point> getTail() {
		return myTail;
	}

}
