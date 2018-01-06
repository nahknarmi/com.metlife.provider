#!/usr/bin/env bash
set -eoux pipefail

CIRCLE_SPRING_PROFILES=${1:-dev}
HOSTNAME_DNS=provider.${CIRCLE_SPRING_PROFILES}.nahknarmi.ga.
ZONE=nahknarmi-ga
EXISTING_LOAD_BALANCER_IP=$(gcloud dns record-sets list --zone=nahknarmi-ga --filter='NAME='${HOSTNAME_DNS} --format="json" | jq -r '.[].rrdatas[0]')

echo 'Updating '${CIRCLE_SPRING_PROFILES}' for hostname '${HOSTNAME_DNS}' in zone '${ZONE}' with IP '${EXISTING_LOAD_BALANCER_IP}

if [ -z "$EXISTING_LOAD_BALANCER_IP" ]; then
    echo 'Load balancer doesnt exist'
else
    echo 'Removing existing DNS records' ${EXISTING_LOAD_BALANCER_IP}
    rm -f transaction.yaml

    gcloud dns record-sets transaction start -z=${ZONE}
    gcloud dns record-sets transaction remove --zone ${ZONE} --name ${HOSTNAME_DNS} --ttl 300 --type A "${EXISTING_LOAD_BALANCER_IP}"
    gcloud dns record-sets transaction execute -z=${ZONE}
fi

echo 'Adding new DNS records'
rm -f transaction.yaml

LOAD_BALANCER_IP=$(kubectl get services provider --namespace=${CIRCLE_SPRING_PROFILES} -o json | jq -r '.status.loadBalancer.ingress[0].ip')
gcloud dns record-sets transaction start -z=${ZONE}
gcloud dns record-sets transaction add --zone ${ZONE} --name ${HOSTNAME_DNS} --ttl 300 --type A "${LOAD_BALANCER_IP}"
gcloud dns record-sets transaction execute -z=${ZONE}

gcloud dns record-sets list -z=${ZONE}