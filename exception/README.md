# Exception

**Exception:** Common ExceptionMapper Jar File For NDM API's

## Description

This library simplifies exception handling and error response propagation
within your micro-service to our REST API clients.
The library **MUST** only be used with **JAX-RS** compatible microservices
as the common exception mappers interact with the JAX-RS runtime. This
library will handle most common JAX-RS exceptions namely:

* ForbiddenException
* NotAcceptedException
* NotAllowedException
* NotAuthorizedException
* NotFoundException
* NotSupportedException
* ServiceUnavailableException
* WebApplicationException

It will also handle NDM specific exceptions namely:

* ConstraintViolationExceptions (from our NDMValidators)
* APIExceptions (exception that can be thrown anywhere in a developer's
  code to return a well formatted error response to our API clients)

## Usage

Applications can use the library by adding the maven dependency and declaring
the index dependency in the application configuration file.

## Maven dependency

```xml
<dependency>
    <groupId>com.postnord.ndm.base</groupId>
    <artifactId>exception</artifactId>
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
      artifact-id: exception
com:
  postnord:
    ndm:
      base:
        exception:
          log-category: <log category for logs from exception mappers>
```
