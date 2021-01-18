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

		// the piece which is being moved
		Piece piece = board.getGridSquare(fromCoord[0], fromCoord[1]);

		// TODO consider en pessant captures (requires additional board info)

		if (color.equals("White")) {
			Piece collisionPiece = board.getGridSquare(fromCoord[0] - 1, fromCoord[1]);

			// collision check
			if (collisionPiece == null) {
				validMoves.add(new Integer[] { fromCoord[0] - 1, fromCoord[1] });
			}

			if (fromCoord[0] == 6) {
				Piece collisionPiece1 = board.getGridSquare(fromCoord[0] - 1, fromCoord[1]);
				Piece collisionPiece2 = board.getGridSquare(fromCoord[0] - 2, fromCoord[1]);

				// collision check
				if (collisionPiece1 == null && collisionPiece2 == null) {
					validMoves.add(new Integer[] { fromCoord[0] - 2, fromCoord[1] });
				}
			}

			Integer[][] captureSquares = { { fromCoord[0] - 1, fromCoord[1] - 1 },
					{ fromCoord[0] - 1, fromCoord[1] + 1 } };

			for (Integer[] captureSquare : captureSquares) {
				// boundary check
				if (!(captureSquare[0] >= 0 && captureSquare[0] <= 7 && captureSquare[1] >= 0
						&& captureSquare[1] <= 7)) {
					continue;
				}

				Piece capturePiece = board.getGridSquare(captureSquare[0], captureSquare[1]);

				// capture check
				if (capturePiece != null && !capturePiece.getColor().equals(piece.getColor())) {
					validMoves.add(new Integer[] { captureSquare[0], captureSquare[1] });
				}
			}
		}
		else if (color.equals("Black")) {
			Piece collisionPiece = board.getGridSquare(fromCoord[0] + 1, fromCoord[1]);

			// collision check
			if (collisionPiece == null) {
				validMoves.add(new Integer[] { fromCoord[0] + 1, fromCoord[1] });
			}

			if (fromCoord[0] == 1) {
				Piece collisionPiece1 = board.getGridSquare(fromCoord[0] + 1, fromCoord[1]);
				Piece collisionPiece2 = board.getGridSquare(fromCoord[0] + 2, fromCoord[1]);

				// collision check
				if (collisionPiece1 == null && collisionPiece2 == null) {
					validMoves.add(new Integer[] { fromCoord[0] + 2, fromCoord[1] });
				}
			}

			Integer[][] captureSquares = { { fromCoord[0] + 1, fromCoord[1] - 1 },
					{ fromCoord[0] + 1, fromCoord[1] + 1 } };

			for (Integer[] captureSquare : captureSquares) {
				// boundary check
				if (!(captureSquare[0] >= 0 && captureSquare[0] <= 7 && captureSquare[1] >= 0
						&& captureSquare[1] <= 7)) {
					continue;
				}

				Piece capturePiece = board.getGridSquare(captureSquare[0], captureSquare[1]);

				// capture check
				if (capturePiece != null && !capturePiece.getColor().equals(piece.getColor())) {
					validMoves.add(new Integer[] { captureSquare[0], captureSquare[1] });
				}
			}
		}

		return validMoves;
	}

}
