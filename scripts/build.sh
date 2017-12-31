#!/usr/bin/env bash
set -e

gradle clean build

docker stop com.metlife.provider || echo 'Container doesnt exist'
docker rm com.metlife.provider ||  echo 'Container doesnt exist'
docker build -t asia.gcr.io/nahknarmi-190508/com.metlife.provider:v5 .

