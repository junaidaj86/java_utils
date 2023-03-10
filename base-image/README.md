## Login into Amazon ECR first to private registry

    aws ecr get-login-password --region eu-west-1 | docker login --username AWS --password-stdin 043970611749.dkr.ecr.eu-west-1.amazonaws.com

## Build manually openjdk11-jre-headless-maven-3.8.1

    docker build -f Dockerfile-11 -t ndm/java11-maven-base .
    docker tag ndm/java11-maven-base:latest 043970611749.dkr.ecr.eu-west-1.amazonaws.com/ndm/java11-maven-base:latest
    docker push 043970611749.dkr.ecr.eu-west-1.amazonaws.com/ndm/java11-maven-base:latest


## Build manually openjdk17-jre-headless-maven-3.9.0

    docker build -f Dockerfile-17 -t ndm/java17-maven-base .
    docker tag ndm/java17-maven-base:latest 043970611749.dkr.ecr.eu-west-1.amazonaws.com/ndm/java17-maven-base:latest
    docker push 043970611749.dkr.ecr.eu-west-1.amazonaws.com/ndm/java17-maven-base:latest



## Login into Amazon ECR first to public registry

    aws ecr-public get-login-password --region us-east-1 | docker login --username AWS --password-stdin public.ecr.aws/a1t5b6y5

## Build manually openjdk11-jre-headless-maven-3.8.1

    docker build -f Dockerfile-11 -t ndm/java11-maven-base .
    docker tag ndm/java11-maven-base:latest public.ecr.aws/a1t5b6y5/ndm/java11-maven-base:latest
    docker push public.ecr.aws/a1t5b6y5/ndm/java11-maven-base:latest

## Build manually openjdk17-jre-headless-maven-3.9.0

    docker build -f Dockerfile-17 -t ndm/java17-maven-base .
    docker tag ndm/java17-maven-base:latest public.ecr.aws/a1t5b6y5/ndm/java17-maven-base:latest
    docker push public.ecr.aws/a1t5b6y5/ndm/java17-maven-base:latest