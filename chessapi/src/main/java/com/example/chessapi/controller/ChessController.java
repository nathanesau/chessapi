package com.example.chessapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.chessapi.chess.Board;
import com.example.chessapi.chess.Chess;
import com.example.chessapi.chess.exceptions.InvalidMoveException;

@RestController
public class ChessController {

	@PostMapping("/api/get_chess_board")
	public ResponseEntity<String> getChessBoard(@RequestBody String pgn) {

		try {
			Board board = Chess.loadPgn(pgn);

			return new ResponseEntity<String>(board.toString(), HttpStatus.OK);
		}
		catch (InvalidMoveException e) {

			return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
		}
	}

}
