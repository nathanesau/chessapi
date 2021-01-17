package chessapi.chess.piece;

public class PieceFactory {

	public Piece getPiece(String color, String type) {
		switch (type) {
		case "King":
			return new King(color);
		case "Queen":
			return new Queen(color);
		case "Rook":
			return new Rook(color);
		case "Bishop":
			return new Bishop(color);
		case "Knight":
			return new Knight(color);
		case "Pawn":
			return new Pawn(color);
		default:
			return null;
		}
	}

}
