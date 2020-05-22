#!/bin/sh

set -o errexit

# Dummy service (JVM jar)
cd dummy-service
mvn clean package -Dquarkus.container-image.build=true
docker tag guillaumebarthelemy/quarkus-k8s-dummy-service:1.0-SNAPSHOT localhost:5001/guillaumebarthelemy/quarkus-k8s-dummy-service:1.0-SNAPSHOT
cd ..

# Gateway service
cd gateway-service
mvn clean install
docker build -t localhost:5001/gateway-service .

# Push to container registry
docker push localhost:5001/guillaumebarthelemy/quarkus-k8s-dummy-service:1.0-SNAPSHOT
docker push localhost:5001/gateway-service:latest
