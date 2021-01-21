package com.example.chessapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AjaxController {

    @GetMapping("/onDragStart")
    public ResponseEntity<String> onDragStart(@RequestParam("source") String source,
            @RequestParam("piece") String piece, @RequestParam("position") String position,
            @RequestParam("orientation") String orientation) {
        // TODO perform proper action
        return new ResponseEntity<String>("spring: onDragStart", HttpStatus.OK);
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
