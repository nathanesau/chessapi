package chessapi.chess.piece;

import java.util.List;

import chessapi.chess.Board;

import java.util.ArrayList;

public class Pawn extends Piece {

	public Pawn(String color) {
		super(color);
	}

	@Override
	public String getType() {
		return "Pawn";
	}

	@Override
	public List<Integer[]> getValidMoves(Integer[] fromCoord, Board board) {
		List<Integer[]> validMoves = new ArrayList<Integer[]>();

		// TODO consider collisions with other pieces
		// TODO consider diagonal pawn captures

		if (color.equals("White")) {
			validMoves.add(new Integer[] { fromCoord[0] - 1, fromCoord[1] });
			if (fromCoord[0] == 6) {
				validMoves.add(new Integer[] { fromCoord[0] - 2, fromCoord[1] });
			}
		}
		else if (color.equals("Black") && fromCoord[0] == 1) {
			validMoves.add(new Integer[] { fromCoord[0] + 1, fromCoord[1] });
			if (fromCoord[0] == 1) {
				validMoves.add(new Integer[] { fromCoord[0] + 2, fromCoord[1] });
			}
		}

		return validMoves;
	}

}
