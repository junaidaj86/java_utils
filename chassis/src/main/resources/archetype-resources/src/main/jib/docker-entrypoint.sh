#!/usr/bin/env bash

set -euo pipefail

/usr/bin/java -jar -Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager quarkus-run.jar
