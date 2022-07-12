package application;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Board {

	public enum Direction {
		VERTICAL, HORIZONTAL;
	}

	final int BOARD_SIZE = 9;
	final String P1PAWN = "RED";
	final String P2PAWN = "GREEN";
	Grid p1 = new Grid("e9");
	Grid p2 = new Grid("e1");

	List<LinkedList<Grid>> adjlist = new LinkedList<LinkedList<Grid>>();
	LinkedList<Wall> walls = new LinkedList<Wall>();
	List<String> record = new LinkedList<String>();

	int p1Wall = 0;
	int p2Wall = 0;
	int turn = 0;

	public Board() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				Grid g = new Grid(i, j);
				adjlist.add(g.inRange(1));
			}
		}
	}

	public Board(Board b) {
		adjlist.addAll(b.adjlist);
		p1 = new Grid(b.p1);
		p2 = new Grid(b.p2);
		walls.addAll(b.walls);
		p1Wall = b.p1Wall;
		p2Wall = b.p2Wall;
		turn = b.turn;
	}

	public String info() {
		return "Turn : " + turn + " | Player to Move: " + (currentPlayer() == 0 ? P1PAWN : P2PAWN)
				+ "\nWalls Remaining: " + (10 - currentPlayerWalls()) + "\n";
	}

	public String win() {
		return "Game End. The winner is player " + winner() + "!";
	}

	public boolean isFinished() {
		return p1.getRow() == 0 || p2.getRow() == 8;
	}

	public String winner() {
		if (p1.getRow() == 0)
			return P1PAWN;
		else
			return P2PAWN;
	}

	public int turn() {
		return turn;
	}

	public int currentPlayer() {
		return turn % 2;
	}

	public Grid currentPlayerPosition() {
		if (currentPlayer() == 0)
			return p1;
		else
			return p2;
	}

	public Grid otherPlayerPosition() {
		if (currentPlayerPosition().equals(p1))
			return p2;
		else
			return p1;
	}

	public int currentPlayerWalls() {
		if (currentPlayer() == 0)
			return p1Wall;
		else
			return p2Wall;
	}

	public boolean otherPlayerOccupy(Grid g) {
		return p1.equals(g) || p2.equals(g);
	}

	public void addEdge(Grid a, Grid b) {
		LinkedList<Grid> adj_a = new LinkedList<Grid>();
		LinkedList<Grid> adj_b = new LinkedList<Grid>();
		adj_a.addAll(adjlist.get(a.hashCode()));
		adj_b.addAll(adjlist.get(b.hashCode()));
		adj_a.add(b);
		adj_b.add(a);
		adjlist.set(a.hashCode(), adj_a);
		adjlist.set(b.hashCode(), adj_b);
	}

	public void removeEdge(Grid a, Grid b) {
		LinkedList<Grid> adj_a = new LinkedList<Grid>();
		LinkedList<Grid> adj_b = new LinkedList<Grid>();
		adj_a.addAll(adjlist.get(a.hashCode()));
		adj_b.addAll(adjlist.get(b.hashCode()));
		adj_a.remove(b);
		adj_b.remove(a);
		adjlist.set(a.hashCode(), adj_a);
		adjlist.set(b.hashCode(), adj_b);
	}

	protected boolean hasPathToGoal() {
		return !(shortestPathToRow(p1, 0).isEmpty() || shortestPathToRow(p2, 8).isEmpty());
	}

	protected List<Grid> shortestPathToRow(Grid src, int row) {
		Queue<Grid> q = new LinkedList<Grid>();
		List<Grid> path = new LinkedList<Grid>();
		HashMap<Grid, Grid> parentNode = new HashMap<Grid, Grid>();

		q.add(src);
		parentNode.put(src, null);
		while (!q.isEmpty()) {
			Grid t = q.poll();
			if (t.getRow() == row) {
				while (!t.equals(src)) {
					path.add(t);
					t = parentNode.get(t);
				}
				Collections.reverse(path);
				return path;
			}
			for (Grid b : gridAvailableMove(t)) {
				if (!parentNode.containsKey(b)) {
					parentNode.put(b, t);
					q.add(b);
				}
			}
		}
		return path;
	}

	public int directDist(Grid src, int row) {
		return Math.abs(row - src.getRow());
	}

	public Grid checkSide(Grid a, Grid b) {
		// a = currentPlayer, b = otherPlayer
		if ((a.getCol() == b.getCol()) && (a.getRow() - b.getRow() == 1)) {
			// b on left
			return new Grid(b.getRow() - 1, b.getCol());
		} else if ((a.getCol() == b.getCol()) && (a.getRow() - b.getRow() == -1)) {
			// b on right
			return new Grid(b.getRow() + 1, b.getCol());
		} else if ((a.getRow() == b.getRow()) && (a.getCol() - b.getCol() == 1)) {
			// b on up
			return new Grid(b.getRow(), b.getCol() - 1);
		} else if ((a.getRow() == b.getRow()) && (a.getCol() - b.getCol() == -1)) {
			// b on right
			return new Grid(b.getRow(), b.getCol() + 1);
		} else
			return new Grid(0, 0);
	}

	protected List<Grid> currentPlayerAvailableMove() {
		List<Grid> available = new LinkedList<Grid>();
		List<Grid> temp = new LinkedList<Grid>();
		for (Grid g : adjlist.get(currentPlayerPosition().hashCode())) {
			if (otherPlayerOccupy(g)) {
				Grid t = checkSide(currentPlayerPosition(), otherPlayerPosition());
				if (!adjlist.get(otherPlayerPosition().hashCode()).contains(t)) {
					temp.addAll(adjlist.get(otherPlayerPosition().hashCode()));
				} else {
					for (Grid g2 : adjlist.get(otherPlayerPosition().hashCode())) {
						if (currentPlayerPosition().inRange(2).contains(g2)) {
							temp.add(g2);
						}
					}
				}
			} else {
				available.add(g);
			}
		}
		available.addAll(temp);
		return available;
	}

	protected List<Grid> gridAvailableMove(Grid g) {
		List<Grid> available = new LinkedList<Grid>();
		List<Grid> temp = new LinkedList<Grid>();
		for (Grid gg : adjlist.get(g.hashCode())) {
			if (otherPlayerOccupy(gg)) {
				Grid t = checkSide(g, gg);
				if (!adjlist.get(gg.hashCode()).contains(t)) {
					temp.addAll(adjlist.get(gg.hashCode()));
				} else {
					for (Grid g2 : adjlist.get(gg.hashCode())) {
						if (gg.inRange(2).contains(g2)) {
							temp.add(g2);
						}
					}
				}
			} else {
				available.add(gg);
			}
		}
		available.addAll(temp);
		return available;
	}

	protected boolean isValidSyntax(String move) {
		Pattern p = Pattern.compile("[a-i][1-9][hv]?");
		Matcher m = p.matcher(move);
		return m.matches();
	}

	protected boolean isWallPlaceInput(String move) {
		return isValidSyntax(move) && move.length() == 3;
	}

	protected boolean isMoveInput(String move) {
		return isValidSyntax(move) && move.length() == 2;
	}

	public boolean isValidNextMove(Grid next) {
		if ((next.equals(currentPlayerPosition()) || next.equals(otherPlayerPosition()))) {
			return false;
		} else if (currentPlayerAvailableMove().contains(next)) {
			return true;
		}
		return false;
	}

	public boolean isValidWallPlace(Wall w) {
		if (currentPlayerWalls() >= 10) {
			return false;
		}

		if (w.wall.getCol() >= 8 || w.wall.getRow() >= 8) {
			return false;
		}

		if (w.dir == Direction.HORIZONTAL) {
			if (walls.contains(w) || walls.contains(w.neighbor(0, 0, Direction.VERTICAL))
					|| walls.contains(w.neighbor(0, -1, Direction.HORIZONTAL))
					|| walls.contains(w.neighbor(0, 1, Direction.HORIZONTAL))) {
				return false;
			}
		} else {
			if (walls.contains(w) || walls.contains(w.neighbor(0, 0, Direction.HORIZONTAL))
					|| walls.contains(w.neighbor(-1, 0, Direction.VERTICAL))
					|| walls.contains(w.neighbor(1, 0, Direction.VERTICAL))) {
				return false;
			}
		}

		if (w.dir == Direction.HORIZONTAL) {
			removeEdge(w.wall, w.wall.subBoard(1, 0));
			removeEdge(w.wall.subBoard(0, 1), w.wall.subBoard(1, 1));
		} else {
			removeEdge(w.wall, w.wall.subBoard(0, 1));
			removeEdge(w.wall.subBoard(1, 0), w.wall.subBoard(1, 1));
		}

		boolean hasPath = hasPathToGoal();
		if (w.dir == Direction.HORIZONTAL) {
			addEdge(w.wall, w.wall.subBoard(1, 0));
			addEdge(w.wall.subBoard(0, 1), w.wall.subBoard(1, 1));
		} else {
			addEdge(w.wall, w.wall.subBoard(0, 1));
			addEdge(w.wall.subBoard(1, 0), w.wall.subBoard(1, 1));
		}
		return hasPath;
	}

	protected void updateGrid(Grid g) {
		if (currentPlayer() == 0) {
			p1 = g;
		} else {
			p2 = g;
		}
	}

	protected void placeWall(Wall wall) {
		if (currentPlayer() == 0) {
			p1Wall++;
		} else {
			p2Wall++;
		}
		walls.add(wall);
		if (wall.getDir() == Direction.HORIZONTAL) {
			removeEdge(wall.wall, wall.wall.subBoard(1, 0));
			removeEdge(wall.wall.subBoard(0, 1), wall.wall.subBoard(1, 1));
		} else {
			removeEdge(wall.wall, wall.wall.subBoard(0, 1));
			removeEdge(wall.wall.subBoard(1, 0), wall.wall.subBoard(1, 1));
		}
	}

	public boolean move(String move) {
		boolean valid = true;
		valid &= !isFinished();
		if (isWallPlaceInput(move)) {
			Wall wall = new Wall(move);
			valid &= isValidWallPlace(wall);
			if (valid) {
				placeWall(wall);
			}
		} else if (isMoveInput(move)) {
			Grid sq = new Grid(move);
			valid &= isValidNextMove(sq);
			if (valid) {
				updateGrid(sq);
			}
		} else {
			valid = false;
		}
		if (valid) {
			turn++;
			record.add(move);
		}
//		record.add(move);
		return valid;
	}

	public List<String> validChoices() {
		List<String> validChoices = new LinkedList<String>();
		if (currentPlayerWalls() == 10) {
			for (Grid g : currentPlayerAvailableMove()) {
				if (isValidNextMove(g)) {
					validChoices.add(g.toString());
				}
			}
		} else {
			for (Grid g : currentPlayerAvailableMove()) {
				if (isValidNextMove(g)) {
					validChoices.add(g.toString());
				}
			}
			for (int i = 0; i < BOARD_SIZE; i++) {
				for (int j = 0; j < BOARD_SIZE; j++) {
					Grid gw = new Grid(i, j);
					for (Direction d : Direction.values()) {
						Wall wall = new Wall(gw, d);
						if (isValidWallPlace(wall)) {
							validChoices.add(wall.toString());
						}
					}
				}
			}
		}

		return validChoices;
	}
}
