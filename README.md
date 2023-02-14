# Base

This project provides some base services for Quarkus based applications.
If you want to learn about Quarkus, please visit [website](https://quarkus.io).

## Compile root

The project includes two compile roots, one for common libraries and the other
one for applications. The compilation roots define pom files that include
common dependency management and plugin management, common profiles, and
plugins pre-configured and activated for the child projects.

## Common libraries

The project includes common libraries for applications. A dedicated project
contains each library, and each library can be individually included in the
applications' pom files. The libraries can depend on each other, and they
should define transitive dependencies in that case. The libraries may only use
external dependencies defined in the dependency management of the compilation
root pom.