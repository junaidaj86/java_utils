# JWT Handler

This project is a common library for Quarkus based applications to extract the
'resource_access' claim from the JWT. The library integrates with Quarkus'
native JWT handling and the extraction is transparent to the application.

If you want to learn about Quarkus, please visit [website](https://quarkus.io).

## Usage

Applications can use the '@RolesAllowed' annotation to require specific
permissions for the endpoints. The `AllowedAccounts` bean will automatically
contain the IDs of the organization accounts that passed the permission check.
It can be injected in the application and used to filter access to the
resources based on their tenancy and possible access policies.

The 'AllowedAccounts' bean also includes a flag indicating if any of the
included organization accounts included the 'super user' permission.
Multi-tenancy filtering shall be skipped in that case.

## Role - permissions mapping

The library can handle the mapping from roles to permissions on behalf of the
application. The application need to provide an implementation of the
'RoleMapper' interface to enable this. There is a default implementation that
maps the roles to permissions without any conversions. This is useless in
most cases but needed to avoid exceptions if translation needs to be outside
this framework (for some reasons).

## Maven dependency

```xml
<dependency>
    <groupId>com.postnord.ndm.base</groupId>
    <artifactId>jwt-handler</artifactId>
    <version>add version</version>
</dependency>
```

The staging build generates the version.
