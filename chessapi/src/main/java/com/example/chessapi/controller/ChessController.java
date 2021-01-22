package com.example.chessapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import com.example.chessapi.chess.Board;
import com.example.chessapi.chess.Chess;
import com.example.chessapi.chess.exceptions.InvalidMoveException;
import com.example.chessapi.chess.exceptions.InvalidPositionException;

@RestController
public class ChessController {

	@GetMapping("/api/initial_board")
	public ResponseEntity<String> initialBoard()
	{
		Board board = Chess.getInitialBoard();

		return new ResponseEntity<String>(board.toString(), HttpStatus.OK);
	}

	@PostMapping("/api/load_position")
	public ResponseEntity<String> loadPosition(@RequestBody String position) {
		
		try {
			Board board = Chess.loadPosition(position);

			return new ResponseEntity<String>(board.toString(), HttpStatus.OK);
		}
		catch (InvalidPositionException e) {

			return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/api/load_pgn")
	public ResponseEntity<String> loadPgn(@RequestBody String pgn) {

		try {
			Board board = Chess.loadPgn(pgn);

			return new ResponseEntity<String>(board.toString(), HttpStatus.OK);
		}
		catch (InvalidMoveException e) {

			return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
		}
	}

		/**
	 * Provides the valid moves available for piece in the current position.
	 * 
	 * @param piece the piece to move.
	 * @param position the current position.
	 * @return all of the valid moves available for piece.
	 */
	@PostMapping("/api/valid_moves")
	public ResponseEntity<String> validMoves(@RequestBody String piece, @RequestBody String position)
	{
		try {
			Board board = Chess.loadPosition(position);

			Integer[] fromCoord = {0, 0};
			List<Integer[]> validMoves = board.getGridSquare(fromCoord[0], fromCoord[1])
					.getValidMoves(fromCoord, board);

			// TODO use proper square for piece
			// TODO get string from validMoves
			
			return new ResponseEntity<String>("valid moves", HttpStatus.OK);
		}
		catch (InvalidPositionException e) {

			return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
		}
	}

}
