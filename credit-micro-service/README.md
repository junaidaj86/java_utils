
OpenJDK 17  
Apache Maven 3.8.1
Quarkus 2.16.4-Final
Strimzi Kafka
PostgreSQL 13.4



     docker run -d --rm -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=password -e POSTGRES_DB=my_db -p 5432:5432 postgres:13.4-alpine


     docker-compose -f local-kafka.yaml up -d
     docker-compose -f local-kafka.yaml down



```bash
mvn clean compile quarkus:dev
```


```bash
mvn clean package

java -jar target/quarkus-app/quarkus-run.jar
```


```bash
mvn clean test
```


```bash
curl -i -X POST http://localhost:8080/credit -H "Content-Type: application/json" -d "{\"ownerName\":\"aaa\",\"cardNumber\":\"1111-2222-3333-4444\",\"expirationMonth\":3,\"expirationYear\":1978,\"securityCode\":200,\"availableCredit\":60000}"

curl -i -X GET http://localhost:8080/credit -H "Content-Type: application/json"
```
