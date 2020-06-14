#!/bin/sh

# Dummy service
kubectl delete -f dummy-service/target/kubernetes/kubernetes.yml
kubectl create -f dummy-service/target/kubernetes/kubernetes.yml

# Gateway service
kubectl delete -f gateway-service/target/kubernetes/kubernetes.yml
kubectl create -f gateway-service/target/kubernetes/kubernetes.yml
kubectl delete -f gateway-service/api-ingress.yml
kubectl create -f gateway-service/api-ingress.yml
