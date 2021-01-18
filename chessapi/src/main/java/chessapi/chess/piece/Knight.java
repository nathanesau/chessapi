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

		// the piece which is being moved
		Piece piece = board.getGridSquare(fromCoord[0], fromCoord[1]);

		Integer[][] potentialMoves = { { fromCoord[0] - 2, fromCoord[1] - 1 }, { fromCoord[0] - 2, fromCoord[1] + 1 },
				{ fromCoord[0] + 2, fromCoord[1] - 1 }, { fromCoord[0] + 2, fromCoord[1] + 1 },
				{ fromCoord[0] - 1, fromCoord[1] - 2 }, { fromCoord[0] + 1, fromCoord[1] - 2 },
				{ fromCoord[0] - 1, fromCoord[1] + 2 }, { fromCoord[0] + 1, fromCoord[1] + 2 } };

		for (Integer[] move : potentialMoves) {
			// boundary check
			if (!(move[0] >= 0 && move[0] <= 7 && move[1] >= 0 && move[1] <= 7)) {
				continue;
			}

			// the piece on the square which is being moved to
			Piece movePiece = board.getGridSquare(move[0], move[1]);

			// collision check
			if (movePiece != null && movePiece.getColor().equals(piece.getColor())) {
				continue;
			}

			validMoves.add(new Integer[] { move[0], move[1] });
		}

		return validMoves;
	}

}
