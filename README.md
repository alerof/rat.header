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
````
remove container
````
docker-compose down
````
## test
````
curl http://localhost:8080/rat
````

## debug
Just add Run/Debug config 
![plot](./readme.img/debug-settings.png)

then run debug