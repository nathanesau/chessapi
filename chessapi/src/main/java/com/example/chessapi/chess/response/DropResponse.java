package com.example.chessapi.chess.response;

public class DropResponse {

    // "snapback" or "drop"
    String action = "";
    
    String pgn = "";

    String position = "";
    
    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setPgn(String pgn) {
        this.pgn = pgn;
    }

    public String getPgn() {
        return pgn;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

}
