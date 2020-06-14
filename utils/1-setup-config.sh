#!/bin/sh

kubectl delete -f gateway-service/quarkus-gateway-service-configmap.yml
kubectl create -f gateway-service/quarkus-gateway-service-configmap.yml
