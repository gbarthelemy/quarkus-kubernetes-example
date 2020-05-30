#!/bin/sh

set -o errexit

# Dummy service (JVM jar)
cd dummy-service
mvn clean package -Dquarkus.container-image.build=true
docker tag guillaumebarthelemy/quarkus-k8s-dummy-service:1.0-SNAPSHOT localhost:5001/guillaumebarthelemy/quarkus-k8s-dummy-service:1.0-SNAPSHOT
cd ..

# Gateway service
cd gateway-service
mvn clean package -Pnative -Dquarkus.native.container-build=true -Dquarkus.native.builder-image=quay.io/quarkus/ubi-quarkus-native-image:20.1.0-java11
docker tag guillaumebarthelemy/quarkus-k8s-gateway-service:1.0-SNAPSHOT localhost:5001/guillaumebarthelemy/quarkus-k8s-gateway-service:1.0-SNAPSHOT
cd ..

# Push to container registry
docker push localhost:5001/guillaumebarthelemy/quarkus-k8s-dummy-service:1.0-SNAPSHOT
docker push localhost:5001/guillaumebarthelemy/quarkus-k8s-gateway-service:1.0-SNAPSHOT
