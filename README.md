# chessapi

Simple chess api written using spring boot. Provides an endpoint which takes algebraic notation as input and returns the current board.

api tests:

```bash
curl -H "Content-Type: application/json" --request POST --data "hello world" "http://localhost:8080/api/get_chess_board"
```
