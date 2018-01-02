#!/usr/bin/env bash

docker build -t asia.gcr.io/nahknarmi-190508/com.metlife.provider:$CIRCLE_SHA1 .
gcloud docker -- push asia.gcr.io/nahknarmi-190508/com.metlife.provider:$CIRCLE_SHA1