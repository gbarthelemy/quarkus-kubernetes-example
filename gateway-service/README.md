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

### 2.1. Native

* Start time : 0.115s
* Pod memory usage : 9Mi
* Pod CPU(cores) : 1m
* Image size : 196MB
* App build time : 3 to 8 minutes

cf https://quarkus.io/guides/building-native-image

#### 2.1.1.(A) [Option 1] Build native exe for your OS

Build time : 03:33 min

Note that the native executable generated will be specific to your operating system. To create an executable that will run in a container, use the following [Option 2].

#### 2.1.1.0 Prerequisites

1 Install graalvm using [homebrew](https://github.com/graalvm/homebrew-tap)
```bash
brew cask install graalvm/tap/graalvm-ce-java11
brew cask install graalvm/tap/graalvm-ce-lts-java11
xattr -r -d com.apple.quarantine /Library/Java/JavaVirtualMachines/graalvm-ce-*
``` 
2 On OSX : xcode dependencies 
```bash
xcode-select --install
```
2 On Linux : GCC glibc and zlib 
```bash
# dnf (rpm-based)
sudo dnf install gcc glibc-devel zlib-devel
# Debian-based distributions:
sudo apt-get install build-essential libz-dev zlib1g-dev
```

Install the native-image tool using gu install
```bash
/Library/Java/JavaVirtualMachines/graalvm-ce-java11-21.0.0/Contents/Home/bin/gu install native-image
```
OR
```bash
gu install native-image
```

#### 2.1.1.1 Build

Set JAVA_HOME : GraalVM 
```bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/graalvm-ce-java11-21.0.0.2/Contents/Home
```
Build Native exe
```bash
mvn clean package -Pnative
```

#### 2.1.1.(B) [Option 2] Build native exe from docker container

/!\ You will notice that in order to save time build, it is better not to have containers running while building native.  

Since build requires a lot of memory, it is strongly advised to :
* set docker daemon memory resources to 8gb
* delete any running kind cluster while building (kind delete cluster --name=<cluster_name>).


Build time with default Ubi Quarkus builder image (21.0.0-java11) : 04:40 min
```bash
mvn clean package -Pnative -Dquarkus.native.container-build=true
```

#### 2.1.2. Build docker native image from exe

You can include this part in the maven packaging step (2.1.1) overriding properties (application.properties) :
* quarkus.container-image.build=true

```bash
docker build -f src/main/docker/Dockerfile.native -t localhost:5000/quarkus/quarkus-k8s-gateway-service:1.0-SNAPSHOT .
```

### 2.2.3. Push to the kind repository

```bash
docker push localhost:5000/quarkus/quarkus-k8s-gateway-service:1.0-SNAPSHOT
```

### 2.2 Jvm (Non native)

* Start time : 2.079 seconds
* Pod memory usage : 115Mi
* Pod CPU(cores) : 2m
* Image size : 400MB
* App build time : ~10 seconds

#### 2.2.1. Build jar 

Build time : 
```
mvn clean package
```

### 2.2.2. Build docker jvm image from jar

You can include this part in the maven packaging step overriding properties (application.properties) :
* quarkus.container-image.build=true

```bash
docker build -f src/main/docker/Dockerfile.jvm -t localhost:5000/quarkus/quarkus-k8s-gateway-service:1.0-SNAPSHOT .
```

### 2.2.3. Push the image to the kind repository

```bash
docker push localhost:5000/quarkus/quarkus-k8s-gateway-service:1.0-SNAPSHOT
```

## 3. Create configmap and secret

#### 3.1. create configmap

```bash
kubectl delete -f quarkus-gateway-service-configmap.yml
kubectl create -f quarkus-gateway-service-configmap.yml
```

## 4. Run

### 4.0. Prerequisites

* kind cluster running (you will find [instructions here](../README.md#2.1.-create-kind-kubernetes-cluster))

### 4.1. Run the app

Using the generated
```bash
kubectl delete -f target/kubernetes/kubernetes.yml
kubectl create -f target/kubernetes/kubernetes.yml
```

## 5. Check performance

Check boostrap time
```bash
kubectl logs <pod_name> | grep started
```

Check memory footprint
```bash
kubectl top pods
```

For more informations about performances : 
* https://quarkus.io/blog/runtime-performance/


## 6. Create Ingress controller

In order to map incoming http traffic to gateway-service, run the following :
```bash
kubectl delete -f api-ingress.yml
kubectl create -f api-ingress.yml
```

Since your kind cluster has :
 * extra config for portMapping
 * a Contour ingress controller deployed
 * a kind patch to forward the hostPorts to the ingress controller

You don't need anymore port-forward to call your gateway app. 
```bash
curl localhost
```

## 7. Test API

Test Gateway API using /dummy endpoint (which internally call dummy service)
```bash
curl localhost:80/dummy
```

Test Gateway API /configmap endpoint (which internally call dummy service)
```bash
curl localhost:80/configmap
```

//TODO
Test Gateway API /secret endpoint (which internally get message from configmap)
```bash
curl localhost:80/secret
```