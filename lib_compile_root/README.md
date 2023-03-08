# Compile root for libraries

This project includes a parent pom for all common Quarkus based libraries. It
is also parent pom for app-compile-root, which in turn is used as parent in all
Quarkus based applications. This structure ensures that all common libraries
and all applications use same versions of external libraries.

If you want to learn about Quarkus, please visit [website](https://quarkus.io).

## Usage

The common base libraries can use the parent pom file declared in this project
by declaring it as parent in their own pom files.

Example:

```xml
<parent>
    <groupId>com.postnord.ndm.base</groupId>
    <artifactId>lib-compile-root</artifactId>
    <version>add version</version>
</parent>
```

The staging build generates the version of all base projects.