###  Run Postgres docker container

     docker run -d --rm -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=password -e POSTGRES_DB=my_db -p 5432:5432 postgres:13.4-alpine

### Add a movie

     curl -i -X POST "http://localhost:8080/movies" -H "Content-Type: application/json" -H "Accept: application/json" -d '{"title":"movieTitle","description":"movieDescription"}'

### Get all movies back

     curl -i -X GET "http://localhost:8080/movies" -H "Content-Type: application/json"

### Run the Application

    mvn compile quarkus:dev