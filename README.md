# Quarkus kubernetes example

This project is a tutorial about creating 2 kubernetes micro-service based on Quarkus.

* **Dummy Service** : Simple quarkus project with a basic root endpoint.  
* **Gateway service** : Simple quarkus project, including the following features : 
  * inject properties from k8s configmap
  * inject properties from k8s secret
  * call dummy service from k8s client discovery 

## 1. Prerequisites
* docker daemon
* kind installed (Minikube could do the job)
* kubectl cli installed
* maven cli installed
* java 11 installed

You will find the prerequisites installation [instructions here](utils/setup-tools/README.md)

## 2. Setup kind kubernetes cluster

```bash
./utils/0-create-kind-cluster.sh
```

This script does the following tasks :
* run registry:2 container (local image repository used by our local k8s cluster)
* create our local k8s cluster named quarkus-kube using config for handling docker registry and portMapping
* create Contour ingress controller
* create kind patch to forward the hostPorts to the ingress controller

## 3. Setup Dummy service

You will find the [instructions here](dummy-service/README.md)

## 4. Setup Gateway service

You will find the [instructions here](gateway-service/README.md)
