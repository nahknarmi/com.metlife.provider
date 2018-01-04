#!/usr/bin/env bash

kubectl delete namespace prod
kubectl delete namespace dev

gcloud container clusters delete dev-cluster --zone=australia-southeast1-a