#!/bin/sh

# Dummy service (JVM jar)
cd ../dummy-service || exit
mvn clean package -Pnative -Dquarkus.native.container-build=true
docker build -f src/main/docker/Dockerfile.native -t localhost:5000/quarkus/quarkus-k8s-gateway-service:1.0-SNAPSHOT .

# Gateway service (JVM jar)
cd ../gateway-service || exit
mvn clean package -Pnative -Dquarkus.native.container-build=true
docker build -f src/main/docker/Dockerfile.native -t localhost:5000/quarkus/quarkus-k8s-gateway-service:1.0-SNAPSHOT .

# Push to container registry
docker push localhost:5000/quarkus/quarkus-k8s-dummy-service:1.0-SNAPSHOT
docker push localhost:5000/quarkus/quarkus-k8s-gateway-service:1.0-SNAPSHOT
