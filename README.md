# Spring cloud kubernetes example

This project is a tutorial about creating 2 kubernetes micro-service based on Quarkus.

**Dummy Service** : Simple quarkus project with a basic root endpoint.  
**Gateway service** : //TODO

## 1. Prerequisites
* Docker daemon
* kind installed (Minikube could do the job also)
* kubectl cli installed
* maven cli installed
* java 11 installed

You will find the prerequisites installation [instructions here](utils/setup-tools/README.md)

## 2. Setup kubernetes cluster

### 2.1. create kind kubernetes cluster
```bash
./utils/0-create-kind-cluster.sh
```

This script does the following tasks :
* run registry:2 container (local image repository used by our local k8s cluster)
* create our local k8s cluster named spring-kube using config for handling docker registry and portMapping
* create Contour ingress controller
* create kind patch to forward the hostPorts to the ingress controller

### 2.2. create role
```bash
kubectl delete -f 1-namespace-reader-role.yaml
kubectl create -f 1-namespace-reader-role.yaml
```

## 3. Setup Dummy service

You will find the [instructions here](dummy-service/README.md)

## 4. Setup Gateway service

You will find the [instructions here](gateway-service/README.md)

## 5. [Optional] Setup metric server

In order to use resources metric, please setup k8s metric server.
```bash
kubectl delete -f 2-metric-server.yml
kubectl create -f 2-metric-server.yml
```
Get pod cpu/memory usage
```bash
kubectl top pod
```

For more information about metric-server, check :
* https://kubernetes.io/docs/tasks/debug-application-cluster/resource-metrics-pipeline/
* https://github.com/kubernetes-sigs/metrics-server

