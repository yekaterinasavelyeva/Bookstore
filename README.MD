## Running local DB

1. Need to install Docker
2. In terminal go to /docker folder 
3. Start docker instance with initialized communication_local_db by running `docker-compose up --build`
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
