#!/usr/bin/env bash

docker build -t asia.gcr.io/${GOOGLE_PROJECT_ID}/com.metlife.provider:${CIRCLE_SHA1} .
gcloud docker -- push asia.gcr.io/${GOOGLE_PROJECT_ID}/com.metlife.provider:${CIRCLE_SHA1}