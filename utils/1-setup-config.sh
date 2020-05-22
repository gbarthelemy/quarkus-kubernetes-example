#!/bin/sh

kubectl delete -f 1-namespace-reader-role.yaml
kubectl create -f 1-namespace-reader-role.yaml

kubectl delete -f gateway-service/1-gateway-service-configmap.yaml
kubectl create -f gateway-service/1-gateway-service-configmap.yaml

kubectl delete -f gateway-service/2-gateway-service-secret.yaml
kubectl create -f gateway-service/2-gateway-service-secret.yaml
