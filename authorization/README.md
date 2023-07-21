# Authorization

**Authorization:** Common Authorization Jar File For NDM API's

## Description

This library simplifies authorization handling and error response propagation
within your micro-service to our REST API clients.
The library **MUST** only be used with **JAX-RS** compatible microservices
as the common exception mappers interact with the JAX-RS runtime. 

## Usage

Applications can use the library by adding the maven dependency and declaring
the index dependency in the application configuration file.

## Maven dependency

```xml
<dependency>
    <groupId>com.postnord.ndm.base</groupId>
    <artifactId>authorization</artifactId>
    <version>add version</version>
</dependency>
```

The staging build generates the version.

## Application.yaml

In application.yaml file below lines need to be added.

```yaml
quarkus:
  index-dependency:
    authorization:
      group-id: com.postnord.ndm.base
      artifact-id: authorization

ndm_jwt:
  claims: roles,exp,upn
  roles-allowed: ${GPS_ROLES:Administrator,GlobalAdministrator,Manager,Intelligence,Directory.Read.All}
```
