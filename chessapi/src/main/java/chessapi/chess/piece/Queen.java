package chessapi.chess.piece;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import chessapi.chess.Board;

public class Queen extends Piece {

	public Queen(String color) {
		super(color);
	}

	@Override
	public String getType() {
		return "Queen";
	}

	@Override
	public List<Integer[]> getValidMoves(Integer[] fromCoord, Board board) {

		List<Integer[]> validDiagonalMoves = getValidDiagonalMoves(fromCoord, board);
		List<Integer[]> validHorizontalVerticalMoves = getValidHorizontalVerticalMoves(fromCoord, board);

		return Stream.of(validDiagonalMoves, validHorizontalVerticalMoves).flatMap(Collection::stream)
				.collect(Collectors.toList());
	}

}
