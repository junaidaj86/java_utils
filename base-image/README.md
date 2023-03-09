## Build manual openjdk11-jre-headless-maven-3.8.1

    docker build -f Dockerfile-11 -t ndm/java11-maven-base .
    docker tag ndm/java11-maven-base:latest 043970611749.dkr.ecr.eu-west-1.amazonaws.com/ndm/java11-maven-base:latest
    docker push 043970611749.dkr.ecr.eu-west-1.amazonaws.com/ndm/java11-maven-base:latest


## Build manual openjdk17-jre-headless-maven-3.9.0

    docker build -f Dockerfile-17 -t ndm/java17-maven-base .
    docker tag ndm/java17-maven-base:latest 043970611749.dkr.ecr.eu-west-1.amazonaws.com/ndm/java17-maven-base:latest
    docker push 043970611749.dkr.ecr.eu-west-1.amazonaws.com/ndm/java17-maven-base:latest
