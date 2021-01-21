package com.example.chessapi.chess;

import com.example.chessapi.chess.piece.Piece;

public class Board {

	// each square has either a king, queen, rook, bishop, knight, pawn or null
	// each piece is either white, black or null
	Piece[][] grid = new Piece[8][8];

	// we should keep track of whether king has moved
	// if king has moved, castling is no longer an option
	Boolean[] kingMoved = new Boolean[2];

	// TODO address en pessant captures (need to store additional pawn info)

	public void setGrid(Piece[][] grid) {
		this.grid = grid;
	}

	public void setGridSquare(int i, int j, Piece piece) {
		this.grid[i][j] = piece;
	}

	public void setKingMoved(String color, Boolean kingMoved) {
		if (color.equals("White")) {
			this.kingMoved[0] = kingMoved;
		}
		else { // "Black"
			this.kingMoved[1] = kingMoved;
		}
	}

	public Piece[][] getGrid() {
		return grid;
	}

	public Piece getGridSquare(int i, int j) {
		return grid[i][j];
	}

	public Boolean getKingMoved(String color) {
		if (color.equals("White")) {
			return kingMoved[0];
		}
		else { // "Black"
			return kingMoved[1];
		}
	}

	@Override
	public String toString() {
		String s = "";

		for (int i = 0; i < 8; i++) {
			String line = "";
			for (int j = 0; j < 8; j++) {
				if (grid[i][j] == null) {
					line = line + ".. ";
					continue;
				}

				String color = (grid[i][j].getColor().equals("White")) ? "w" : "b";
				switch (grid[i][j].getType()) {
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

}
