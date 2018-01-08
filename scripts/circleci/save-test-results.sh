#!/usr/bin/env bash
set -eoux pipefail

mkdir -p ~/junit/
find . -type f -regex ".*/build/test-results/.*xml" -exec cp {} ~/junit/ \;