#!/bin/bash
set -e

java -version

# finding script folder
dir=`dirname $0`
absdir=`cd $dir; pwd`
cd $absdir

skip='-Dmaven.test.skip -DskipITs'

MODULE_NAME=meteo-ya
echo "make $MODULE_NAME"

show_help(){
    echo -e "Usage: ./make build|docker|test"
    exit
}

docker_stop(){
  set +e
  echo -e "\nStop running Docker containers with image tag ${MODULE_NAME}, and remove them...n"
  docker stop $(docker ps -a | grep ${MODULE_NAME} | awk '{print $1}')
  docker rm $(docker ps -a | grep ${MODULE_NAME} | awk '{print $1}')
  set -e
 }
 
docker_build(){
  IMAGE_NAME=${MODULE_NAME}:22.1.1
  docker_stop
  
  echo -e "\nDocker build image with name ${IMAGE_NAME}...\n"
  docker build -t ${IMAGE_NAME} -f Dockerfile .
  
  echo -e "\nStart Docker container of the image ${IMAGE_NAME} with name ${MODULE_NAME}...\n"
  docker run -it -d --restart unless-stopped \
      -p 8083:8083 --network=host \
      -v /opt/meteo/meteo-ya/logs:/opt/meteo/logs \
      -e METEO_USER=${METEO_USER} \
      -e METEO_PASSWD=${METEO_PASSWD} \
      -e YANDEX_API_KEY=${YANDEX_API_KEY} \
      --name ${MODULE_NAME} \
      ${IMAGE_NAME} 
}
if [ ! -n "$1" ] ;then
    show_help
else
    case "$1" in
        "build-dev")
            mvn clean install -P dev $skip
            ;;
        "build-stage")
            mvn clean install -P stage $skip
            ;;
        "build-prod")
              mvn clean install -P prod $skip 
            ;;
        "build-site")
            mvn site -P prod $skip
            ;;
        "docker-build")
            docker_build
            ;;
        "docker-stop")
            docker_stop
            ;;
        "test-it")
            mvn clean test verify -P dev
            ;;
        "test-stress")
            ./wrk -t12 -c12 -d30s http://meteo-host:8083/meteo-ya/?geonameId=1
            ;;                          
        *)
            echo 'Invalid command!'
            show_help
            ;;
    esac
fi
    
