# fruit_management
## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
mvn clean install quarkus:dev
```

## Packaging and running the application

The application can be packaged using `mvn package`.
It produces the `fruit-management-1.0-SNAPSHOT-runner.jar` file in the `/target` directory.
The application is now runnable using
``` 
java -jar target/fruit-management-1.0-SNAPSHOT-runner.jar
```

## Testing the application

The application can be tested by calling some endpoints.

Create a new fruit
```
curl -w "\n" -X POST 'http://localhost:8080/market/fruits' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "apple",
    "description": "An apple is an edible fruit produced by an apple tree",
    "quantity": 1
}'
```
Retrieve all existing fruits:
```
curl -w "\n" -v http://localhost:8080/market/fruits
```
Retrieve one single fruit:
```
curl -w "\n" -v http://localhost:8080/market/fruits/34
```
Updating an existing fruit:
```
curl -w "\n" -v -X PUT 'http://localhost:8080/market/fruits/654' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "apple",
    "description": "The new description should go here",
    "quantity": 5
}'
```
Deleting an existing fruit:
```
curl -w "\n" -v -X DELETE http://localhost:8080/market/fruits/74
```

