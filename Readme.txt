REQUIREMENTS
============
.- First of all, install 'docker' in your computer.
.- Rename files src/main/resources/static/*.js_s to *.js


COMMENTS
========
.- This is a Spring Boot Application with all needed dependencies included in the POM file.
.- You have a docker file to run the Application. The command to run your implementation is
docker-compose up.
.- Modify properties to change the host to connect to the local database (docker). It must be your local IP in your current network.
.- Modify properties to change . The host to connect to Rabbitmq (docker) must be your local IP in your current network.
.- If not connect using the local network ip you can discover the IP using this docker command
	docker network inspect bridge
.- The host to connect to Rabbitmq (docker) must be your local IP in your current network.
.- To remove all docker installation (including prior dockers) do (in terminal):
docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)
docker rmi -f $(docker images -q)
docker volume prune

EXERCISES
=========
1.- Implement REST API to Create, Retrieve (all, byId, byEmail), Update & Delete Account from/to Database.
Account entity should have (at least) the next attributes:
    name   	    (Alphanumeric)
    email	    (Alphanumeric)
    age		    (Number)
    addresses   (Collection of account address)

2.- Create REST service to retrieve Pokemons (the name of the endpoint must be '/pokemon') that its name starts with {parameter}. The result is a JSON containing any field from the pokemon.

3.- Create Unit Testing for any class of the Implemented Service Layer.
4.- Create the WEB page to manage (CRUD) the accounts.
