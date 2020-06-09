# Fruit Management
The fruit management app is a simple quarkus application that can be run in your local machine and OpenShift 
using the following two different persistence modes:
- Using a MongoDB database.
- Using just a simple list.

By default, the application would use an `ArrayList` to store data, but if you have a MongoDB instance running, 
then you can enable it by adding `fruit.storage.ephemeral` environment variable.
```
// Possible values are 'true' or 'false'
// To enable MongoDB
-Dfruit.storage.ephemeral=false
```
Typically, if you have a running MongoDB instance in your local machine with a direct type connection, you don't need any extra configuration.
 However, if you have database with user and password configured, you need to add `quarkus.mongodb.connection-string` env variable.
```
// MongoDB connection string
-Dquarkus.mongodb.connection-string=mongodb://<username>:<pasowrd>@<host>/<database>
```

- ## Running the application in your local machine
The following steps show you how to run the application in your local machine. Make sure you have installed and configured:
- Maven 3.6.x 
- JDK 11 or higher.

### Running the application in dev mode
You can run your application in dev mode that enables live coding using:
```
mvn clean install quarkus:dev
```
### Packaging and running the application
The application can be packaged using `mvn package`.
It produces the `fruit-management-1.0-SNAPSHOT-runner.jar` file in the `/target` directory.
The application is now runnable using
``` 
java -jar target/fruit-management-1.0-SNAPSHOT-runner.jar
```
### Testing the application with JUnit
By default, the application would run all the unit tests, but this can be disabled by adding a flag which would cause the
application to build faster.
``` 
mvn clean install -DskipTests=true
```
- ## Running the application on OpenShift
Assuming you have an OCP 4 available, create a project first:
```
oc new-project fruit-management
```
If you want to use a MongoDB database, we need to create a new MongoDB app first.
```
oc new-app openshift/mongodb-ephemeral -p MONGODB_PASSWORD=abc123 -p MONGODB_USER=admin -p MONGODB_DATABASE=market -p MONGODB_ADMIN_PASSWORD=abc321 -l db=marketdb
```
Once it's deployed, now we need to create a template and create a new app from it.
```
oc create -f https://raw.githubusercontent.com/redhat-gpte-devopsautomation/fruit_management/master/.openshift-template.yaml
```
Create now the fruit app (notice how we are passing environment variables to the app).
If we don't need a MongoDB, there's no need to pass any variable during the application creation.
```
oc new-app fruit-quarkus -e quarkus.mongodb.connection-string=mongodb://admin:abc123@mongodb/market -e fruit.storage.ephemeral=false
```

- ## Testing the application with curl
Depending on where you are running your application, the `$BASE_URL` is different.
- From your local machine: `http://localhost:8080`
- From OpenShift (depends on project and cluster name): `http://fruit-quarkus-fruit-management.apps.shared-dev4.dev4.openshift.opentlc.com`

If you need to see the application in action, there are some endpoints available which can be invoked by using curl
or any other tool like Postman.

Create a new fruit
```
curl -w "\n" -v -X POST '{$BASE_URL}/market/fruits' \
--header 'Content-Type: application/json' \
--data '{
    "name": "apple",
    "description": "An apple is an edible fruit produced by an apple tree",
    "quantity": 1
}'
```
Retrieve all existing fruits:
```
curl -w "\n" -v {$BASE_URL}/market/fruits
```
Retrieve one single fruit:
```
curl -w "\n" -v {$BASE_URL}/market/fruits/34
```
Updating an existing fruit:
```
curl -w "\n" -v -X PUT '{$BASE_URL}/market/fruits/654' \
--header 'Content-Type: application/json' \
--data '{
    "name": "apple",
    "description": "The new description should go here",
    "quantity": 5
}'
```
Deleting an existing fruit:
```
curl -w "\n" -v -X DELETE {$BASE_URL}/market/fruits/74
```

