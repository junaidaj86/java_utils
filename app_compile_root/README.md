# Compile root for applications

This project includes a parent pom for all Quarkus based applications. This
structure ensures that all applications use same plugins and versions of
external libraries.

If you want to learn about Quarkus, please visit [website](https://quarkus.io).

## Usage

The applications can use the parent pom file declared in this project by
declaring it as parent in their own pom files.

Example:

```xml
<parent>
    <groupId>com.postnord.ndm.base</groupId>
    <artifactId>app-compile-root</artifactId>
    <version>add version</version>
</parent>
```

The staging build generates the version of all base projects.

## Liquibase change log handling

This section describes how to handle the Liquibase change log file initial
and updates for projects. The Liquibase plugin configuration is defined in the
common compile root.

### Start up the local database environment

```bash
cd ${IOTA_REPO}/tests/environment/local
./run.sh start_db
```

### Project setup

Add the following Quarkus profile configuration to the file `application.yaml`.

```yaml
"%dev-db":
  quarkus:
    datasource:
      username: nwcoveragemanager
      jdbc:
        url: jdbc:postgresql://localhost:25432/nwcoveragemanager
    hibernate-orm:
      database:
        generation: drop-and-create
        default-schema: nwcoveragemanager_dev
      log:
        sql: true
    liquibase:
      migrate-at-start: false
```

The example above shows the Quarkus profile with configuration values for
Network Coverage Manager.
For another project change the username, password, url and default-schema to the
project specific values.

### Configure the password for the profile 'dev-db'

Add a file `.env` to the project root  with the password for the local database.

```properties
_DEV_DB_QUARKUS_DATASOURCE_PASSWORD = __LOCAL_PASSWORD_HERE__
```

### Configure Maven for the local environment setup

Add for each service project a profile for the local configuration values into
the file `${HOME}/.m2/settings.xml`.

The example below shows the profile with configuration values for Network
Coverage Manager.
Add similar profiles for the other services.

```xml
<profile>
    <id>network-coverage-manager</id>
    <properties>
        <liquibase.db.url>
            jdbc:postgresql://localhost:25432/nwcoveragemanager
        </liquibase.db.url>
        <liquibase.db.username>nwcoveragemanager</liquibase.db.username>
        <liquibase.db.password>__LOCAL_PASSWORD_HERE__</liquibase.db.password>
        <liquibase.db.dev-schema>nwcoveragemanager_dev</liquibase.db.dev-schema>
    </properties>
</profile>
```

The password for the local environment can be found in the runtime configuration
file `${IOTA_REPO}/tests/environment/local/.env`.

### Create the initial Liquibase change log file for your project

Create the top level change log file `src/main/resources/db/changeLog.yaml`.

```yaml
databaseChangeLog:
  - include:
      file: db/changeLog.baseline.yaml
```

Run the Quarkus application in dev mode. This will make Hibernate to deploy the
development database schema based on the current entity classes in our project.

 ```bash
mvn \
    quarkus:dev \
    -Dquarkus.profile=dev-db
 ```

Create the initial change log file based on the database objects in the
development database schema.

__Note:__ In all of the examples below, exchange the profile
`-Pnetwork-coverage-manager` to reflect profile for your own project as
configured in your `${HOME}/.m2/settings.xml`.

```bash
mvn \
    liquibase:generateChangeLog@gen-baseline \
    -Pliquibase \
    -Pnetwork-coverage-manager
```

Move the generated file into the baseline change log file.

```bash
mv \
    target/changeLog.generated.yaml \
    src/main/resources/db/changeLog.baseline.yaml
```

Verify the content of `src/main/resources/db/changeLog.baseline.yaml`.
Edit if needed. For example change the 'id' to baseline-1, baseseline-2, etc.

Drop the database objects from the runtime schema.

```bash
mvn \
    liquibase:dropAll@drop-runtime \
    -Pliquibase \
    -Pnetwork-coverage-manager
```

Deploy runtime database schema with Liquibase

```bash
mvn \
    liquibase:update \
    -Pliquibase \
    -Pnetwork-coverage-manager
```

Validate that there are no differences between the development schema and the
runtime schema.

```bash
mvn \
    liquibase:diff@dev-diff \
    -Pliquibase \
    -Pnetwork-coverage-manager
```

- Expected message: `INFO: changeSets count: 0`

- Expected content of file `target/changeLog.diff.yaml`:\
  `databaseChangeLog: []`

### Update the Liquibase change logs

Clean the database schemas if needed

```bash
mvn \
    liquibase:dropAll@drop-runtime \
    -Pliquibase \
    -Pnetwork-coverage-manager
mvn \
    liquibase:dropAll@drop-dev \
    -Pliquibase \
    -Pnetwork-coverage-manager
```

Deploy the runtime database schema with the current Liquibase change logs

```bash
mvn \
    liquibase:update \
    -Pliquibase \
    -Pnetwork-coverage-manager
```

Run the Quarkus application in dev mode. This will make Hibernate to deploy the
development database schema based on the current entity classes in our project.

 ```bash
 mvn \
    clean \
    quarkus:dev \
    -Dquarkus.profile=dev-db
 ```

Create a new diff file `target/changeLog.diff.yaml`.

```bash
mvn \
    liquibase:diff@dev-diff \
    -Pliquibase \
    -Pnetwork-coverage-manager
```

Move the generated file into a change log update file.

```bash
mv \
    target/changeLog.diff.yaml \
    src/main/resources/db/changeLog.update-1.yaml
```

Verify the content of `src/main/resources/db/changeLog.update-1.yaml`

Edit if needed. For example change the 'id' to update-1-1, update-1-2, etc.

Inlcude the new change log update file into the top-level change log file.
Edit file `src/main/resources/db/changeLog.yaml`:

```yaml
databaseChangeLog:
  - include:
      file: db/changeLog.baseline.yaml
  - include:
      file: db/changeLog.update-1.yaml
```

Deploy runtime database schema with the new Liquibase change log.

```bash
mvn \
    liquibase:update \
    -Pliquibase \
    -Pnetwork-coverage-manager
```

Validate that there are no differences between the development schema and the
runtime schema.

```bash
mvn \
    liquibase:diff@dev-diff \
    -Pliquibase \
    -Pnetwork-coverage-manager
```

- Expected message: `INFO: changeSets count: 0`

- Expected content of file `target/changeLog.diff.yaml`:\
  `databaseChangeLog: []`
