#!/usr/bin/env bash

gradle unitTests

gradle assemble

mkdir -p ~/junit/
find . -type f -regex ".*/build/test-results/.*xml" -exec cp {} ~/junit/ \;