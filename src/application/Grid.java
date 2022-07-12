package application;

import java.util.LinkedList;

public class Grid {

	private int row = 0, col = 0;

	public Grid() {
	}

	public Grid(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public Grid(String g) {
		if (g.length() > 1) {
			this.col = g.charAt(0) - 'a';
			this.row = g.charAt(1) - '1';
		}
	}

	public Grid(Grid g) {
		this.row = g.getRow();
		this.col = g.getCol();
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public Grid subBoard(int row, int col) {
		return new Grid(this.row + row, this.col + col);
	}

	public LinkedList<Grid> inRange(int n) {
		LinkedList<Grid> near = new LinkedList<Grid>();
		// sample r = radius from the box coord 4 direction
		for (int d = -n; d < n + 1; d++) {
			if (d != 0) {
				if (row + d >= 0 && row + d < 9) {
					near.add(new Grid(row + d, col));
				}
				if (col + d >= 0 && col + d < 9) {
					near.add(new Grid(row, col + d));
				}
			}
		}
		return near;

	}

	@Override
	public String toString() {
		char row = '1';
		char col = 'a';
		row += this.row;
		col += this.col;
		return "" + col + row;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Grid) {
			Grid g = (Grid) obj;
			return (g.row == row && g.col == col);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return 9 * row + col;
	}
}
