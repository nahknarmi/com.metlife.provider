#!/usr/bin/env bash
set -e

n=0
until [ $n -ge 5 ]
do
  LOAD_BALANCER_IP=$(kubectl get services provider --namespace=${CIRCLE_SPRING_PROFILES} -o json | jq -r '.status.loadBalancer.ingress[0].ip')
  echo ${LOAD_BALANCER_IP}

  GIT_SHA1=$(curl ${LOAD_BALANCER_IP}/info | jq -r '.git.commit.id')
  echo ${GIT_SHA1}
  echo $(echo ${CIRCLE_SHA1} | cut -c -7)

  if [ ${GIT_SHA1} = $(echo ${CIRCLE_SHA1} | cut -c -7) ]; then
    exit 0
  fi;
  n=$[$n+1]
  sleep 10
done

exit 1