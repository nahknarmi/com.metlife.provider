#!/usr/bin/env bash
set -eoux pipefail

cat scripts/circleci/config/deployment-provider.yml | sed -e "s/\${GIT_HASH}/${CIRCLE_SHA1}/" -e "s/\${SPRING_PROFILE}/${CIRCLE_SPRING_PROFILES}/" > /tmp/deployment-provider.yml
cat scripts/circleci/config/service-provider.yml | sed -e "s/\${SPRING_PROFILE}/${CIRCLE_SPRING_PROFILES}/" > /tmp/service-provider.yml

kubectl apply -f /tmp/deployment-provider.yml
kubectl apply -f /tmp/service-provider.yml