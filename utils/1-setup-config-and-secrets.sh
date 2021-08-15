#!/bin/sh

kubectl delete -f ../gateway-service/api-ingress.yml
kubectl create -f ../gateway-service/api-ingress.yml

kubectl create secret generic the-secrets \
        --from-literal=username=admin \
        --from-literal=password=secret