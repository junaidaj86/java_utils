# Problem

Problem: Common Problem Jar File For IOT API's

## Description

This library contains the common problem error model MUST be used to
communicate error responses to our REST based API clients. This library is a
realization of API Design rule (MUST use Problem JSON). RFC 7807 defines the
Problem error model format.

## Known Issues

If running this library in **native** mode GraalVM has issues when
attempting to serialize the Problem contained in a JAX-RS Response.
You may see a problem response returned as the following when running natively.

```json
{}
```

In order to correct the issue above for a service, that uses this library for
**native** purposes, you MUST do the following:

1. Add the following file to inform the Quarkus native compiler to prepare
   the problem class from this common library for return from your API. This
   file must be specified in location `src/main/resources/reflection-config.json`.

```json
[
    {
      "name" : "com.postnord.ndm.api.common.problem.Problem",
      "allPublicMethods" : true
    }
]
```
