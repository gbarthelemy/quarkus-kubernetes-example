# quarkus-k8s-gateway-service project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

This project has the following dependencies :
* quarkus-spring-web : JAX-RS resource in code (Spring way) (VS quarkus-resteasy)
* quarkus-rest-client : define rest client in code
* quarkus-jackson : jackson de-serialization
* quarkus-kubernetes-config : use kubernetes configmap
* quarkus-kubernetes-client : discover kubernetes services
* quarkus-kubernetes : create kubernetes.yml manifest when building
* quarkus-container-image-jib : create docker image when building
* quarkus-junit5 : unit test

## 0. Prerequisites

* Docker daemon
* kind installed (Minikube could do the job also)
* kubectl cli installed
* maven cli installed
* Java 11 installed
* local kubernetes cluster running

You will find the [instructions here](../README.md)

## 1 Run the application in dev mode

You can run your application in dev mode that enables live coding using:
```
mvn clean quarkus:dev
```

## 2 Build the app docker image

You can now choose to build OS specific image (as a Native binary) at [section 2.1](#2.1-native) or JVM-based image (as a java bytecode) at [section 2.2](#2.2-Jvm-(Non-native)).

### 2.1 Native 

* Start time : 0.044s
* Pod memory usage : 8Mi
* Pod CPU(cores) : 1m
* Image size : 176MB
* App build time : 3 to 5 minutes

All operations are done on a 2,2 ghz intel core i7 quad-core.

cf https://quarkus.io/guides/building-native-image

#### 2.1.1(A) [Option 1] Build native exe for your OS

Build time : 02:46 min

Note that the native executable generated will be specific to your operating system. To create an executable that will run in a container, use the following [Option 2].

##### 2.1.1.0 Prerequisites

##### OSX
1 Install graalvm using [homebrew](https://github.com/graalvm/homebrew-tap)
```bash
brew cask install graalvm/tap/graalvm-ce-java11
brew cask install graalvm/tap/graalvm-ce-lts-java11
xattr -r -d com.apple.quarantine /Library/Java/JavaVirtualMachines/graalvm-ce-*
```
You can also use sdkman...

2 On OSX : xcode dependencies 
```bash
xcode-select --install
```

##### Linux
1 On Linux : GCC glibc and zlib 
```bash
# dnf (rpm-based)
sudo dnf install gcc glibc-devel zlib-devel
# Debian-based distributions:
sudo apt-get install build-essential libz-dev zlib1g-dev
```
You can also use sdkman...

Install the native-image tool using `gu install cmd`
```bash
/Users/guillaumebarthelemy/.sdkman/candidates/java/current/bin/gu install native-image
```
OR
```bash
gu install native-image
```

##### 2.1.1.1 Build

Set JAVA_HOME : GraalVM 
```bash
export JAVA_HOME=/Users/guillaumebarthelemy/.sdkman/candidates/java/21.2.0.r11-grl/bin/java
```
Build Native exe
```bash
mvn clean package -Pnative
```

Executable file `target/quarkus-k8s-gateway-service-1.0-SNAPSHOT-runner` is generated but actually if you try to run it, it will crash because of Kubernetes specific code:

#### 2.1.1(B) [Option 2] Build native exe from docker container

/!\ In order to save time build, it is better not having containers running while building native.  

Since build requires a lot of memory, it is strongly advised to :
* set docker daemon memory resources to 8gb
* delete any running kind cluster while building (kind delete cluster --name=<cluster_name>).


Build time with default Ubi Quarkus builder image (22.0-java17) : 03:50 min
```bash
# Be sure docker is running 
mvn clean package -Pnative -Dquarkus.native.container-build=true
```

#### 2.1.2 Build docker native image from exe

You can include this part in the maven packaging step (2.1.1) overriding properties (in application.properties) :
* quarkus.container-image.build=true

```bash
docker build -f src/main/docker/Dockerfile.native -t localhost:5000/quarkus/quarkus-k8s-gateway-service:1.0-SNAPSHOT .
```

Since you chose to build Native binary image you can now skip 2.2 JVM (Non Native) section.


### 2.2 Jvm (Non native)

* Start time : 2.214 seconds
* Pod memory usage : 110Mi
* Pod CPU(cores) : 2m
* Image size : 401MB
* App build time : ~10 seconds

All operations are done on a 2,2 ghz intel core i7 quad-core.

#### 2.2.1 Build jar 

Build time : 10.555 s
```
mvn clean package
```

### 2.2.2 Build docker jvm image from jar

You can include this part in the maven packaging step overriding properties (application.properties) :
* quarkus.container-image.build=true

```bash
docker build -f src/main/docker/Dockerfile.jvm -t localhost:5000/quarkus/quarkus-k8s-gateway-service:1.0-SNAPSHOT .
```

## 3 Create configmap and secret

#### 3.1 create configmap

```bash
kubectl delete -f quarkus-gateway-service-configmap.yml
kubectl create -f quarkus-gateway-service-configmap.yml
```

#### 3.2 create secret

```bash
kubectl create secret generic the-secrets \
        --from-literal=username=admin \
        --from-literal=password=secret
```

## 4 Run

### 4.0 Prerequisites

* kind cluster running (you will find [instructions here](../README.md#2.1.-create-kind-kubernetes-cluster))


### 4.1 Push to the Kind repository

```bash
docker push localhost:5000/quarkus/quarkus-k8s-gateway-service:1.0-SNAPSHOT
```

### 4.2 Run the app

Using the generated
```bash
kubectl delete -f target/kubernetes/kubernetes.yml
kubectl create -f target/kubernetes/kubernetes.yml
```

## 5 Check performance

Check boostrap time
```bash
# Get pods name
kubectl get pods
# Get pod bootstrap time
kubectl logs <pod_name> | grep started
```

Check memory footprint
```bash
kubectl top pods
```

For more informations about performances : 
* https://quarkus.io/blog/runtime-performance/


## 7 Test API

```bash
curl localhost
```

Test Gateway API using /dummy endpoint (which internally call dummy service)
```bash
curl localhost/dummy
```

Test Gateway API /configmap endpoint (which internally get properties from configmap)
```bash
curl localhost/configmap
```

Test Gateway API /secret endpoint (which internally get secret from configmap)
```bash
curl localhost/secret
```
