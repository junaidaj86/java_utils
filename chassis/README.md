# Chassis Module

## Overview

The Chassis module is based on the `Maven Archetype Plugin`.
It allows user to create maven project from an existing template.
It also allows the user to create an archetype from an existing project.
For more info:
[Maven archetype plugin](
https://maven.apache.org/archetype/maven-archetype-plugin/)

## Usage

1. Pull latest code for NDM repo.
2. Go to base directory (e.g. `cd ..`) and run `mvn clean install`
3. Make a new directory for new Maven project (e.g new microservice).
   For example marty_is_superb (or cd to any directory without a `pom.xml` in it)
4. Go to new directory (e.g.`cd hamed_artifact`)
5. Generate the project

```bash
   mvn archetype:generate \
       -DarchetypeArtifactId=chassis \
       -DarchetypeGroupId=com.postnord.ndm.base \
       -DarchetypeVersion=0.0.1-SNAPSHOT \
       -DartifactId=hamed_artifact \
       -DgroupId=com.postnord.ndm
```
<!-- markdownlint-disable MD029 -->

6. Provide all params according to need

<!-- markdownlint-enable MD029 -->
## Technology

The generated application(maven project) is based on the Quarkus Java framework
and it uses PostgreSQL DB when running in standalone mode. Quarkus Component tests
are executed against Containerized PostgreSQL DB on the fly.

If you want to learn about Quarkus, please visit [website](https://quarkus.io).
