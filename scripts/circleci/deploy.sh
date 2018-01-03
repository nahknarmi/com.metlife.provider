#!/usr/bin/env bash

kubectl apply -f scripts/circleci/config/deployment-provider.yml
kubectl apply -f scripts/circleci/config/service-provider.yml