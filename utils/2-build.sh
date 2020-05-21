#!/bin/sh

set -o errexit

# Dummy service
cd dummy-service
mvn clean package -Dquarkus.container-image.build=true
cd ..

# Gateway service
cd gateway-service
mvn clean install
docker build -t localhost:5000/gateway-service .

# Push to container registry
docker push localhost:5000/guillaumebarthelemy/quarkus-k8s-dummy-service:1.0-SNAPSHOT
docker push localhost:5000/gateway-service:latest
