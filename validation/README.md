# Validation

Validation: Common Validation Jar File For NDM API's

## Description

This library contains common API parameter validator's that can be used to
sanitise and check API input parameters, not checking API input parameters
was identified as one of the most common attack vectors on API's by OWASP
in 2019. This library intends to help developers mitigate
to fix the following attacks:

* [OWASP API Mass Assignment](
  https://apisecurity.io/encyclopedia/content/owasp/api6-mass-assignment.htm)
* [OWASP API Inject](
  https://apisecurity.io/encyclopedia/content/owasp/api8-injection.htm)

All validation violations will be returned in NDM's RFC 7807 compatible Problem
error model format.
