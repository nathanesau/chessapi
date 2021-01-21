package com.example.chessapi.chess.piece;

import java.util.ArrayList;
import java.util.List;

import com.example.chessapi.chess.Board;

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

		// the piece which is being moved
		Piece piece = board.getGridSquare(fromCoord[0], fromCoord[1]);

		// up
		Integer[] coord = new Integer[] { fromCoord[0] - 1, fromCoord[1] };
		while (coord[0] >= 0) { // boundary check
			// the piece on the square which is being moved to
			Piece coordPiece = board.getGridSquare(coord[0], coord[1]);

			// collision check
			if (coordPiece != null) {
				if (!coordPiece.getColor().equals(piece.getColor())) {
					validMoves.add(new Integer[] { coord[0], coord[1] });
				}
				break;
			}

			validMoves.add(new Integer[] { coord[0], coord[1] });
			coord[0] = coord[0] - 1;
		}

		// down
		coord = new Integer[] { fromCoord[0] + 1, fromCoord[1] };
		while (coord[0] <= 7) { // boundary check
			// the piece on the square which is being moved to
			Piece coordPiece = board.getGridSquare(coord[0], coord[1]);

			// collision check
			if (coordPiece != null) {
				if (!coordPiece.getColor().equals(piece.getColor())) {
					validMoves.add(new Integer[] { coord[0], coord[1] });
				}
				break;
			}

			validMoves.add(new Integer[] { coord[0], coord[1] });
			coord[0] = coord[0] + 1;
		}

		// left
		coord = new Integer[] { fromCoord[0], fromCoord[1] - 1 };
		while (coord[1] >= 0) { // boundary check
			// the piece on the square which is being moved to
			Piece coordPiece = board.getGridSquare(coord[0], coord[1]);

			// collision check
			if (coordPiece != null) {
				if (!coordPiece.getColor().equals(piece.getColor())) {
					validMoves.add(new Integer[] { coord[0], coord[1] });
				}
				break;
			}

			validMoves.add(new Integer[] { coord[0], coord[1] });
			coord[1] = coord[1] - 1;
		}

		// right
		coord = new Integer[] { fromCoord[0], fromCoord[1] + 1 };
		while (coord[1] <= 7) { // boundary check
			// the piece on the square which is being moved to
			Piece coordPiece = board.getGridSquare(coord[0], coord[1]);

			// collision check
			if (coordPiece != null) {
				if (!coordPiece.getColor().equals(piece.getColor())) {
					validMoves.add(new Integer[] { coord[0], coord[1] });
				}
				break;
			}

			validMoves.add(new Integer[] { coord[0], coord[1] });
			coord[1] = coord[1] + 1;
		}

		return validMoves;
	}

	public List<Integer[]> getValidDiagonalMoves(Integer[] fromCoord, Board board) {
		List<Integer[]> validMoves = new ArrayList<Integer[]>();

		// the piece which is being moved
		Piece piece = board.getGridSquare(fromCoord[0], fromCoord[1]);

		// up-left
		Integer[] coord = { fromCoord[0] - 1, fromCoord[1] - 1 };
		while (coord[0] >= 0 && coord[1] >= 0) { // boundary check
			// the piece on the square which is being moved to
			Piece coordPiece = board.getGridSquare(coord[0], coord[1]);

			// collision check
			if (coordPiece != null) {
				if (!coordPiece.getColor().equals(piece.getColor())) {
					validMoves.add(new Integer[] { coord[0], coord[1] });
				}
				break;
			}

			validMoves.add(new Integer[] { coord[0], coord[1] });
			coord[0] = coord[0] - 1;
			coord[1] = coord[1] - 1;
		}

		// up-right
		coord = new Integer[] { fromCoord[0] - 1, fromCoord[1] + 1 };
		while (coord[0] >= 0 && coord[1] <= 7) { // boundary check
			// the piece on the square which is being moved to
			Piece coordPiece = board.getGridSquare(coord[0], coord[1]);

			// collision check
			if (coordPiece != null) {
				if (!coordPiece.getColor().equals(piece.getColor())) {
					validMoves.add(new Integer[] { coord[0], coord[1] });
				}
				break;
			}

			validMoves.add(new Integer[] { coord[0], coord[1] });
			coord[0] = coord[0] - 1;
			coord[1] = coord[1] + 1;
		}

		// down-left
		coord = new Integer[] { fromCoord[0] + 1, fromCoord[1] - 1 };
		while (coord[0] <= 7 && coord[1] >= 0) { // boundary check
			// the piece on the square which is being moved to
			Piece coordPiece = board.getGridSquare(coord[0], coord[1]);

			// collision check
			if (coordPiece != null) {
				if (!coordPiece.getColor().equals(piece.getColor())) {
					validMoves.add(new Integer[] { coord[0], coord[1] });
				}
				break;
			}

			validMoves.add(new Integer[] { coord[0], coord[1] });
			coord[0] = coord[0] + 1;
			coord[1] = coord[1] - 1;
		}

		// down-right
		coord = new Integer[] { fromCoord[0] + 1, fromCoord[1] + 1 };
		while (coord[0] <= 7 && coord[1] <= 7) { // boundary check
			// the piece on the square which is being moved to
			Piece coordPiece = board.getGridSquare(coord[0], coord[1]);

			// collision check
			if (coordPiece != null) {
				if (!coordPiece.getColor().equals(piece.getColor())) {
					validMoves.add(new Integer[] { coord[0], coord[1] });
				}
				break;
			}

			validMoves.add(new Integer[] { coord[0], coord[1] });
			coord[0] = coord[0] + 1;
			coord[1] = coord[1] + 1;
		}

		return validMoves;
	}

	// TODO address checks for the king (this limits what moves are possible)

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
