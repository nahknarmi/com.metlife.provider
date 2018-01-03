#!/usr/bin/env bash

#template substitution
cat scripts/circleci/config/deployment-provider.yml | sed -e "s/\${GIT_HASH}/${CIRCLE_SHA1}/" -e "s/\${SPRING_PROFILE}/${CIRCLE_SPRING_PROFILES}/" > /tmp/deployment-provider.yml

kubectl apply -f /tmp/deployment-provider.yml
kubectl apply -f scripts/circleci/config/service-provider.yml