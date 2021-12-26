#!/bin/bash
set -e

java -version

# finding script folder
dir=`dirname $0`
absdir=`cd $dir; pwd`
cd $absdir

skip='-Dmaven.test.skip -DskipITs'

MODULE_NAME="meteo-ya"
echo "make $MODULE_NAME"

show_help(){
    echo -e "Usage: ./make build|git|check|deploy|test"
    exit
}

build_docker(){
  CONTAINER_NAME=meteo-ya
  echo -e "\nSet docker container name as ${CONTAINER_NAME}\n"
  IMAGE_NAME=${CONTAINER_NAME}:latest
  echo -e "\nSet docker image name as ${IMAGE_NAME}\n"
  PORT=8083
  echo -e "Set docker image PORT to ${PORT}\n"
  
  set +e
  echo -e "\nStop running Docker containers with image tag ${CONTAINER_NAME}, and remove them...n"
  docker stop $(docker ps -a | grep ${CONTAINER_NAME} | awk '{print $1}')
  docker rm $(docker ps -a | grep ${CONTAINER_NAME} | awk '{print $1}')
  set -e
  
  echo -e "\nDocker build image with name ${IMAGE_NAME}...\n"
  docker build -t ${IMAGE_NAME} -f Dockerfile .
  
  echo -e "\nStart Docker container of the image ${IMAGE_NAME} with name ${CONTAINER_NAME}...\n"
  docker run -it -d --restart unless-stopped \
      -p ${PORT}:${PORT} --network=host \
      -v /opt/meteo/meteo-ya/logs:/opt/meteo/logs \
      -e METEO_USER=${METEO_USER} \
      -e METEO_PASSWD=${METEO_PASSWD} \
      -e YANDEX_API_KEY=${YANDEX_API_KEY} \
      --name ${CONTAINER_NAME} \
      ${IMAGE_NAME} 
}
    
if [ "$1" = "build-prod" ]; then
  mvn clean install -P prod $skip 
fi

if [ "$1" = "build-stage" ]; then
  mvn clean install -P stage $skip 
fi

if [ "$1" = "build-dev" ]; then
  mvn clean install -P dev $skip 
fi

if [ "$1" = "test-it" ]; then
  mvn clean test verify -P dev 
fi

if [ "$1" = "build-site" ]; then
  mvn site -P prod $skip 
fi

if [ "$1" = "build-docker" ]; then
  build_docker
fi

if [ "$1" = "test-stress" ]; then
  ./wrk -t12 -c12 -d30s http://meteo-ya-host:8083/meteo-ya/?geonameId=1
fi


