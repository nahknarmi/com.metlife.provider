#!/usr/bin/env bash
set -e
#Assumes that docker has been installed

docker run -d -p 8080:8080 --name=com.metlife.provider com.metlife.provider
docker logs -f com.metlife.provider