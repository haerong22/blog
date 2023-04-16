#!/bin/sh

echo "========== build gradle =========="
gradle clean bootJar

echo "========== exec container ========"
docker-compose up --build

function cleanup {
  echo "========== delete images ========="
  docker image prune -f
}

trap cleanup EXIT