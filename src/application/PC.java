package application;

import java.util.LinkedList;
import java.util.List;

public class PC {

	String bestMove = new String();
	int depth, bestScore = 0;
	Grid temp = new Grid();
	List<String> choices = new LinkedList<String>();
	List<Grid> choices2 = new LinkedList<Grid>();

	public PC() {
		depth = 3;
	}

	public PC(int depth) {
		this.depth = depth;
	}

	public String getMove(Board b) {
		choices = b.validChoices();
		int val;
		int alpha = Integer.MIN_VALUE, beta = Integer.MAX_VALUE;
		bestMove = choices.get(0);
		for (String moves : choices) {
			Board child = new Board(b);
			child.move(moves);
			if (b.p2Wall == 10) {
				val = minimaxAlphaBeta(child, depth + 1, alpha, beta, false);
			} else {
				val = minimaxAlphaBeta(child, depth, alpha, beta, false);
			}
			if (alpha < val) {
				alpha = val;
				bestMove = moves;
			}
			if (beta <= alpha) {
				break;
			}
		}

		choices2 = b.currentPlayerAvailableMove();
		if (b.isMoveInput(bestMove)) {
			temp = new Grid(bestMove);
			int score1 = b.shortestPathToRow(temp, 8).size();
			int score2 = 0;
			for (Grid a : choices2) {
				score2 = b.shortestPathToRow(a, 8).size();
				if (score2 < score1) {
					String te = a.toString();
					bestMove = te;
					score1 = score2;
//					System.out.println("b: " + te);
				}
			}
		}
//		System.out.println("last :" + bestMove);
		return bestMove;
	}

	public int minimaxAlphaBeta(Board node, int depth, int alpha, int beta, boolean maxPlayer) {
		if (depth == 0 || node.isFinished()) {
			return heuristic(node);
		}
		if (maxPlayer) {
			for (String move : node.validChoices()) {
				Board child = new Board(node);
				child.move(move);
//				System.out.println("alpha :" + move);
				alpha = Math.max(alpha, minimaxAlphaBeta(child, depth - 1, alpha, beta, false));
				if (beta <= alpha) {
					break;
				}
			}
			return alpha;
		} else {
			for (String move : node.validChoices()) {
				Board child = new Board(node);
				child.move(move);
//				System.out.println("beta :" + move);
				beta = Math.min(beta, minimaxAlphaBeta(child, depth - 1, alpha, beta, true));
				if (beta <= alpha) {
					break;
				}
			}
			return beta;
		}
	}

	public int heuristic(Board b) {
		int p1Score = 0, p2Score = 0, totalScore = 0;
		int t1 = b.shortestPathToRow(b.p1, 0).size(), t2 = b.shortestPathToRow(b.p2, 8).size();
		int w1 = 0, w2 = 0, s1 = 0, s2 = 0;
		int d1 = b.directDist(b.p1, 0), d2 = b.directDist(b.p2, 8);

		if (b.directDist(b.p1, 0) < 5) {
			if (b.shortestPathToRow(b.p1, 0).size() < 3) {
				s1 = 300;
			} else if (b.shortestPathToRow(b.p1, 0).size() > 2 && b.shortestPathToRow(b.p1, 0).size() < 5) {
				s1 = 200;
			} else if (b.shortestPathToRow(b.p1, 0).size() > 4 && b.shortestPathToRow(b.p1, 0).size() < 7) {
				s1 = 100;
			} else if (b.shortestPathToRow(b.p1, 0).size() > 6 && b.shortestPathToRow(b.p1, 0).size() < 9) {
				s1 = 50;
			} else if (b.shortestPathToRow(b.p1, 0).size() > 8) {
				s1 = -80;
			} else
				s1 = 0;
		} else if (b.directDist(b.p1, 0) > 4) {
			if (b.shortestPathToRow(b.p1, 0).size() > 10 && b.shortestPathToRow(b.p1, 0).size() < 16) {
				s1 = -100;
			} else if (b.shortestPathToRow(b.p1, 0).size() > 15) {
				s1 = -200;
			} else
				s1 = 0;
		} else {
			s1 = 0;
		}

		if (b.directDist(b.p2, 8) < 5) {
			if (b.shortestPathToRow(b.p2, 8).size() < 3) {
				s2 = 300;
			}

			else if (b.shortestPathToRow(b.p2, 8).size() > 2 && b.shortestPathToRow(b.p2, 8).size() < 5) {
				s2 = 200;
			} else if (b.shortestPathToRow(b.p2, 8).size() > 4 && b.shortestPathToRow(b.p2, 8).size() < 7) {
				s2 = 100;
			} else if (b.shortestPathToRow(b.p2, 8).size() > 6 && b.shortestPathToRow(b.p2, 8).size() < 9) {
				s2 = 50;
			} else if (b.shortestPathToRow(b.p2, 8).size() > 8) {
				s2 = -80;
			} else
				s2 = 0;
		} else if (b.directDist(b.p2, 8) > 4) {
			if (b.shortestPathToRow(b.p2, 8).size() > 10 && b.shortestPathToRow(b.p2, 8).size() < 16) {
				s2 = -100;
			} else if (b.shortestPathToRow(b.p2, 8).size() > 15) {
				s2 = -200;
			} else
				s2 = 0;
		} else {
			s2 = 0;
		}

		if (b.p2Wall == 10) {
			w1 = 2 * (10 - b.p1Wall);
		} else {
			w1 = 4 * (10 - b.p1Wall);
		}

		if (b.p1Wall == 10) {
			w2 = 2 * (10 - b.p2Wall);
		} else {
			w2 = 4 * (10 - b.p2Wall);
		}

		p1Score = (100 - t1) + s1 + w1;
		p2Score = (100 - t2) + s2 + w2;

		if (b.isFinished()) {
			if (b.winner() == b.P1PAWN) {
				totalScore = Integer.MIN_VALUE;
			} else if (b.winner() == b.P2PAWN) {
				totalScore = Integer.MAX_VALUE;
			}
		} else {
			totalScore = p2Score - p1Score;
		}
//		System.out.println("total :" + totalScore + " p1Score :" + p1Score + " p2SCore :" + p2Score);
//		System.out.println(" t1 :" + t1 + " t2 :" + t2 + " s1 :" + s1 + " s2 :" + s2);
//		System.out.println("w1 :" + w1 + " w2 :" + w2 + " d1 :" + d1 + " d2 :" + d2);
		return totalScore;
	}
}
