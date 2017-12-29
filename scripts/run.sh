#!/usr/bin/env bash
set -e
#Assumes that docker has been installed
SPRING_PROFILES_ACTIVE=${1:-dev}

docker stop com.metlife.provider
docker rm com.metlife.provider
docker run -d -p 8080:8080 -e SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE} --name=com.metlife.provider com.metlife.provider
docker logs -f com.metlife.provider