# JWT REST Mapper

This project is a common library for Quarkus based applications to
transparently map JWT roles to permissions using IAM REST API. It implements
the 'RoleMapper' interface from 'jwt-handler' library.

If you want to learn about Quarkus, please visit [website](https://quarkus.io).

## Usage

Add the dependency and specify the 'com.postnord.ndm.base.jwt_rest_mapper.'
prefixed properties from 'src/test/resources/application.properties' file in
your application. Please refer to the 'jwt-handler' library for more
information.

## Maven dependency

```xml
<dependency>
    <groupId>com.postnord.ndm.base</groupId>
    <artifactId>jwt-rest-mapper</artifactId>
    <version>add version</version>
</dependency>
```

The staging build generates the version.
