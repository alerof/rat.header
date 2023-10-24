# Spring boot web app in docker
Spring boot web app running in docker example

## build
````
mvn clean install -Pdocker
````
## deploy
run container
````
docker-compose up
````
stop container
````
ctrl+C
docker-compose down
````
## test
````
curl http://localhost:8090/rat
````
