#!/usr/bin/env bash
set -e

n=0
until [ $n -ge 5 ]
do
  HOSTNAME_DNS=provider.${CIRCLE_SPRING_PROFILES}.nahknarmi.ga
  echo ${HOSTNAME_DNS}

  GIT_SHA1=$(curl ${HOSTNAME_DNS}/info | jq -r '.git.commit.id')
  echo ${GIT_SHA1}
  echo $(echo ${CIRCLE_SHA1} | cut -c -7)

  if [ ${GIT_SHA1} = $(echo ${CIRCLE_SHA1} | cut -c -7) ]; then
    exit 0
  fi;
  n=$[$n+1]
  sleep 10
done

exit 1