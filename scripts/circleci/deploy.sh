#!/usr/bin/env bash

kubectl set image deployment/provider provider=asia.gcr.io/${GOOGLE_PROJECT_ID}/com.metlife.provider:${CIRCLE_SHA1}