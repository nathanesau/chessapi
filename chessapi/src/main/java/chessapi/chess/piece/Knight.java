package chessapi.chess.piece;

import java.util.ArrayList;
import java.util.List;

import chessapi.chess.Board;

public class Knight extends Piece {

	public Knight(String color) {
		super(color);
	}

	@Override
	public String getType() {
		return "Knight";
	}

	@Override
	public List<Integer[]> getValidMoves(Integer[] fromCoord, Board board) {
		List<Integer[]> validMoves = new ArrayList<Integer[]>();

		Integer[][] potentialMoves = { { fromCoord[0] - 2, fromCoord[1] - 1 }, // up 2,
																				// left 1
				{ fromCoord[0] - 2, fromCoord[1] + 1 }, // up 2, right 1
				{ fromCoord[0] + 2, fromCoord[1] - 1 }, // down 2, left 1
				{ fromCoord[0] + 2, fromCoord[1] + 1 }, // down 2, right 1
				{ fromCoord[0] - 1, fromCoord[1] - 2 }, // left 2, up 1
				{ fromCoord[0] + 1, fromCoord[1] - 2 }, // left 2, down 1
				{ fromCoord[0] - 1, fromCoord[1] + 2 }, // right 2, up 1
				{ fromCoord[0] + 1, fromCoord[1] + 2 } // right 2, down 1
		};

		for (Integer[] move : potentialMoves) {
			// boundary check
			if (!(move[0] >= 0 && move[0] <= 7 && move[1] >= 0 && move[1] <= 7)) {
				continue;
			}

			// TODO collision check

			validMoves.add(new Integer[] { move[0], move[1] });
		}

		return validMoves;
	}

}
