# Spring cloud kubernetes gateway service

This project is a gateway web-service and is based on Spring Cloud Kubernetes project. It is build as a jar and then embedded in openjdk docker image.

## 1. Prerequisites

* Docker daemon
* kind installed (Minikube could do the job also)
* kubectl cli installed
* maven cli installed
* Java 11 installed
* local kubernetes cluster running
* default serviceAccount have access to configmaps, secrets, pods, services

You will find the [instructions here](../README.md)

## 2. Build the application

### 2.1. Build the image manually

Build the app jar   
```bash
mvn clean install
```
Build the docker image named gateway-service   
```bash
docker build -t gateway-service .
```

### 2.2. Push the image to the kubernetes image repository

Tag the image to prepare upload   
```bash
docker tag gateway-service:latest localhost:5000/gateway-service:latest
```
Push the image to the kubernetes repository
```bash
docker push localhost:5000/gateway-service:latest
```

## 3. Create configmap and secret

#### 3.1. create configmap

```bash
kubectl delete -f 1-gateway-service-configmap.yaml
kubectl create -f 1-gateway-service-configmap.yaml
```

#### 3.2. create secret

```bash
kubectl delete -f 2-gateway-service-secret.yaml
kubectl create -f 2-gateway-service-secret.yaml
```

## 4. Run 

### 4.1. Run the app

Create NodePort service and Deployment gateway-service
```bash
kubectl delete -f 3-gateway-service-deployment.yaml 
kubectl create -f 3-gateway-service-deployment.yaml 
```

### 4.2. Check state of the app

Check k8s service (NodePort) status
```bash
kubectl get services 
```
Check pods status
```bash
kubectl get pods 
```
You can get pod detailed information running the followings
```bash
kubectl describe pod <pod_name> 
# <pod_name> is returned in 'kubectl get pods' command 
```
```bash
kubectl logs <pod_name> 
# <pod_name> is returned in 'kubectl get pods' command 
```

## 5. Call the app

Create port forward
```bash
kubectl port-forward svc/gateway-service 8080:8080
```
Curl the actuator health endpoint to check service 
```bash
curl localhost:8080/actuator/health
```
Curl the gateway controller
```bash
curl localhost:8080/
```

## 6. Create Ingress controller

In order to map incoming http traffic to gateway-service, run the following :
```bash
kubectl delete -f 4-api-ingress.yml
kubectl create -f 4-api-ingress.yml
```

Since your kind cluster has :
 * extra config for portMapping
 * a Contour ingress controller deployed
 * a kind patch to forward the hostPorts to the ingress controller

You don't need anymore port-forward to call your gateway app. 
```bash
curl localhost
```
