# chessapi

Development instructions:

```bash
# build chessapi
gradlew chessapi:build

# build docker image
docker build -t chessapi:image .

# run docker image
docker run --name chessapi --restart always -p 8080:8080 -d chessapi:image
```

Note:

* The API does not catch all errors with the PGN. If you pass invalid PGN to the api you may get unexpected behavior.
