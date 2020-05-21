# quarkus-k8s-dummy-service project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## 1 Run the app locally
### Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

### Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `quarkus-k8s-dummy-service-1.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/quarkus-k8s-dummy-service-1.0-SNAPSHOT-runner.jar`.

### Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/quarkus-k8s-dummy-service-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.

## 2 Build the app docker image

### 2.1. Build the image

```
mvn package -Dquarkus.container-image.build=true
```

### 2.2. Push the image to the kubernetes image repository

Push the image to the kubernetes repository
```bash
docker push localhost:5000/guillaumebarthelemy/quarkus-k8s-dummy-service:1.0-SNAPSHOT
```

## 3. Run

### 3.1. Run the app

Deploy the app
```bash
kubectl delete -f target/kubernetes/kubernetes.yml
kubectl create -f target/kubernetes/kubernetes.yml
```
