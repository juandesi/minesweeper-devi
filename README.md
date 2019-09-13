# Minesweeper

This is a simple [Minesweeper](https://en.wikipedia.org/wiki/Minesweeper) REST API implementation that provides the basic functionality for clients to 
consume.

## To Build

### Pre Requisites

* Java Development Kit 1.8 or newer
* Maven 3.3.0 or newer

### Building

This is maven project, to build this project run the following command

```
mvn clean package -DskipTests
``` 

Once the execution is finished you'll find the `minesweeper-api-1.0.0-SNAPSHOT.jar` 
file under the `target/` folder, which are the final binaries of this application.

## To Run

Once the App has is built, just run the following command to start it.

```
java -jar minesweeper-api-1.0.0-SNAPSHOT.jar
```

NOTE: a Mongo DB instance is required to start this application, the connection string should be provided as an environment variabled named `mongo.juan.desi`

You will see some logs that indicate that the app is starting, and then it will be 
ready for a test user to hit.

## Try it

There is an already deployed and running version of the API so you can test it right away.

https://minesweeper-devi.herokuapp.com/games

At the root of the project you will find: 

* the full RAML Specification for this API (api.raml) to generate clients.
* a Postman collection to be used as a test HTTP Client.

### API Quick Overview

this API provides the following functionality:

* Create a new Game
* Reveal a cell in position
* Query games by id

#### Create a new Game 

```
curl -X POST https://minesweeper-devi.herokuapp.com/games [?level=EASY]
```

This endpoint will return the ID of the game so you can perform actions to it.

Optionally a Query Param `level` can be passed to generate different game settings:
 
 * EASY (8x8 grid)
 * MEDIUM (16x16 grid)
 * HARD (24x24 grid)


#### Query a Game

```
curl -X GET https://minesweeper-devi.herokuapp.com/games/{id}
```

Returns the Game information as JSON


#### View a game grid

```
curl -X GET https://minesweeper-devi.herokuapp.com/games/{id}/view
```

Returns a Game grid with in a pretty format.

#### Reveal a Cell in the grid

```
curl -X PUT https://minesweeper-devi.herokuapp.com/games/{id}/reveal \
      -d '{
            "x": 1,
            "y": 2
          }'
```

Reveals the cell in the position provided in the body of the request.

