package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Controller {

	// player 1 circle - red
	@FXML
	protected Circle play1;

	// player 2 circle - green
	@FXML
	protected Circle play2;

	// vertical wall
	@FXML
	private Rectangle a1v, a2v, a3v, a4v, a5v, a6v, a7v, a8v, a9v, b1v, b2v, b3v, b4v, b5v, b6v, b7v, b8v, b9v, c1v,
			c2v, c3v, c4v, c5v, c6v, c7v, c8v, c9v, d1v, d2v, d3v, d4v, d5v, d6v, d7v, d8v, d9v, e1v, e2v, e3v, e4v,
			e5v, e6v, e7v, e8v, e9v, f1v, f2v, f3v, f4v, f5v, f6v, f7v, f8v, f9v, g1v, g2v, g3v, g4v, g5v, g6v, g7v,
			g8v, g9v, h1v, h2v, h3v, h4v, h5v, h6v, h7v, h8v, h9v;

	// small sq grid between wall
	@FXML
	private Rectangle a1c, a2c, a3c, a4c, a5c, a6c, a7c, a8c, b1c, b2c, b3c, b4c, b5c, b6c, b7c, b8c, c1c, c2c, c3c,
			c4c, c5c, c6c, c7c, c8c, d1c, d2c, d3c, d4c, d5c, d6c, d7c, d8c, e1c, e2c, e3c, e4c, e5c, e6c, e7c, e8c,
			f1c, f2c, f3c, f4c, f5c, f6c, f7c, f8c, g1c, g2c, g3c, g4c, g5c, g6c, g7c, g8c, h1c, h2c, h3c, h4c, h5c,
			h6c, h7c, h8c;

	// horizontal wall
	@FXML
	private Rectangle a1h, a2h, a3h, a4h, a5h, a6h, a7h, a8h, b1h, b2h, b3h, b4h, b5h, b6h, b7h, b8h, c1h, c2h, c3h,
			c4h, c5h, c6h, c7h, c8h, d1h, d2h, d3h, d4h, d5h, d6h, d7h, d8h, e1h, e2h, e3h, e4h, e5h, e6h, e7h, e8h,
			f1h, f2h, f3h, f4h, f5h, f6h, f7h, f8h, g1h, g2h, g3h, g4h, g5h, g6h, g7h, g8h, h1h, h2h, h3h, h4h, h5h,
			h6h, h7h, h8h, i1h, i2h, i3h, i4h, i5h, i6h, i7h, i8h;

	// human user input
	@FXML
	private TextArea ta;

	// field to show some output
	@FXML
	private TextField tf;

	@FXML
	private Button start, human, enter;

	Board b = new Board();
	PC p2 = new PC(2);
	String s = " ", info = " ", m = " ", src1 = " ", src2 = " ";
	int i = 0, ii = 0, iii = 0, iv = 0;
	char row1, row2, col1, col2;
	
	boolean test;

	public void start() {
		s = b.info();
		ta.setText(s);
		ta.setEditable(false);
		start.setDisable(true);
		enter.setDisable(true);
	}

	public void end() {
		if (b.isFinished()) {
			ta.setText("Game End. The winner is player " + b.winner() + "!");
//			System.out.println(b.adjlist);
//			System.out.println(b);
			enter.setDisable(true);
			human.setDisable(true);
//			write();
		}
	}
	
	public void write() {
		 try {
		      File fil = new File("record.txt");
		      FileWriter myWriter = new FileWriter("record.txt");
		      if (fil.createNewFile()) {
		        System.out.println("File created: " + fil.getName());
		        
		        myWriter.write("test" +b.record + " Winner: "+b.winner());
		        myWriter.close();
		        System.out.println("Successfully wrote to the file.");
		      } else {
		        System.out.println("File already exists.");
		        myWriter.append("test"+b.record+ " Winner: "+b.winner());
		        myWriter.close();
		        System.out.println("Successfully wrote to the file.");
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}

	public void movePawn(String src, String move, Circle c) {
		// src - 1 - current position
		// move - 2 - next position
		row1 = src.charAt(0);
		col1 = src.charAt(1);
		row2 = move.charAt(0);
		col2 = move.charAt(1);

		if (col1 == col2 && row1 != row2) {
			i = row1 - row2;
			if (i > 0) {
				for (int n = 0; n < i; n++) {
					left(c);
				}
			} else if (i < 0) {
				ii = Math.abs(i);
				for (int o = 0; o < ii; o++) {
					right(c);
				}
			}
		} else if (row2 == row1 && col1 != col2) {
			i = col1 - col2;
			if (i > 0) {
				for (int n = 0; n < i; n++) {
					up(c);
				}
			} else if (i < 0) {
				ii = Math.abs(i);
				for (int o = 0; o < ii; o++) {
					down(c);
				}
			}
		} else if ((col1 != col2) && (row1 != row2)) {
			i = row1 - row2;
			ii = col1 - col2;
			if (i > 0) {
				for (int n = 0; n < i; n++) {
					left(c);
				}
			} else if (i < 0) {
				iii = Math.abs(i);
				for (int o = 0; o < iii; o++) {
					right(c);
				}
			}
			if (ii > 0) {
				for (int p = 0; p < ii; p++) {
					up(c);
				}
			} else if (ii < 0) {
				iv = Math.abs(ii);
				for (int q = 0; q < iv; q++) {
					down(c);
				}
			}
		}

	}

	public void action() {
		test = start.isDisable();
		if(test) {
			ta.setEditable(false);
			src1 = b.currentPlayerPosition().toString();
			s = tf.getText();
			tf.clear();
			ta.setText(b.info());
			if (!b.move(s)) {
				ta.setText("invalid move" + "\n" + b.info());
				tf.clear();
//				System.out.println(b);
			} else {
				if (s.length() == 2) {
					movePawn(src1, s, play1);
				} else if (s.length() == 3) {
					setH(s);
					setV(s);
				}
				ta.setText(b.info());
				tf.clear();
//				System.out.println(b.adjlist);
//				System.out.println(b);
				human.setDisable(true);
				enter.setDisable(false);
				tf.setDisable(true);
				end();
			}
		}
		else {
			ta.setText("Press Start button");
			ta.setEditable(false);
		}
	}

	public void action2() {
		test = start.isDisable();
		if(test) {
			ta.setEditable(false);
			src2 = b.currentPlayerPosition().toString();
			m = p2.getMove(b);
			ta.setText(b.info());
			if (!b.move(m)) {
				ta.setText("invalid move" + "\n" + b.info());
				tf.clear();
//				System.out.println(m);
//				System.out.println(b.adjlist);
//				System.out.println(b);
			} else {
				if (m.length() == 2) {
					movePawn(src2, m, play2);
				} else if (m.length() == 3) {
					setH(m);
					setV(m);
				}
				ta.setText(b.info());
				tf.clear();
//				System.out.println(b.adjlist);
//				System.out.println(b);
				human.setDisable(false);
				enter.setDisable(true);
				tf.setDisable(false);
				end();
			}
		}
		else {
			ta.setText("Press Start button");
			ta.setEditable(false);
		}
	}

	public void up(Circle c) {
		c.setCenterY(c.getCenterY() - 60);
	}

	public void down(Circle c) {
		c.setCenterY(c.getCenterY() + 60);
	}

	public void left(Circle c) {
		c.setCenterX(c.getCenterX() - 60);
	}

	public void right(Circle c) {
		c.setCenterX(c.getCenterX() + 60);
	}

	public void setH(String s) {

		if (s.equals("a1h")) {
			a1h.setFill(Color.BLACK);
			a1c.setFill(Color.BLACK);
			b1h.setFill(Color.BLACK);
		} else if (s.equals("a2h")) {
			a2h.setFill(Color.BLACK);
			a2c.setFill(Color.BLACK);
			b2h.setFill(Color.BLACK);
		} else if (s.equals("a3h")) {
			a3h.setFill(Color.BLACK);
			a3c.setFill(Color.BLACK);
			b3h.setFill(Color.BLACK);
		} else if (s.equals("a4h")) {
			a4h.setFill(Color.BLACK);
			a4c.setFill(Color.BLACK);
			b4h.setFill(Color.BLACK);
		} else if (s.equals("a5h")) {
			a5h.setFill(Color.BLACK);
			a5c.setFill(Color.BLACK);
			b5h.setFill(Color.BLACK);
		} else if (s.equals("a6h")) {
			a6h.setFill(Color.BLACK);
			a6c.setFill(Color.BLACK);
			b6h.setFill(Color.BLACK);
		} else if (s.equals("a7h")) {
			a7h.setFill(Color.BLACK);
			a7c.setFill(Color.BLACK);
			b7h.setFill(Color.BLACK);
		} else if (s.equals("a8h")) {
			a8h.setFill(Color.BLACK);
			a8c.setFill(Color.BLACK);
			b8h.setFill(Color.BLACK);
		} else if (s.equals("b1h")) {
			b1h.setFill(Color.BLACK);
			b1c.setFill(Color.BLACK);
			c1h.setFill(Color.BLACK);
		} else if (s.equals("b2h")) {
			b2h.setFill(Color.BLACK);
			b2c.setFill(Color.BLACK);
			c2h.setFill(Color.BLACK);
		} else if (s.equals("b3h")) {
			b3h.setFill(Color.BLACK);
			b3c.setFill(Color.BLACK);
			c3h.setFill(Color.BLACK);
		} else if (s.equals("b4h")) {
			b4h.setFill(Color.BLACK);
			b4c.setFill(Color.BLACK);
			c4h.setFill(Color.BLACK);
		} else if (s.equals("b5h")) {
			b5h.setFill(Color.BLACK);
			b5c.setFill(Color.BLACK);
			c5h.setFill(Color.BLACK);
		} else if (s.equals("b6h")) {
			b6h.setFill(Color.BLACK);
			b6c.setFill(Color.BLACK);
			c6h.setFill(Color.BLACK);
		} else if (s.equals("b7h")) {
			b7h.setFill(Color.BLACK);
			b7c.setFill(Color.BLACK);
			c7h.setFill(Color.BLACK);
		} else if (s.equals("b8h")) {
			b8h.setFill(Color.BLACK);
			b8c.setFill(Color.BLACK);
			c8h.setFill(Color.BLACK);
		} else if (s.equals("c1h")) {
			c1h.setFill(Color.BLACK);
			c1c.setFill(Color.BLACK);
			d1h.setFill(Color.BLACK);
		} else if (s.equals("c2h")) {
			c2h.setFill(Color.BLACK);
			c2c.setFill(Color.BLACK);
			d2h.setFill(Color.BLACK);
		} else if (s.equals("c3h")) {
			c3h.setFill(Color.BLACK);
			c3c.setFill(Color.BLACK);
			d3h.setFill(Color.BLACK);
		} else if (s.equals("c4h")) {
			c4h.setFill(Color.BLACK);
			c4c.setFill(Color.BLACK);
			d4h.setFill(Color.BLACK);
		} else if (s.equals("c5h")) {
			c5h.setFill(Color.BLACK);
			c5c.setFill(Color.BLACK);
			d5h.setFill(Color.BLACK);
		} else if (s.equals("c6h")) {
			c6h.setFill(Color.BLACK);
			c6c.setFill(Color.BLACK);
			d6h.setFill(Color.BLACK);
		} else if (s.equals("c7h")) {
			c7h.setFill(Color.BLACK);
			c7c.setFill(Color.BLACK);
			d7h.setFill(Color.BLACK);
		} else if (s.equals("c8h")) {
			c8h.setFill(Color.BLACK);
			c8c.setFill(Color.BLACK);
			d8h.setFill(Color.BLACK);
		} else if (s.equals("d1h")) {
			d1h.setFill(Color.BLACK);
			d1c.setFill(Color.BLACK);
			e1h.setFill(Color.BLACK);
		} else if (s.equals("d2h")) {
			d2h.setFill(Color.BLACK);
			d2c.setFill(Color.BLACK);
			e2h.setFill(Color.BLACK);
		} else if (s.equals("d3h")) {
			d3h.setFill(Color.BLACK);
			d3c.setFill(Color.BLACK);
			e3h.setFill(Color.BLACK);
		} else if (s.equals("d4h")) {
			d4h.setFill(Color.BLACK);
			d4c.setFill(Color.BLACK);
			e4h.setFill(Color.BLACK);
		} else if (s.equals("d5h")) {
			d5h.setFill(Color.BLACK);
			d5c.setFill(Color.BLACK);
			e5h.setFill(Color.BLACK);
		} else if (s.equals("d6h")) {
			d6h.setFill(Color.BLACK);
			d6c.setFill(Color.BLACK);
			e6h.setFill(Color.BLACK);
		} else if (s.equals("d7h")) {
			d7h.setFill(Color.BLACK);
			d7c.setFill(Color.BLACK);
			e7h.setFill(Color.BLACK);
		} else if (s.equals("d8h")) {
			d8h.setFill(Color.BLACK);
			d8c.setFill(Color.BLACK);
			e8h.setFill(Color.BLACK);
		} else if (s.equals("e1h")) {
			e1h.setFill(Color.BLACK);
			e1c.setFill(Color.BLACK);
			f1h.setFill(Color.BLACK);
		} else if (s.equals("e2h")) {
			e2h.setFill(Color.BLACK);
			e2c.setFill(Color.BLACK);
			f2h.setFill(Color.BLACK);
		} else if (s.equals("e3h")) {
			e3h.setFill(Color.BLACK);
			e3c.setFill(Color.BLACK);
			f3h.setFill(Color.BLACK);
		} else if (s.equals("e4h")) {
			e4h.setFill(Color.BLACK);
			e4c.setFill(Color.BLACK);
			f4h.setFill(Color.BLACK);
		} else if (s.equals("e5h")) {
			e5h.setFill(Color.BLACK);
			e5c.setFill(Color.BLACK);
			f5h.setFill(Color.BLACK);
		} else if (s.equals("e6h")) {
			e6h.setFill(Color.BLACK);
			e6c.setFill(Color.BLACK);
			f6h.setFill(Color.BLACK);
		} else if (s.equals("e7h")) {
			e7h.setFill(Color.BLACK);
			e7c.setFill(Color.BLACK);
			f7h.setFill(Color.BLACK);
		} else if (s.equals("e8h")) {
			e8h.setFill(Color.BLACK);
			e8c.setFill(Color.BLACK);
			f8h.setFill(Color.BLACK);
		} else if (s.equals("f1h")) {
			f1h.setFill(Color.BLACK);
			f1c.setFill(Color.BLACK);
			g1h.setFill(Color.BLACK);
		} else if (s.equals("f2h")) {
			f2h.setFill(Color.BLACK);
			f2c.setFill(Color.BLACK);
			g2h.setFill(Color.BLACK);
		} else if (s.equals("f3h")) {
			f3h.setFill(Color.BLACK);
			f3c.setFill(Color.BLACK);
			g3h.setFill(Color.BLACK);
		} else if (s.equals("f4h")) {
			f4h.setFill(Color.BLACK);
			f4c.setFill(Color.BLACK);
			g4h.setFill(Color.BLACK);
		} else if (s.equals("f5h")) {
			f5h.setFill(Color.BLACK);
			f5c.setFill(Color.BLACK);
			g5h.setFill(Color.BLACK);
		} else if (s.equals("f6h")) {
			f6h.setFill(Color.BLACK);
			f6c.setFill(Color.BLACK);
			g6h.setFill(Color.BLACK);
		} else if (s.equals("f7h")) {
			f7h.setFill(Color.BLACK);
			f7c.setFill(Color.BLACK);
			g7h.setFill(Color.BLACK);
		} else if (s.equals("f8h")) {
			f8h.setFill(Color.BLACK);
			f8c.setFill(Color.BLACK);
			g8h.setFill(Color.BLACK);
		} else if (s.equals("g1h")) {
			g1h.setFill(Color.BLACK);
			g1c.setFill(Color.BLACK);
			h1h.setFill(Color.BLACK);
		} else if (s.equals("g2h")) {
			g2h.setFill(Color.BLACK);
			g2c.setFill(Color.BLACK);
			h2h.setFill(Color.BLACK);
		} else if (s.equals("g3h")) {
			g3h.setFill(Color.BLACK);
			g3c.setFill(Color.BLACK);
			h3h.setFill(Color.BLACK);
		} else if (s.equals("g4h")) {
			g4h.setFill(Color.BLACK);
			g4c.setFill(Color.BLACK);
			h4h.setFill(Color.BLACK);
		} else if (s.equals("g5h")) {
			g5h.setFill(Color.BLACK);
			g5c.setFill(Color.BLACK);
			h5h.setFill(Color.BLACK);
		} else if (s.equals("g6h")) {
			g6h.setFill(Color.BLACK);
			g6c.setFill(Color.BLACK);
			h6h.setFill(Color.BLACK);
		} else if (s.equals("g7h")) {
			g7h.setFill(Color.BLACK);
			g7c.setFill(Color.BLACK);
			h7h.setFill(Color.BLACK);
		} else if (s.equals("g8h")) {
			g8h.setFill(Color.BLACK);
			g8c.setFill(Color.BLACK);
			h8h.setFill(Color.BLACK);
		} else if (s.equals("h1h")) {
			h1h.setFill(Color.BLACK);
			h1c.setFill(Color.BLACK);
			i1h.setFill(Color.BLACK);
		} else if (s.equals("h2h")) {
			h2h.setFill(Color.BLACK);
			h2c.setFill(Color.BLACK);
			i2h.setFill(Color.BLACK);
		} else if (s.equals("h3h")) {
			h3h.setFill(Color.BLACK);
			h3c.setFill(Color.BLACK);
			i3h.setFill(Color.BLACK);
		} else if (s.equals("h4h")) {
			h4h.setFill(Color.BLACK);
			h4c.setFill(Color.BLACK);
			i4h.setFill(Color.BLACK);
		} else if (s.equals("h5h")) {
			h5h.setFill(Color.BLACK);
			h5c.setFill(Color.BLACK);
			i5h.setFill(Color.BLACK);
		} else if (s.equals("h6h")) {
			h6h.setFill(Color.BLACK);
			h6c.setFill(Color.BLACK);
			i6h.setFill(Color.BLACK);
		} else if (s.equals("h7h")) {
			h7h.setFill(Color.BLACK);
			h7c.setFill(Color.BLACK);
			i7h.setFill(Color.BLACK);
		} else if (s.equals("h8h")) {
			h8h.setFill(Color.BLACK);
			h8c.setFill(Color.BLACK);
			i8h.setFill(Color.BLACK);
		}
	}

	public void setV(String s) {

		if (s.equals("a1v")) {
			a1v.setFill(Color.BLACK);
			a1c.setFill(Color.BLACK);
			a2v.setFill(Color.BLACK);
		} else if (s.equals("a2v")) {
			a2v.setFill(Color.BLACK);
			a2c.setFill(Color.BLACK);
			a3v.setFill(Color.BLACK);
		} else if (s.equals("a3v")) {
			a3v.setFill(Color.BLACK);
			a3c.setFill(Color.BLACK);
			a4v.setFill(Color.BLACK);
		} else if (s.equals("a4v")) {
			a4v.setFill(Color.BLACK);
			a4c.setFill(Color.BLACK);
			a5v.setFill(Color.BLACK);
		} else if (s.equals("a5v")) {
			a5v.setFill(Color.BLACK);
			a5c.setFill(Color.BLACK);
			a6v.setFill(Color.BLACK);
		} else if (s.equals("a6v")) {
			a6v.setFill(Color.BLACK);
			a6c.setFill(Color.BLACK);
			a7v.setFill(Color.BLACK);
		} else if (s.equals("a7v")) {
			a7v.setFill(Color.BLACK);
			a7c.setFill(Color.BLACK);
			a8v.setFill(Color.BLACK);
		} else if (s.equals("a8v")) {
			a8v.setFill(Color.BLACK);
			a8c.setFill(Color.BLACK);
			a9v.setFill(Color.BLACK);
		} else if (s.equals("b1v")) {
			b1v.setFill(Color.BLACK);
			b1c.setFill(Color.BLACK);
			b2v.setFill(Color.BLACK);
		} else if (s.equals("b2v")) {
			b2v.setFill(Color.BLACK);
			b2c.setFill(Color.BLACK);
			b3v.setFill(Color.BLACK);
		} else if (s.equals("b3v")) {
			b3v.setFill(Color.BLACK);
			b3c.setFill(Color.BLACK);
			b4v.setFill(Color.BLACK);
		} else if (s.equals("b4v")) {
			b4v.setFill(Color.BLACK);
			b4c.setFill(Color.BLACK);
			b5v.setFill(Color.BLACK);
		} else if (s.equals("b5v")) {
			b5v.setFill(Color.BLACK);
			b5c.setFill(Color.BLACK);
			b6v.setFill(Color.BLACK);
		} else if (s.equals("b6v")) {
			b6v.setFill(Color.BLACK);
			b6c.setFill(Color.BLACK);
			b7v.setFill(Color.BLACK);
		} else if (s.equals("b7v")) {
			b7v.setFill(Color.BLACK);
			b7c.setFill(Color.BLACK);
			b8v.setFill(Color.BLACK);
		} else if (s.equals("b8v")) {
			b8v.setFill(Color.BLACK);
			b8c.setFill(Color.BLACK);
			b9v.setFill(Color.BLACK);
		} else if (s.equals("c1v")) {
			c1v.setFill(Color.BLACK);
			c1c.setFill(Color.BLACK);
			c2v.setFill(Color.BLACK);
		} else if (s.equals("c2v")) {
			c2v.setFill(Color.BLACK);
			c2c.setFill(Color.BLACK);
			c3v.setFill(Color.BLACK);
		} else if (s.equals("c3v")) {
			c3v.setFill(Color.BLACK);
			c3c.setFill(Color.BLACK);
			c4v.setFill(Color.BLACK);
		} else if (s.equals("c4v")) {
			c4v.setFill(Color.BLACK);
			c4c.setFill(Color.BLACK);
			c5v.setFill(Color.BLACK);
		} else if (s.equals("c5v")) {
			c5v.setFill(Color.BLACK);
			c5c.setFill(Color.BLACK);
			c6v.setFill(Color.BLACK);
		} else if (s.equals("c6v")) {
			c6v.setFill(Color.BLACK);
			c6c.setFill(Color.BLACK);
			c7v.setFill(Color.BLACK);
		} else if (s.equals("c7v")) {
			c7v.setFill(Color.BLACK);
			c7c.setFill(Color.BLACK);
			c8v.setFill(Color.BLACK);
		} else if (s.equals("c8v")) {
			c8v.setFill(Color.BLACK);
			c8c.setFill(Color.BLACK);
			c9v.setFill(Color.BLACK);
		} else if (s.equals("d1v")) {
			d1v.setFill(Color.BLACK);
			d1c.setFill(Color.BLACK);
			d2v.setFill(Color.BLACK);
		} else if (s.equals("d2v")) {
			d2v.setFill(Color.BLACK);
			d2c.setFill(Color.BLACK);
			d3v.setFill(Color.BLACK);
		} else if (s.equals("d3v")) {
			d3v.setFill(Color.BLACK);
			d3c.setFill(Color.BLACK);
			d4v.setFill(Color.BLACK);
		} else if (s.equals("d4v")) {
			d4v.setFill(Color.BLACK);
			d4c.setFill(Color.BLACK);
			d5v.setFill(Color.BLACK);
		} else if (s.equals("d5v")) {
			d5v.setFill(Color.BLACK);
			d5c.setFill(Color.BLACK);
			d6v.setFill(Color.BLACK);
		} else if (s.equals("d6v")) {
			d6v.setFill(Color.BLACK);
			d6c.setFill(Color.BLACK);
			d7v.setFill(Color.BLACK);
		} else if (s.equals("d7v")) {
			d7v.setFill(Color.BLACK);
			d7c.setFill(Color.BLACK);
			d8v.setFill(Color.BLACK);
		} else if (s.equals("d8v")) {
			d8v.setFill(Color.BLACK);
			d8c.setFill(Color.BLACK);
			d9v.setFill(Color.BLACK);
		} else if (s.equals("e1v")) {
			e1v.setFill(Color.BLACK);
			e1c.setFill(Color.BLACK);
			e2v.setFill(Color.BLACK);
		} else if (s.equals("e2v")) {
			e2v.setFill(Color.BLACK);
			e2c.setFill(Color.BLACK);
			e3v.setFill(Color.BLACK);
		} else if (s.equals("e3v")) {
			e3v.setFill(Color.BLACK);
			e3c.setFill(Color.BLACK);
			e4v.setFill(Color.BLACK);
		} else if (s.equals("e4v")) {
			e4v.setFill(Color.BLACK);
			e4c.setFill(Color.BLACK);
			e5v.setFill(Color.BLACK);
		} else if (s.equals("e5v")) {
			e5v.setFill(Color.BLACK);
			e5c.setFill(Color.BLACK);
			e6v.setFill(Color.BLACK);
		} else if (s.equals("e6v")) {
			e6v.setFill(Color.BLACK);
			e6c.setFill(Color.BLACK);
			e7v.setFill(Color.BLACK);
		} else if (s.equals("e7v")) {
			e7v.setFill(Color.BLACK);
			e7c.setFill(Color.BLACK);
			e8v.setFill(Color.BLACK);
		} else if (s.equals("e8v")) {
			e8v.setFill(Color.BLACK);
			e8c.setFill(Color.BLACK);
			e9v.setFill(Color.BLACK);
		} else if (s.equals("f1v")) {
			f1v.setFill(Color.BLACK);
			f1c.setFill(Color.BLACK);
			f2v.setFill(Color.BLACK);
		} else if (s.equals("f2v")) {
			f2v.setFill(Color.BLACK);
			f2c.setFill(Color.BLACK);
			f3v.setFill(Color.BLACK);
		} else if (s.equals("f3v")) {
			f3v.setFill(Color.BLACK);
			f3c.setFill(Color.BLACK);
			f4v.setFill(Color.BLACK);
		} else if (s.equals("f4v")) {
			f4v.setFill(Color.BLACK);
			f4c.setFill(Color.BLACK);
			f5v.setFill(Color.BLACK);
		} else if (s.equals("f5v")) {
			f5v.setFill(Color.BLACK);
			f5c.setFill(Color.BLACK);
			f6v.setFill(Color.BLACK);
		} else if (s.equals("f6v")) {
			f6v.setFill(Color.BLACK);
			f6c.setFill(Color.BLACK);
			f7v.setFill(Color.BLACK);
		} else if (s.equals("f7v")) {
			f7v.setFill(Color.BLACK);
			f7c.setFill(Color.BLACK);
			f8v.setFill(Color.BLACK);
		} else if (s.equals("f8v")) {
			f8v.setFill(Color.BLACK);
			f8c.setFill(Color.BLACK);
			f9v.setFill(Color.BLACK);
		} else if (s.equals("g1v")) {
			g1v.setFill(Color.BLACK);
			g1c.setFill(Color.BLACK);
			g2v.setFill(Color.BLACK);
		} else if (s.equals("g2v")) {
			g2v.setFill(Color.BLACK);
			g2c.setFill(Color.BLACK);
			g3v.setFill(Color.BLACK);
		} else if (s.equals("g3v")) {
			g3v.setFill(Color.BLACK);
			g3c.setFill(Color.BLACK);
			g4v.setFill(Color.BLACK);
		} else if (s.equals("g4v")) {
			g4v.setFill(Color.BLACK);
			g4c.setFill(Color.BLACK);
			g5v.setFill(Color.BLACK);
		} else if (s.equals("g5v")) {
			g5v.setFill(Color.BLACK);
			g5c.setFill(Color.BLACK);
			g6v.setFill(Color.BLACK);
		} else if (s.equals("g6v")) {
			g6v.setFill(Color.BLACK);
			g6c.setFill(Color.BLACK);
			g7v.setFill(Color.BLACK);
		} else if (s.equals("g7v")) {
			g7v.setFill(Color.BLACK);
			g7c.setFill(Color.BLACK);
			g8v.setFill(Color.BLACK);
		} else if (s.equals("g8v")) {
			g8v.setFill(Color.BLACK);
			g8c.setFill(Color.BLACK);
			g9v.setFill(Color.BLACK);
		} else if (s.equals("h1v")) {
			h1v.setFill(Color.BLACK);
			h1c.setFill(Color.BLACK);
			h2v.setFill(Color.BLACK);
		} else if (s.equals("h2v")) {
			h2v.setFill(Color.BLACK);
			h2c.setFill(Color.BLACK);
			h3v.setFill(Color.BLACK);
		} else if (s.equals("h3v")) {
			h3v.setFill(Color.BLACK);
			h3c.setFill(Color.BLACK);
			h4v.setFill(Color.BLACK);
		} else if (s.equals("h4v")) {
			h4v.setFill(Color.BLACK);
			h4c.setFill(Color.BLACK);
			h5v.setFill(Color.BLACK);
		} else if (s.equals("h5v")) {
			h5v.setFill(Color.BLACK);
			h5c.setFill(Color.BLACK);
			h6v.setFill(Color.BLACK);
		} else if (s.equals("h6v")) {
			h6v.setFill(Color.BLACK);
			h6c.setFill(Color.BLACK);
			h7v.setFill(Color.BLACK);
		} else if (s.equals("h7v")) {
			h7v.setFill(Color.BLACK);
			h7c.setFill(Color.BLACK);
			h8v.setFill(Color.BLACK);
		} else if (s.equals("h8v")) {
			h8v.setFill(Color.BLACK);
			h8c.setFill(Color.BLACK);
			h9v.setFill(Color.BLACK);
		}
	}
}
