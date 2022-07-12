package application;

import application.Board.Direction;

public class Wall {

	// wall start from northwest, 1 of 4
	Grid wall = new Grid();
	Direction dir = null;

	public Wall(Grid wall, Direction dir) {
		this.wall = wall;
		this.dir = dir;

	}

	public Wall(String s) {
		if (s.length() > 2) {
			this.wall = new Grid(s.substring(0, 2));
			this.dir = s.charAt(2) == 'h' ? Direction.HORIZONTAL : Direction.VERTICAL;
		}
	}

	public Wall neighbor(int row, int col, Direction direction) {
		return new Wall(this.wall.subBoard(row, col), direction);
	}

	public Grid getWall() {
		return wall;
	}

	public Direction getDir() {
		return dir;
	}

	@Override
	public String toString() {
		return wall.toString() + dir.name().toLowerCase().charAt(0);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Wall) {
			Wall w = (Wall) obj;
			return (w.wall.getRow() == wall.getRow() && w.wall.getCol() == wall.getCol() && w.dir == dir);
		}
		return false;
	}
}
