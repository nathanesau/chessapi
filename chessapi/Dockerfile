# base image
FROM adoptopenjdk/openjdk11

# copy the war file
COPY build/libs/chessapi.war .

# start app
CMD ["java", "-Xms256m", "-Xmx256m", "-jar", "chessapi.war"]