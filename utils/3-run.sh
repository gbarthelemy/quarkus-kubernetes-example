#!/bin/sh

# Dummy service
kubectl delete -f dummy-service/target/kubernetes/kubernetes.yml
kubectl create -f dummy-service/target/kubernetes/kubernetes.yml

# Gateway service
kubectl delete -f gateway-service/3-gateway-service-deployment.yaml
kubectl create -f gateway-service/3-gateway-service-deployment.yaml
