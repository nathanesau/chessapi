package chessapi.chess.piece;

import java.util.List;

import chessapi.chess.Board;

public class Bishop extends Piece {

	public Bishop(String color) {
		super(color);
	}

	@Override
	public String getType() {
		return "Bishop";
	}

	@Override
	public List<Integer[]> getValidMoves(Integer[] fromCoord, Board board) {

		return getValidDiagonalMoves(fromCoord, board);
	}

}
