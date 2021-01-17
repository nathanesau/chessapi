package chessapi.chess;

import chessapi.chess.piece.Piece;

public class Board {

	// each square has either a king, queen, rook, bishop, knight, pawn or null
	// each piece is either white, black or null
	Piece[][] grid = new Piece[8][8];

	// we should keep track of whether king has moved
	// if king has moved, castling is no longer an option
	Boolean kingMoved;

	public void setGrid(Piece[][] grid) {
		this.grid = grid;
	}

	public void setGridSquare(int i, int j, Piece piece) {
		this.grid[i][j] = piece;
	}

	public Piece[][] getGrid() {
		return grid;
	}

	public Piece getGridSquare(int i, int j) {
		return grid[i][j];
	}

	public Boolean hasKingMoved() {
		return kingMoved;
	}

	@Override
	public String toString() {
		String s = "";

		for (int i = 0; i < 8; i++) {
			String line = "";
			for (int j = 0; j < 8; j++) {
				if (grid[i][j] == null) {
					line += ".. ";
					continue;
				}

				String color = (grid[i][j].getColor().equals("White")) ? "w" : "b";
				switch (grid[i][j].getType()) {
				case "King":
					line = line + color + "K ";
				case "Queen":
					line = line + color + "Q ";
				case "Rook":
					line = line + color + "R ";
				case "Bishop":
					line = line + color + "B ";
				case "Knight":
					line = line + color + "N ";
				case "Pawn":
					line = line + color + "P ";
				default:
					break;
				}
			}
			s = s + line + "\n";
		}

		return s;
	}

}
