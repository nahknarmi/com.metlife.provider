#!/usr/bin/env bash

gcloud container clusters create dev-cluster --num-nodes=3 --zone=australia-southeast1-a
kubectl run provider --image=asia.gcr.io/nahknarmi-190508/com.metlife.provider --port 8080

kubectl apply -f scripts/gcloud/config/dev.namespace.yml
kubectl apply -f scripts/gcloud/config/prod.namespace.yml