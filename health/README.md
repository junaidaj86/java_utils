# Health

This project is a common library for Quarkus based applications. The library
exposes health related endpoints as described in
[website](https://quarkus.io/guides/microprofile-health)

If you want to learn about Quarkus, please visit [website](https://quarkus.io).

## Usage

Applications can use the library by adding the maven dependency and declaring
the index dependency in the application configuration file.

## Maven dependency

```xml
<dependency>
    <groupId>com.postnord.ndm.base</groupId>
    <artifactId>health</artifactId>
    <version>add version</version>
</dependency>
```

The staging build generates the version.

## Application.yaml

In application.yaml file below lines need to be added.

```yaml
quarkus:
  index-dependency:
    health:
      group-id: com.postnord.ndm.base
      artifact-id: health
com:
  postnord:
    ndm:
      base:
        health:
          log-category: <log category for health related events>
```
