#!/usr/bin/env bash

gcloud container clusters delete dev-cluster --zone=australia-southeast1-a

kubectl delete namespace prod
kubectl delete namespace dev