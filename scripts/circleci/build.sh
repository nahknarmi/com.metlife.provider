#!/usr/bin/env bash
set -eoux pipefail

gradle unitTests

gradle assemble

mkdir -p ~/junit/
find . -type f -regex ".*/build/test-results/.*xml" -exec cp {} ~/junit/ \;