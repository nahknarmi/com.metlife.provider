#!/usr/bin/env bash

gcloud container clusters create nahknarmi --num-nodes=3 --zone=australia-southeast1-a
kubectl run provider --image=asia.gcr.io/nahknarmi-190508/com.metlife.provider --port 8080