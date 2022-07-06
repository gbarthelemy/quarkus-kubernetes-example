# quarkus-k8s-dummy-service project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

This project has the following dependencies :
* quarkus-resteasy : JAX-RS resource in code (VS quarkus spring-web)
* quarkus-junit5 : unit test
* quarkus-kubernetes : create kubernetes.yml manifest when building
* quarkus-container-image-jib : create docker image when building

## 🧭 Prerequisites

* Docker daemon
* maven cli installed
* kubectl cli installed
* kind installed (or Minikube) and local kubernetes cluster running

You will find the [instructions here](../README.md)

## 1 Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
mvn clean quarkus:dev
```

## 🔧 Build

You can now choose to build OS specific image (as a Native binary) at [section 2.1](#2.1.-Native) or JVM-based image (as a java bytecode) at [section 2.2](#2.2-Jvm-(Non-native))

### 1. Native image

* Start time : 0.023s
* Pod memory usage : 5Mi
* Pod CPU(cores) : 1m
* Image size : 144MB
* App build time : 1 to 3 minutes

All operations are done on a 2,2 ghz intel core i7 quad-core.

cf https://quarkus.io/guides/building-native-image

#### 1.1.(A) [Option 1] Build native exe for your OS

Build time : 01:41 min

Note that the native executable generated will be specific to your operating system. To create an executable that will run in a container, use the following [Option 2].

#### 1.1.0 Prerequisites

Have graalvm and native-image installed. You will find the prerequisites installation [instructions here](../utils/markdown/PREREQUISITES.md)

#### 1.1.1 Build

Set JAVA_HOME : GraalVM 
```bash
export JAVA_HOME=/Users/guillaumebarthelemy/.sdkman/candidates/java/22.1.0.r17-grl/bin/java
```
Build Native exe
```bash
mvn clean package -Pnative
```

Executable file `target/quarkus-k8s-dummy-service-1.0-SNAPSHOT-runner` is generated and can be run:
````bash
./target/quarkus-k8s-dummy-service-1.0-SNAPSHOT-runner
````

#### 1.1.(B) [Option 2] Build native exe from docker container

/!\ In order to save time build, it is better not having containers running while building native.  

Since build requires a lot of memory, it is strongly advised to :
* set docker daemon memory resources to 8gb
* delete any running kind cluster while building (kind delete cluster --name=<cluster_name>).


Build time with default Ubi Quarkus builder image (22.0-java17) : 02:32 min
```bash
# Be sure docker is running 
mvn clean package -Pnative -Dquarkus.native.container-build=true
```

#### 1.2. Build native image from exe

You can include this part in the maven packaging step overriding properties (application.properties) :
* quarkus.container-image.build=true

```bash
docker build -f src/main/docker/Dockerfile.native -t localhost:5000/quarkus/quarkus-k8s-dummy-service:1.0-SNAPSHOT .
```

Since you chose to build Native binary image you can now skip 2.2 JVM (Non Native) section.

### 2.2 Jvm (Non native)

* Start time : 1.862s
* Pod memory usage : 83Mi
* Pod CPU(cores) : 2m
* Image size : 417MB
* App build time : ~9 seconds

All operations are done on a 2,2 ghz intel core i7 quad-core.

#### 2.2.1. Build jar 

```
mvn clean package
```

### 2.2.2. Build docker jvm image from jar

You can include this part in the maven packaging step overriding properties (application.properties) :
* quarkus.container-image.build=true

```bash
docker build -f src/main/docker/Dockerfile.jvm -t localhost:5000/quarkus/quarkus-k8s-dummy-service:1.0-SNAPSHOT .
```


## 🚀Run

### 0. Prerequisites

* kind cluster running (you will find [instructions here](../README.md#2.1.-create-kind-kubernetes-cluster))

### 1. Push images to the Kind repository

```bash
docker push localhost:5000/quarkus/quarkus-k8s-dummy-service:1.0-SNAPSHOT
```

### 2. Run the app

Using the generated
```bash
kubectl delete -f target/kubernetes/kubernetes.yml;
kubectl create -f target/kubernetes/kubernetes.yml
```

## 🔎 Check performance

Check boostrap time
```bash
# Get <pod_name>
kubectl get pods
# Get pod bootstrap time replacing <pod_name>
kubectl logs <pod_name> | grep started
```

Check memory footprint
```bash
kubectl top pods
```

For more informations about performances : 
* https://quarkus.io/blog/runtime-performance/
