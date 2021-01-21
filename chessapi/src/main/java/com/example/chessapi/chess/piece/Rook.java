package com.example.chessapi.chess.piece;

import java.util.List;

import com.example.chessapi.chess.Board;

public class Rook extends Piece {

	public Rook(String color) {
		super(color);
	}

	@Override
	public String getType() {
		return "Rook";
	}

	@Override
	public List<Integer[]> getValidMoves(Integer[] fromCoord, Board board) {

		return getValidHorizontalVerticalMoves(fromCoord, board);
	}

}
