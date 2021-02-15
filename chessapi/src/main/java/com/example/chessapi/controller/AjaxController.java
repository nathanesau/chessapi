package com.example.chessapi.controller;

import java.util.List;

import com.example.chessapi.chess.Board;
import com.example.chessapi.chess.Chess;
import com.example.chessapi.chess.exceptions.InvalidMoveException;
import com.example.chessapi.chess.exceptions.InvalidPositionException;
import com.example.chessapi.chess.piece.Piece;
import com.example.chessapi.chess.response.DropResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// Based on https://chessboardjs.com/examples#5000
@RestController
public class AjaxController {

    private Integer[] getSquare(String source) {

        switch(source.charAt(0)) {
            case 'a':
                return new Integer[]{8 - Character.getNumericValue(source.charAt(1)), 0};
            case 'b':
                return new Integer[]{8 - Character.getNumericValue(source.charAt(1)), 1};
            case 'c':
                return new Integer[]{8 - Character.getNumericValue(source.charAt(1)), 2};
            case 'd':
                return new Integer[]{8 - Character.getNumericValue(source.charAt(1)), 3};
            case 'e':
                return new Integer[]{8 - Character.getNumericValue(source.charAt(1)), 4};
            case 'f':
                return new Integer[]{8 - Character.getNumericValue(source.charAt(1)), 5};
            case 'g':
                return new Integer[]{8 - Character.getNumericValue(source.charAt(1)), 6};
            case 'h':
                return new Integer[]{8 - Character.getNumericValue(source.charAt(1)), 7};
        }

        return null;
    }

    private String getSquareText(Integer[] square) {

        switch(square[1]) {
            case 0:
                return "a" + (8 - square[0]);
            case 1:
                return "b" + (8 - square[0]);
            case 2:
                return "c" + (8 - square[0]);
            case 3:
                return "d" + (8 - square[0]);
            case 4:
                return "e" + (8 - square[0]);
            case 5:
                return "f" + (8 - square[0]);
            case 6:
                return "g" + (8 - square[0]);
            case 7:
                return "h" + (8 - square[0]);
        }

        return null;
    }

    /**
     * Check whether current piece can be moved.
     * @param source The square which was clicked (ex. a4).
     * @param piece The piece which was clicked (ex. wP).
     * @param position The current position.
     * @param orientation The current color (i.e. "white" or "black").
     * @return True if the current piece can be moved, False otherwise.
     */
    @GetMapping("/onDragStart")
    public ResponseEntity<Boolean> onDragStart(@RequestParam("source") String source,
            @RequestParam("piece") String piece, @RequestParam("position") String position,
            @RequestParam("orientation") String orientation) {
        
        try {
            Board board = Chess.loadPosition(position);

            // TODO check whether game over

            Integer[] moveCoord = getSquare(source);
            Piece movePiece = board.getGridSquare(moveCoord[0], moveCoord[1]);
            
            String color = orientation.substring(0, 1).toUpperCase() + orientation.substring(1);
            if (!movePiece.getColor().equals(color)) {
                return new ResponseEntity<Boolean>(false, HttpStatus.OK);
            }

            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }
        catch (InvalidPositionException e) {

            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Check whether move is valid or not.
     * This determines whether or not the move is applied to the board.
     * Note that the API does not keep track of the board in memory
     * so we must pass the current position to the API for every API call.
     * This would be different if we were using JS for the chess logic
     * since we could just keep the board in memory the entire time.
     * 
     * @param source The original square of the piece.
     * @param target The new square of the piece.
     * @param position The current position.
     * @return "snapback" if move is invalid.
     */
    @GetMapping("/onDrop")
    public ResponseEntity<DropResponse> onDrop(@RequestParam("source") String source,
            @RequestParam("target") String target, @RequestParam("position") String position,
            @RequestParam("pgn") String pgn) {
        
        DropResponse response = new DropResponse();

        try {
            Board board = Chess.loadPosition(position);

            Integer[] moveCoord = getSquare(source);
            Piece movePiece = board.getGridSquare(moveCoord[0], moveCoord[1]);
            List<Integer[]> validMoves = movePiece.getValidMoves(moveCoord, board);

            for (Integer[] square : validMoves) {
                if (getSquareText(square).equals(target)) {
                    
                    String movePgn = board.getMovePgn(movePiece, square);
                    response.setAction("drop");

                    // TODO use the proper PGN here
                    response.setPgn(pgn + "1. " + movePgn);

                    // TODO use the proper position here
                    response.setPosition(position);

                    return new ResponseEntity<DropResponse>(response, HttpStatus.OK);
                }
            }

            response.setAction("snapback");
            response.setPgn(pgn);
            response.setPosition(position);

            return new ResponseEntity<DropResponse>(response, HttpStatus.OK);
        }
        catch (InvalidPositionException e) {

            response.setAction("snapback");
            response.setPgn(pgn);
            response.setPosition(position);

            return new ResponseEntity<DropResponse>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Return the current position, based on the PGN.
     * 
     * @return The current position.
     */
    @GetMapping("/onSnapEnd")
    public ResponseEntity<String> onSnapEnd(@RequestParam("pgn") String pgn) {
        try {
            Board board = Chess.loadPgn(pgn);

            return new ResponseEntity<String>(board.toString(), HttpStatus.OK);
        }
        catch (InvalidMoveException e) {
            
            return new ResponseEntity<String>("", HttpStatus.OK);
        }
    }

}
