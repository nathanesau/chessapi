package com.example.chessapi.chess.parsers;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Map.Entry;

import com.example.chessapi.chess.Board;
import com.example.chessapi.chess.Chess;
import com.example.chessapi.chess.exceptions.InvalidPositionException;
import com.example.chessapi.chess.piece.Piece;
import com.example.chessapi.chess.piece.PieceFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PositionParser {

    /**
     * Get the {row, col} associated with some square text (ex. a8).
     * 
     * @param squareText ex. a8
     * @return ex. {7, 7}
     */
    private Integer[] getSquare(String squareText) {

        switch(squareText.charAt(0)) {
            case 'a':
                return new Integer[]{8 - Character.getNumericValue(squareText.charAt(1)), 0};
            case 'b':
                return new Integer[]{8 - Character.getNumericValue(squareText.charAt(1)), 1};
            case 'c':
                return new Integer[]{8 - Character.getNumericValue(squareText.charAt(1)), 2};
            case 'd':
                return new Integer[]{8 - Character.getNumericValue(squareText.charAt(1)), 3};
            case 'e':
                return new Integer[]{8 - Character.getNumericValue(squareText.charAt(1)), 4};
            case 'f':
                return new Integer[]{8 - Character.getNumericValue(squareText.charAt(1)), 5};
            case 'g':
                return new Integer[]{8 - Character.getNumericValue(squareText.charAt(1)), 6};
            case 'h':
                return new Integer[]{8 - Character.getNumericValue(squareText.charAt(1)), 7};
        }

        return null;
    }

    private Piece getPiece(String pieceText) {
        
        PieceFactory pieceFactory = new PieceFactory();

        String color = (pieceText.charAt(0) == 'b') ? "Black" : "White";
        String type = (pieceText.charAt(1) == 'P') ? "Pawn"
            : (pieceText.charAt(1) == 'N') ? "Knight"
            : (pieceText.charAt(1) == 'B') ? "Bishop"
            : (pieceText.charAt(1) == 'R') ? "Rook"
            : (pieceText.charAt(1) == 'Q') ? "Queen"
            : (pieceText.charAt(1) == 'K') ? "King"
            : null;

        return pieceFactory.getPiece(color, type);
    }

    /**
     * @param position The current position.
     * @return The board.
     * @throws InvalidPositionException if position is invalid.
     */
    public Board parse(String position) throws InvalidPositionException {

        Board board = Chess.getInitialBoard();
        
        Gson gson = new Gson();
        Type positionType = new TypeToken<Map<String, String>>() {}.getType();
        Map<String, String> positionMap = gson.fromJson(position, positionType);

        for (Entry<String, String> entry : positionMap.entrySet()) {
            String key = entry.getKey(); // ex. a8
            String value = entry.getValue(); // ex. bP
            Integer[] square = getSquare(key); // ex. {7, 7}
            Piece piece = getPiece(value);
            board.setGridSquare(square[0], square[1], piece);
        }

        return board;
    }

}
