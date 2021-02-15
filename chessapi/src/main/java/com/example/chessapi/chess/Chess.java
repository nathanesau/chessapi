package com.example.chessapi.chess;

import java.util.ArrayList;
import java.util.List;

import com.example.chessapi.chess.exceptions.InvalidMoveException;
import com.example.chessapi.chess.exceptions.InvalidPositionException;
import com.example.chessapi.chess.parsers.PgnParser;
import com.example.chessapi.chess.parsers.PositionParser;
import com.example.chessapi.chess.piece.Piece;
import com.example.chessapi.chess.piece.PieceFactory;

public class Chess {

	public static Board getBlankBoard() {

		Piece[][] initialGrid = new Piece[8][8];

		Board board = new Board();
		board.setGrid(initialGrid);
		board.setKingMoved("White", false);
		board.setKingMoved("Black", false);

		return board;
	}

	/**
	 * Get initial board before any moves have been made.
	 * @return The initial board.
	 */
	public static Board getInitialBoard() {

		PieceFactory pieceFactory = new PieceFactory();

		Piece[][] initialGrid = new Piece[8][8];

		// black
		initialGrid[0][0] = pieceFactory.getPiece("Black", "Rook");
		initialGrid[0][1] = pieceFactory.getPiece("Black", "Knight");
		initialGrid[0][2] = pieceFactory.getPiece("Black", "Bishop");
		initialGrid[0][3] = pieceFactory.getPiece("Black", "Queen");
		initialGrid[0][4] = pieceFactory.getPiece("Black", "King");
		initialGrid[0][5] = pieceFactory.getPiece("Black", "Bishop");
		initialGrid[0][6] = pieceFactory.getPiece("Black", "Knight");
		initialGrid[0][7] = pieceFactory.getPiece("Black", "Rook");
		initialGrid[1][0] = pieceFactory.getPiece("Black", "Pawn");
		initialGrid[1][1] = pieceFactory.getPiece("Black", "Pawn");
		initialGrid[1][2] = pieceFactory.getPiece("Black", "Pawn");
		initialGrid[1][3] = pieceFactory.getPiece("Black", "Pawn");
		initialGrid[1][4] = pieceFactory.getPiece("Black", "Pawn");
		initialGrid[1][5] = pieceFactory.getPiece("Black", "Pawn");
		initialGrid[1][6] = pieceFactory.getPiece("Black", "Pawn");
		initialGrid[1][7] = pieceFactory.getPiece("Black", "Pawn");

		// white
		initialGrid[6][0] = pieceFactory.getPiece("White", "Pawn");
		initialGrid[6][1] = pieceFactory.getPiece("White", "Pawn");
		initialGrid[6][2] = pieceFactory.getPiece("White", "Pawn");
		initialGrid[6][3] = pieceFactory.getPiece("White", "Pawn");
		initialGrid[6][4] = pieceFactory.getPiece("White", "Pawn");
		initialGrid[6][5] = pieceFactory.getPiece("White", "Pawn");
		initialGrid[6][6] = pieceFactory.getPiece("White", "Pawn");
		initialGrid[6][7] = pieceFactory.getPiece("White", "Pawn");
		initialGrid[7][0] = pieceFactory.getPiece("White", "Rook");
		initialGrid[7][1] = pieceFactory.getPiece("White", "Knight");
		initialGrid[7][2] = pieceFactory.getPiece("White", "Bishop");
		initialGrid[7][3] = pieceFactory.getPiece("White", "Queen");
		initialGrid[7][4] = pieceFactory.getPiece("White", "King");
		initialGrid[7][5] = pieceFactory.getPiece("White", "Bishop");
		initialGrid[7][6] = pieceFactory.getPiece("White", "Knight");
		initialGrid[7][7] = pieceFactory.getPiece("White", "Rook");

		Board board = new Board();
		board.setGrid(initialGrid);
		board.setKingMoved("White", false);
		board.setKingMoved("Black", false);

		return board;
	}

	/**
	 * Get the (row, col) coordinates of a piece on the board before move was made. The
	 * color and type for the piece may not uniquely identify the piece. Additional checks
	 * are needed, specifically whether the piece can actually move to the "toCoord"
	 * square.
	 *
	 * This function is useful when loading the board using PGN. For PGN, we know where we
	 * are moving "to" but need to figure out where we are moving "from".
	 * @param move The move.
	 * @param board The current board.
	 * @return {0, 0} or similar if piece is found, null otherwise.
	 */
	public static Integer[] getFromCoord(Move move, Board board) throws InvalidMoveException {

		Piece piece = move.getPiece();

		// The square the piece is moving to.
		Integer[] toCoord = move.getToCoord();

		// fromCoord for possible pieces
		List<Integer[]> possiblePieces = new ArrayList<Integer[]>();

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.getGridSquare(i, j) == null) {
					continue;
				}
				if (board.getGridSquare(i, j).getColor().equals(piece.getColor())
						&& board.getGridSquare(i, j).getType().equals(piece.getType())) {
					Integer[] fromCoord = new Integer[] { i, j };
					if (board.getGridSquare(i, j).canMoveTo(fromCoord, toCoord, board)) {
						possiblePieces.add(fromCoord);
					}
				}
			}
		}

		if (possiblePieces.size() > 1) {
			// double pawn structure case
			if (piece.getType() == "Pawn") {
				for (Integer[] possiblePiece : possiblePieces) {
					// get piece which is one row away
					if (java.lang.Math.abs(possiblePiece[0] - toCoord[0]) == 1) {
						return possiblePiece;
					}
				}
			}

			// default case
			Character rowCol = move.getRowCol();
			for (Integer[] possiblePiece : possiblePieces) {
				String from = null;
				switch (possiblePiece[1]) {
				case 0:
					from = "a" + possiblePiece[0];
					break;
				case 1:
					from = "b" + possiblePiece[0];
					break;
				case 2:
					from = "c" + possiblePiece[0];
					break;
				case 3:
					from = "d" + possiblePiece[0];
					break;
				case 4:
					from = "e" + possiblePiece[0];
					break;
				case 5:
					from = "f" + possiblePiece[0];
					break;
				case 6:
					from = "g" + possiblePiece[0];
					break;
				case 7:
					from = "h" + possiblePiece[0];
				default:
					break;
				}
				if (from.charAt(0) == rowCol.charValue() || from.charAt(1) == rowCol.charValue()) {
					return possiblePiece;
				}
			}

			throw new InvalidMoveException("Ambiguous move (Move = " + move.toString() + ")");
		}
		else if (possiblePieces.isEmpty()) {
			throw new InvalidMoveException("No piece can make this move (Move = " + move.toString() + ")");
		}
		else {
			return possiblePieces.get(0);
		}
	}

	/**
	 * Apply provided move to provided board.
	 * @param board The current board.
	 * @throws InvalidMoveException if move if invalid.
	 */
	public static void applyMove(Move move, Board board) throws InvalidMoveException {

		PieceFactory factory = new PieceFactory();
		Integer[] toCoord = move.getToCoord();
		Integer[] fromCoord = getFromCoord(move, board);

		// TODO check if king is in check (this limits what moves are allowed)
		// TODO handle pawn promotions

		if (move.getType().equals("Capture")) {
			// TODO consider en passant capture (below logic will not work in that case)
			// since it is a capture, there must be a piece on the target square
			if (board.getGridSquare(toCoord[0], toCoord[1]).getType() == null) {
				throw new InvalidMoveException("Invalid capture (Move = " + move.toString() + ")");
			}

			// overwrite captured piece
			board.setGridSquare(toCoord[0], toCoord[1],
					factory.getPiece(move.getPiece().getColor(), move.getPiece().getType()));
			board.setGridSquare(fromCoord[0], fromCoord[1], null);
		}
		else if (move.getType().equals("QCastle")) {
			if (move.getPiece().getColor().equals("White")) {
				if (!(board.getGridSquare(7, 0).getColor().equals("White")
						&& board.getGridSquare(7, 0).getType().equals("Rook"))) {
					throw new InvalidMoveException("Invalid queen side castle (Move = " + move.toString() + ")");
				}

				// move king
				board.setGridSquare(toCoord[0], toCoord[1],
						factory.getPiece(move.getPiece().getColor(), move.getPiece().getType()));
				board.setGridSquare(fromCoord[0], fromCoord[1], null);

				// move rook
				board.setGridSquare(7, 3,
						factory.getPiece(board.getGridSquare(7, 0).getColor(), board.getGridSquare(7, 0).getType()));
				board.setGridSquare(7, 0, null);
			}
			else { // "Black"
				if (!(board.getGridSquare(0, 0).getColor().equals("Black")
						&& board.getGridSquare(0, 0).getType().equals("Rook"))) {
					throw new InvalidMoveException("Invalid queen side castle (Move = " + move.toString() + ")");
				}

				// move king
				board.setGridSquare(toCoord[0], toCoord[1],
						factory.getPiece(move.getPiece().getColor(), move.getPiece().getType()));
				board.setGridSquare(fromCoord[0], fromCoord[1], null);

				// move rook
				board.setGridSquare(0, 3,
						factory.getPiece(board.getGridSquare(0, 0).getColor(), board.getGridSquare(0, 0).getType()));
				board.setGridSquare(0, 0, null);
			}
		}
		else if (move.getType().equals("KCastle")) {
			if (move.getPiece().getColor().equals("White")) {
				if (!(board.getGridSquare(7, 7).getColor().equals("White")
						&& board.getGridSquare(7, 7).getType().equals("Rook"))) {
					throw new InvalidMoveException("Invalid king side castle (Move = " + move.toString() + ")");
				}

				// move king
				board.setGridSquare(toCoord[0], toCoord[1],
						factory.getPiece(move.getPiece().getColor(), move.getPiece().getType()));
				board.setGridSquare(fromCoord[0], fromCoord[1], null);

				// move rook
				board.setGridSquare(7, 5,
						factory.getPiece(board.getGridSquare(7, 7).getColor(), board.getGridSquare(7, 7).getType()));
				board.setGridSquare(7, 7, null);
			}
			else { // "Black"
				if (!(board.getGridSquare(0, 7).getColor().equals("Black")
						&& board.getGridSquare(0, 7).getType().equals("Rook"))) {
					throw new InvalidMoveException("Invalid king side castle (Move = " + move.toString() + ")");
				}

				// move king
				board.setGridSquare(toCoord[0], toCoord[1],
						factory.getPiece(move.getPiece().getColor(), move.getPiece().getType()));
				board.setGridSquare(fromCoord[0], fromCoord[1], null);

				// move rook
				board.setGridSquare(0, 5,
						factory.getPiece(board.getGridSquare(0, 7).getColor(), board.getGridSquare(0, 7).getType()));
				board.setGridSquare(0, 7, null);
			}
		}
		else { // "Place"
			board.setGridSquare(toCoord[0], toCoord[1],
					factory.getPiece(move.getPiece().getColor(), move.getPiece().getType()));
			board.setGridSquare(fromCoord[0], fromCoord[1], null);
		}

		// update metadata
		if (board.getGridSquare(toCoord[0], toCoord[1]).getType().equals("King")) {
			board.setKingMoved(board.getGridSquare(toCoord[0], toCoord[1]).getColor(), true);
		}
	}

	/**
	 * Load a chess game using position string.
	 * @param position The current position.
	 * @return The board.
	 */
	public static Board loadPosition(String position) throws InvalidPositionException {

		PositionParser positionParser = new PositionParser();
		Board board = positionParser.parse(position);

		return board;
	}

	/**
	 * Load a chess game using PGN.
	 * @param pgn Portable Game Notation for chess game.
	 * @return The board after all the PGN moves.
	 * @throws InvalidMoveException if pgn contains an invalid move.
	 */
	public static Board loadPgn(String pgn) throws InvalidMoveException {

		PgnParser pgnParser = new PgnParser();
		Board board = pgnParser.parse(pgn);

		return board;
	}

}
