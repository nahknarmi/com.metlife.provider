#!/usr/bin/env bash

gradle clean build

docker stop com.metlife.provider
docker rm com.metlife.provider
docker build -t com.metlife.provider .

