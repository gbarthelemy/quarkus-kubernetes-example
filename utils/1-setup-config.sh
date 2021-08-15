#!/bin/sh

kubectl delete -f ../gateway-service/api-ingress.yml
kubectl create -f ../gateway-service/api-ingress.yml
