#FROM openjdk:8-jdk-alpine
# Unofficial immage
FROM adoptopenjdk:11-jre-hotspot
VOLUME /tmp
COPY target/spring-cloud-k8s-gateway-service-*.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=9999 -jar /app.jar
