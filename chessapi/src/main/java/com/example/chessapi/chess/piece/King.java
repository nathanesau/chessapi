package com.example.chessapi.chess.piece;

import java.util.ArrayList;
import java.util.List;

import com.example.chessapi.chess.Board;

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

		// the piece which is being moved
		Piece piece = board.getGridSquare(fromCoord[0], fromCoord[1]);

		Integer[][] potentialMoves = { { fromCoord[0] - 1, fromCoord[1] - 1 }, { fromCoord[0] - 1, fromCoord[1] + 1 },
				{ fromCoord[0] + 1, fromCoord[1] - 1 }, { fromCoord[0] + 1, fromCoord[1] + 1 },
				{ fromCoord[0] - 1, fromCoord[1] }, { fromCoord[0], fromCoord[1] - 1 },
				{ fromCoord[0] + 1, fromCoord[1] }, { fromCoord[0], fromCoord[1] + 1 } };

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

		if (!board.getKingMoved(piece.getColor())) {
			// TODO address that king cannot castle through check

			// queen side castle
			if (board.getGridSquare(fromCoord[0], fromCoord[1] - 1) == null
					&& board.getGridSquare(fromCoord[0], fromCoord[1] - 2) == null
					&& board.getGridSquare(fromCoord[0], fromCoord[1] - 3) == null) {

				Piece rookPiece = board.getGridSquare(fromCoord[0], fromCoord[1] - 4);
				if (rookPiece != null && rookPiece.getColor().equals(piece.getColor())
						&& rookPiece.getType().equals("Rook")) {

					validMoves.add(new Integer[] { fromCoord[0], fromCoord[1] - 2 });
				}
			}

			// king side castle
			if (board.getGridSquare(fromCoord[0], fromCoord[1] + 1) == null
					&& board.getGridSquare(fromCoord[0], fromCoord[1] + 2) == null) {

				Piece rookPiece = board.getGridSquare(fromCoord[0], fromCoord[1] + 3);
				if (rookPiece != null && rookPiece.getColor().equals(piece.getColor())
						&& rookPiece.getType().equals("Rook")) {

					validMoves.add(new Integer[] { fromCoord[0], fromCoord[1] + 2 });
				}
			}
		}

		return validMoves;
	}

}
