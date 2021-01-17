package chessapi.chess.parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chessapi.chess.Board;
import chessapi.chess.Chess;
import chessapi.chess.Move;
import chessapi.chess.exceptions.InvalidMoveException;
import chessapi.chess.piece.Piece;
import chessapi.chess.piece.PieceFactory;

public class PgnParser {

	/**
	 * Convert pgn string into a map.
	 * @param pgn Portable Game Notation for chess game.
	 * @return A map containing the "Event", "Site", "Date", "Round", "White", "Black",
	 * "Result", "Moves".
	 */
	private Map<String, String> pgnStringToPgnMap(String pgn) {
		Map<String, String> data = new HashMap<String, String>();

		for (String line : pgn.split("\n")) {

			if (line.isEmpty()) {
				continue;
			}

			boolean isMovesLine = true;
			for (String field : new String[] { "Event", "Site", "Date", "Round", "White", "Black", "Result" }) {
				if (line.contains(field)) {
					line = line.replace("[", "").replace("]", "").replace("\"", "").strip();
					data.put(field, line.split(field)[1].strip());
					isMovesLine = false;
					break;
				}
			}

			if (isMovesLine) {
				if (!data.containsKey("Moves")) {
					data.put("Moves", line.replace("\n", "").strip());
				}
				else {
					data.put("Moves", data.get("Moves") + " " + line.replace("\n", "").strip());
				}
			}
		}

		return data;
	}

	/**
	 * Get list of moves for the game.
	 * @param pgnMap A map containing the "Event", "Site", "Date", "Round", "White",
	 * "Black", "Result", "Moves".
	 * @return A list containing each move made during the game.
	 */
	private List<Move> getPgnMoveList(Map<String, String> pgnMap) {

		List<Move> moveList = new ArrayList<Move>();
		PieceFactory pieceFactory = new PieceFactory();

		for (String turn : pgnMap.get("Moves").split("[0-9]+\\.")) {
			if (turn.isEmpty()) {
				continue;
			}

			String[] pgnTurn = turn.strip().split(" ");

			for (int i = 0; i < pgnTurn.length; i++) {
				String pgnMove = pgnTurn[i].strip();
				if (Character.isDigit(pgnMove.charAt(0))) {
					// game result, ignore
					continue;
				}

				if (i == 0) { // whites move
					moveList.add(new Move(pieceFactory.getPiece("White", getPgnMovePieceType(pgnMove, "White")),
							getPgnMoveTo(pgnMove, "White"), getPgnMoveType(pgnMove, "White"),
							getPgnMoveCheck(pgnMove, "White")));
				}
				else { // blacks move
					moveList.add(new Move(pieceFactory.getPiece("Black", getPgnMovePieceType(pgnMove, "Black")),
							getPgnMoveTo(pgnMove, "Black"), getPgnMoveType(pgnMove, "Black"),
							getPgnMoveCheck(pgnMove, "Black")));
				}
			}
		}

		return moveList;
	}

	/**
	 * Get the piece type based on a pgn move and color.
	 * @param pgnMove The pgn move (ex. "Rxb5").
	 * @param color "White" or "Black".
	 * @return "King", "Queen", "Rook", "Bishop", "Knight" or "Pawn".
	 */
	private String getPgnMovePieceType(String pgnMove, String color) {
		String moveType = getPgnMoveType(pgnMove, color);
		if (moveType.equals("KCastle") || moveType.equals("QCastle")) {
			return "King";
		}
		else {
			if (pgnMove.charAt(0) == 'K') {
				return "King";
			}
			else if (pgnMove.charAt(0) == 'Q') {
				return "Queen";
			}
			else if (pgnMove.charAt(0) == 'R') {
				return "Rook";
			}
			else if (pgnMove.charAt(0) == 'B') {
				return "Bishop";
			}
			else if (pgnMove.charAt(0) == 'N') {
				return "Knight";
			}
			else {
				return "Pawn";
			}
		}
	}

	/**
	 * Get the square to move the piece to based on a pgn move and color.
	 * @param pgnMove The pgn move (ex. "Rxb5").
	 * @param color "White" or "Black".
	 * @return The square (ex. "b5").
	 */
	private String getPgnMoveTo(String pgnMove, String color) {
		String pieceType = getPgnMovePieceType(pgnMove, color);
		String moveType = getPgnMoveType(pgnMove, color);
		if (moveType.equals("KCastle")) {
			if (color.equals("White")) {
				return "g1";
			}
			else { // black
				return "g8";
			}
		}
		else if (moveType.equals("QCastle")) {
			if (color.equals("White")) {
				return "c1";
			}
			else { // black
				return "c8";
			}
		}
		else {
			if (pieceType.equals("Pawn")) {
				if (pgnMove.charAt(1) == 'x') { // captures (to starts at index 2)
					return Character.toString(pgnMove.charAt(0)) + Character.toString(pgnMove.charAt(2));
				}
				else {
					return pgnMove.substring(0, 2);
				}
			}
			else { // "King", "Queen", "Rook", "Bishop" or "Knight"
				if (pgnMove.charAt(1) == 'x') { // captures (to starts at index 2)
					return pgnMove.substring(2, 4);
				}
				else {
					return pgnMove.substring(1, 3);
				}
			}
		}
	}

	/**
	 * Get the move type based on a pgn move and color.
	 * @param turnMove The pgn move (ex. "Rxb5").
	 * @param color "White" or "Black".
	 * @return "Place", "Capture", "KCastle" or "QCastle"
	 */
	private String getPgnMoveType(String turnMove, String color) {
		if (turnMove.charAt(0) == 'O') {
			if (turnMove.length() > 3) { // O-O-O
				return "QCastle";
			}
			else {
				return "KCastle";
			}
		}
		else {
			if (turnMove.charAt(1) == 'x') {
				return "Captures";
			}
			else {
				return "Place";
			}
		}
	}

	/**
	 * Get whether the move is a check or not.
	 * @param turnMove The pgn move (ex. "Rxb5").
	 * @param color "White" or "Black".
	 * @return true if move is a check, false otherwise.
	 */
	private Boolean getPgnMoveCheck(String turnMove, String color) {
		return turnMove.endsWith("+");
	}

	public String toString(Piece[][] board) {
		String s = "";

		for (int i = 0; i < 8; i++) {
			String line = "";
			for (int j = 0; j < 8; j++) {
				if (board[i][j] == null) {
					line = line + ".. ";
					continue;
				}

				String color = (board[i][j].getColor().equals("White")) ? "w" : "b";
				switch (board[i][j].getType()) {
				case "King":
					line = line + color + "K ";
					break;
				case "Queen":
					line = line + color + "Q ";
					break;
				case "Rook":
					line = line + color + "R ";
					break;
				case "Bishop":
					line = line + color + "B ";
					break;
				case "Knight":
					line = line + color + "N ";
					break;
				case "Pawn":
					line = line + color + "P ";
					break;
				default:
					break;
				}
			}
			s = s + line + "\n";
		}

		return s;
	}

	/**
	 * @param pgn Portable Game Notation for chess game.
	 * @return The final board for the game.
	 * @throws InvalidMoveException if pgn contains an invalid move.
	 */
	public Board parse(String pgn) throws InvalidMoveException {

		Map<String, String> pgnMap = pgnStringToPgnMap(pgn);
		List<Move> pgnMoves = getPgnMoveList(pgnMap);

		Board board = Chess.getInitialBoard();
		for (Move move : pgnMoves) {
			Chess.applyMove(move, board);
			System.out.println(board.toString());
		}

		return board;
	}

}
