#!/usr/bin/env bash

docker run -d -p 8080:8080 --name=com.metlife.provider com.metlife.provider
docker logs -f com.metlife.provider