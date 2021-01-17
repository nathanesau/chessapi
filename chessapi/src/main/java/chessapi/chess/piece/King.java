package chessapi.chess.piece;

import java.util.ArrayList;
import java.util.List;

import chessapi.chess.Board;

public class King extends Piece {

	public King(String color) {
		super(color);
	}

	@Override
	public String getType() {
		return "King";
	}

	@Override
	public List<Integer[]> getValidMoves(Integer[] fromCoord, Board board) {
		List<Integer[]> validMoves = new ArrayList<Integer[]>();

		// TODO consider collisions with other pieces
		// TODO fix castle logic

		Integer[][] potentialMoves = { { fromCoord[0] - 1, fromCoord[1] }, // up
				{ fromCoord[0], fromCoord[1] - 1 }, // left
				{ fromCoord[0] + 1, fromCoord[1] }, // down
				{ fromCoord[0], fromCoord[1] + 1 }, // right
				{ fromCoord[0], fromCoord[1] - 2 }, // queen side castle
				{ fromCoord[0], fromCoord[1] + 2 } // king side castle
		};

		for (Integer[] move : potentialMoves) {
			if (move[0] >= 0 && move[0] <= 7 && move[1] >= 0 && move[1] <= 7) {
				validMoves.add(new Integer[] { move[0], move[1] });
			}
		}

		return validMoves;
	}

}
