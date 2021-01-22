package com.example.chessapi.controller;

import java.util.List;

import com.example.chessapi.chess.Board;
import com.example.chessapi.chess.Chess;
import com.example.chessapi.chess.exceptions.InvalidPositionException;
import com.example.chessapi.chess.piece.Piece;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    public String getSquareText(Integer[] square) {

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

    @GetMapping("/onDragStart")
    public ResponseEntity<String> onDragStart(@RequestParam("source") String source,
            @RequestParam("piece") String piece, @RequestParam("position") String position,
            @RequestParam("orientation") String orientation) {
        
        try {
            Board board = Chess.loadPosition(position);
            
            Integer[] moveCoord = getSquare(source);
            Piece movePiece = board.getGridSquare(moveCoord[0], moveCoord[1]);
            List<Integer[]> validMoves = movePiece.getValidMoves(moveCoord, board);

            String text = "valid moves: ";
            for (Integer[] square : validMoves) {
                text += getSquareText(square) + " ";
            }

            return new ResponseEntity<String>(text, HttpStatus.OK);
        }
        catch (InvalidPositionException e) {

            return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/onDrop")
    public ResponseEntity<String> onDrop(@RequestParam("source") String source,
            @RequestParam("target") String target) {
        // TODO perform proper action
        return new ResponseEntity<String>("spring: onDrop", HttpStatus.OK);
    }

    @GetMapping("/onSnapEnd")
    public ResponseEntity<String> onSnapEnd() {
        // TODO perform proper action
        return new ResponseEntity<String>("spring: onSnapEnd", HttpStatus.OK);
    }

}
