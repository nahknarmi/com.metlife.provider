#!/usr/bin/env bash

kubectl expose deployment provider --type=LoadBalancer --port 80 --target-port 8080