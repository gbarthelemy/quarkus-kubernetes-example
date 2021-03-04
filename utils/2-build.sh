#!/bin/sh

# Dummy service (JVM jar)
cd dummy-service || exit
mvn clean package -Dquarkus.container-image.build=true
cd ..

# Gateway service (JVM jar)
cd gateway-service || exit
mvn clean package -Dquarkus.container-image.build=true
cd ..

# Push to container registry
docker push localhost:5001/guillaumebarthelemy/quarkus-k8s-dummy-service:1.0-SNAPSHOT
docker push localhost:5001/guillaumebarthelemy/quarkus-k8s-gateway-service:1.0-SNAPSHOT
