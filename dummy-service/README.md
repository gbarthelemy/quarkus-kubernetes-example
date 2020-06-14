# quarkus-k8s-dummy-service project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

This project has the following dependencies :
* quarkus-resteasy : JAX-RS resource in code (VS quarkus spring-web)
* quarkus-junit5 : unit test
* quarkus-kubernetes : create kubernetes.yml manifest when building
* quarkus-container-image-jib : create docker image when building

## 0. Prerequisites

* Docker daemon
* kind installed (Minikube could do the job also)
* kubectl cli installed
* maven cli installed
* Java 11 installed
* local kubernetes cluster running

You will find the [instructions here](../README.md)

## 1 Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
mvn clean quarkus:dev
```

## 2 Build the app docker image

### 2.1. Native

* Start time : 0.017s
* Pod memory usage : 4Mi
* Pod CPU(cores) : 1m
* Image size : 164MB
* App build time : 1 to 3 minutes

cf https://quarkus.io/guides/building-native-image

#### 2.1.1. [Option 1] Build native exe for your OS

Build time : 01:38 min

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
/Library/Java/JavaVirtualMachines/graalvm-ce-java11-20.1.0/Contents/Home/bin/gu install native-image
```

#### 2.1.1.1 Build

Set JAVA_HOME : GraalVM 
```bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/graalvm-ce-java11-20.1.0/Contents/Home
```
Build Native exe
```bash
mvn clean package -Pnative
```

#### 2.1.1. [Option 2] Build native exe from docker container

/!\ You will notice that in order to save time build, it is better not to have containers running while building native.  

Since build requires a lot of memory, it is strongly advised to :
* set docker daemon memory resources to 8gb
* delete any running kind cluster while building (kind delete cluster --name=<cluster_name>).


Build time with default properties: 02:55 min
```bash
mvn clean package -Pnative -Dquarkus.native.container-build=true
```

Build time with image 20.1.0-java11 : 02:31 min
```bash
mvn clean package -Pnative -Dquarkus.native.container-build=true -Dquarkus.native.builder-image=quay.io/quarkus/ubi-quarkus-native-image:20.1.0-java11
```

#### 2.1.2. Build docker native image from exe

You can include this part in the maven packaging step overriding properties (application.properties) :
* quarkus.container-image.build=true

```bash
docker build -f src/main/docker/Dockerfile.native -t localhost:5001/guillaumebarthelemy/quarkus-k8s-dummy-service:1.0-SNAPSHOT .
```

### 2.2.3. Push to the kind repository

```bash
docker push localhost:5001/guillaumebarthelemy/quarkus-k8s-dummy-service:1.0-SNAPSHOT
```

### 2.2 Jvm (Non native)

* Start time : 1.629s
* Pod memory usage : 64Mi
* Pod CPU(cores) : 4m
* Image size : 510MB
* App build time : ~5 seconds

#### 2.2.1. Build jar 

Build time : 5.287 s
```
mvn clean package
```

### 2.2.2. Build docker jvm image from jar

You can include this part in the maven packaging step overriding properties (application.properties) :
* quarkus.container-image.build=true

```bash
docker build -f src/main/docker/Dockerfile.jvm -t localhost:5001/guillaumebarthelemy/quarkus-k8s-dummy-service:1.0-SNAPSHOT .
```

### 2.2.3. Push the image to the kind repository

```bash
docker push localhost:5001/guillaumebarthelemy/quarkus-k8s-dummy-service:1.0-SNAPSHOT
```

## 3. Run

### 3.0. Prerequisites

* kind cluster running (you will find [instructions here](../README.md#2.1.-create-kind-kubernetes-cluster))

### 3.1. Run the app

Using the generated
```bash
kubectl delete -f target/kubernetes/kubernetes.yml
kubectl create -f target/kubernetes/kubernetes.yml
```

## 4. Check performance

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
