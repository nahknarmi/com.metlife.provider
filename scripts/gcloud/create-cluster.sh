#!/usr/bin/env bash

gcloud container clusters create dev-cluster --num-nodes=3 --zone=australia-southeast1-a
echo 'Cluster created'

#kubectl run provider --image=asia.gcr.io/nahknarmi-190508/com.metlife.provider --port 8080
#echo 'Cluster created'

kubectl apply -f scripts/gcloud/config/dev.namespace.yml
kubectl apply -f scripts/gcloud/config/prod.namespace.yml
echo 'Namespaces created'

kubectl create secret generic service-account-creds --from-file=scripts/gcloud/config/service-account-creds.json --namespace=dev
kubectl create secret generic service-account-creds --from-file=scripts/gcloud/config/service-account-creds.json --namespace=prod
echo 'Secrets mounted'

#gcloud endpoints services deploy scripts/gcloud/config/openapi.json