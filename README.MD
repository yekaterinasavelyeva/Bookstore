## Running local DB

1. Need to install Docker
2. In terminal go to /docker folder 
3. Start docker instance with initialized bookstore_local_db by running `docker-compose up --build`
4. DB with user `user` and password `1234` is up!
    * if some DB information should be changed do it in `docker/db/init-db.sql` or 
    `docker/docker-compose.yml`

5. Local db now should be up for running. Put DB url and credentials in application.yml 

DATABASE_USER=user
DATABASE_PASSWORD=1234
DATABASE_URL=jdbc:postgresql://localhost:5432/bookstore_local_db

and start service as usual

6. To stop docker just run `docker-compose down` and all data will be saved and available next time running local db instance.
If it's needed to remove all data the Docker volumes must be cleaned by running `docker-compose down -v`

7. Fill Database with tables and data, needed files are in the directory  src/main/resources/sql

## Running Application

1. This is a Spring Boot application, so it doesn't need any specific server locally.
2. To run application:
       - import gradle project, use gradle bootRun command, if running from IDE
       - to get executable war, run bootWar gradle command. Artifact will be available under build/libs directory
       - for packaged war file in console (cmd) run: java -jar ./bookstore-0.1.0-boot.war
3. In the browser open http://localhost/bookstore/ or http://localhost/bookstore/index.html 

## API description
1. Api call example for POST: localhost/bookstore/api/books?date=2020-06-12T04:30:45.123Z   
                        GET: localhost/bookstore/api/books
                             localhost/bookstore/api/mock  
           
2. Swagger documentation: localhost/bookstore/swagger-ui.html

## Build and run docker image

$ .\gradlew bootWar
$ docker build . -t bookstore:spring-boot-docker
$ docker run --publish=8080:8080 bookstore:spring-boot-docker
