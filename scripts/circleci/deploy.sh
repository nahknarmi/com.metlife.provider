#!/usr/bin/env bash

kubectl set image deployment/provider provider=asia.gcr.io/nahknarmi-190508/com.metlife.provider:$CIRCLE_SHA1