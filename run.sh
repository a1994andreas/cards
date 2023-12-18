#!/usr/bin/env bash

# Build
mvn clean -o && mvn install -DskipTests -e -U

# Run a MYSQL instance
docker run -p 3306:3306 -e MYSQL_USER=username -e MYSQL_PASSWORD=password -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=mydb -d mysql:8.1

# wait 10 seconds for the container to start
sleep 10

# Run cards application
java -jar ./target/cards-0.0.1-SNAPSHOT.jar
