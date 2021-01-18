package chessapi.chess;

import chessapi.chess.piece.Piece;

public class Move {

	Piece piece;

	// rowCol is used to uniquely identify a piece in ambiguous situations
	// will be 'a', 'b', ... 'h' or '1', '2', ..., '8' or null
	Character rowCol;

	String to;

	String type;

	Boolean check;

	public Move(Piece piece, Character rowCol, String to, String type, Boolean check) {
		this.piece = piece;
		this.rowCol = rowCol;
		this.to = to;
		this.type = type;
		this.check = check;
	}

	/**
	 * Get the piece which is being moved.
	 * @return the piece which is performing the move.
	 */
	public Piece getPiece() {
		return piece;
	}

	/**
	 * Get the rowCol of the piece which is being moved.
	 * @return 'a', 'b', ... 'h' or '1', '2', ..., '8' or null.
	 */
	public Character getRowCol() {
		return rowCol;
	}

	/**
	 * Get the move target square.
	 * @return "a1" or similar.
	 */
	public String getTo() {
		return to;
	}

	/**
	 * Get the (row, col) coordinates for the move.
	 * @return {0, 0} or similar.
	 */
	public Integer[] getToCoord() {
		int row = 8 - Character.getNumericValue(to.charAt(1));

		switch (to.charAt(0)) {
		case 'a':
			return new Integer[] { row, 0 };
		case 'b':
			return new Integer[] { row, 1 };
		case 'c':
			return new Integer[] { row, 2 };
		case 'd':
			return new Integer[] { row, 3 };
		case 'e':
			return new Integer[] { row, 4 };
		case 'f':
			return new Integer[] { row, 5 };
		case 'g':
			return new Integer[] { row, 6 };
		case 'h':
			return new Integer[] { row, 7 };
		}

		return null;
	}

	/**
	 * Get the move type.
	 * @return "Place", "Capture", "KCastle", or "QCastle".
	 */
	public String getType() {
		return type;
	}

	/**
	 * Get whether the move is check or not.
	 * @return true or false.
	 */
	public Boolean getCheck() {
		return check;
	}

	@Override
	public String toString() {

		return "Move: Piece = " + piece.toString() + ", to = " + to + ", type = " + type + ", " + check;
	}

}
