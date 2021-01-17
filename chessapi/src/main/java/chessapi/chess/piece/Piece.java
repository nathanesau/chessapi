package chessapi.chess.piece;

import java.util.ArrayList;
import java.util.List;

import chessapi.chess.Board;

public abstract class Piece {

	String color;

	public Piece(String color) {
		this.color = color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	// "White" or "Black"
	public String getColor() {
		return color;
	}

	// "King", "Queen", "Rook", "Bishop", "Knight" or "Pawn"
	public abstract String getType();

	@Override
	public String toString() {

		return "Piece: color = " + color + ", type = " + getType();
	}

	public List<Integer[]> getValidHorizontalVerticalMoves(Integer[] fromCoord, Board board) {
		List<Integer[]> validMoves = new ArrayList<Integer[]>();

		// TODO consider collisions with other pieces

		// up
		Integer[] coord = new Integer[] { fromCoord[0] - 1, fromCoord[1] };
		while (coord[0] >= 0) {
			validMoves.add(new Integer[] { coord[0], coord[1] });
			coord[0] = coord[0] - 1;
		}

		// down
		coord = new Integer[] { fromCoord[0] + 1, fromCoord[1] };
		while (coord[0] <= 7) {
			validMoves.add(new Integer[] { coord[0], coord[1] });
			coord[0] = coord[0] + 1;
		}

		// left
		coord = new Integer[] { fromCoord[0], fromCoord[1] - 1 };
		while (coord[1] >= 0) {
			validMoves.add(new Integer[] { coord[0], coord[1] });
			coord[1] = coord[1] - 1;
		}

		// right
		coord = new Integer[] { fromCoord[0], fromCoord[1] + 1 };
		while (coord[1] <= 7) {
			validMoves.add(new Integer[] { coord[0], coord[1] });
			coord[1] = coord[1] + 1;
		}

		return validMoves;
	}

	public List<Integer[]> getValidDiagonalMoves(Integer[] fromCoord, Board board) {
		List<Integer[]> validMoves = new ArrayList<Integer[]>();

		// TODO consider collisions with other pieces

		// up-left
		Integer[] coord = { fromCoord[0] - 1, fromCoord[1] - 1 };
		while (coord[0] >= 0 && coord[1] >= 0) {
			validMoves.add(new Integer[] { coord[0], coord[1] });
			coord[0] = coord[0] - 1;
			coord[1] = coord[1] - 1;
		}

		// up-right
		coord = new Integer[] { fromCoord[0] - 1, fromCoord[1] + 1 };
		while (coord[0] >= 0 && coord[1] <= 7) {
			validMoves.add(new Integer[] { coord[0], coord[1] });
			coord[0] = coord[0] - 1;
			coord[1] = coord[1] + 1;
		}

		// down-left
		coord = new Integer[] { fromCoord[0] + 1, fromCoord[1] - 1 };
		while (coord[0] <= 7 && coord[1] >= 0) {
			validMoves.add(new Integer[] { coord[0], coord[1] });
			coord[0] = coord[0] + 1;
			coord[1] = coord[1] - 1;
		}

		// down-right
		coord = new Integer[] { fromCoord[0] + 1, fromCoord[1] + 1 };
		while (coord[0] <= 7 && coord[1] >= 7) {
			validMoves.add(new Integer[] { coord[0], coord[1] });
			coord[0] = coord[0] + 1;
			coord[1] = coord[1] + 1;
		}

		return validMoves;
	}

	public abstract List<Integer[]> getValidMoves(Integer[] fromCoord, Board board);

	public Boolean canMoveTo(Integer[] fromCoord, Integer[] toCoord, Board board) {
		List<Integer[]> validMoves = getValidMoves(fromCoord, board);
		for (Integer[] validMove : validMoves) {
			if (validMove[0] == toCoord[0] && validMove[1] == toCoord[1]) {
				return true;
			}
		}
		return false;
	}

}
