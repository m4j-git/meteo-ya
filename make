#!/bin/bash
set -e

java -version

# finding script folder
dir=`dirname $0`
absdir=`cd $dir; pwd`
cd $absdir

if [ "$1" = "prod" ]; then
  mvn clean
  mvn install -P prod,mysql -T 1C -Dmaven.test.skip -Dorg.slf4j.simpleLogger.defaultLogLevel=info -DargLine="-Xms1024m -Xmx8192m"
fi

if [ "$1" = "stage" ]; then
  mvn clean
  mvn install -P stage,mysql -T 1C -Dmaven.test.skip -Dorg.slf4j.simpleLogger.defaultLogLevel=info -DargLine="-Xms1024m -Xmx8192m"
fi

if [ "$1" = "dev" ]; then
  mvn clean
  mvn install -P dev,h2 -T 1C -Dmaven.test.skip -Dorg.slf4j.simpleLogger.defaultLogLevel=info -DargLine="-Xms1024m -Xmx8192m"
fi

if [ "$1" = "test" ]; then
  mvn clean
  mvn test -P dev,h2 -T 1C -Dorg.slf4j.simpleLogger.defaultLogLevel=warn -DargLine="-Xms1024m -Xmx8192m"
fi

if [ "$1" = "site" ]; then
  mvn site -P prod,mysql -Dmaven.test.skip -Dorg.slf4j.simpleLogger.defaultLogLevel=info -DargLine="-Xms1024m -Xmx8192m"
fi

if [ "$1" = "kill" ]; then
  rm .attach_pid*
  rm -r catalina.home_IS_UNDEFINED
  ps ax | grep tomcat | awk '{system("kill -KILL " $1 )}'
fi

if [ "$1" = "rm" ]; then
  find . -name "*.iml" -delete
  find . -name ".attach_pid*" -delete
  find . -name "catalina.home_IS_UNDEFINED" -delete
  find . -name "*.project" -delete
  find . -name "buildNumber.properties" -delete
  find . -name ".classpath" -delete
  find . -name ".factorypath" -delete
  find . -path "*.settings*" -delete
  find . -path "*target*" -delete
  rm -r catalina.home_IS_UNDEFINED
  rm -r .attach_pid*
fi

if [ "$1" = "deploy" ]; then
  echo 'Copy files...'

  scp target/meteo-provider.war root@fir:/opt/tomcat/9.0/webapps

  echo 'Restart server...'

  ssh -tt root@fir <<EOF
 
 systemctl stop  tomcatd
 
 sleep 1s
 
 if cd /opt/tomcat/9.0/logs/; then rm -f *; fi
 if cd /opt/tomcat/9.0/webapps/; then rm -rf meteo-provider; fi
 
 
 systemctl restart  tomcatd
 exit
EOF

  sleep 3s

  wget -O wget.txt http://fir:8080/meteo-provider
  rm wget.txt

  echo 'Bye'
fi


FILE_NAME=meteo-ya.jar
LOG_DIR=$(pwd)/logs

get_pid(){
    pid=`ps -ef|grep $FILE_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
    echo "$pid"
}

start_up(){
    pid=$(get_pid)
    if [ "$pid" ]
    then
        echo "Blog already startup!"
    else
        nohup java -jar ./$FILE_NAME --spring.profiles.active=prod > /dev/null 2>&1 &
    fi
}

shut_down(){
    pid=$(get_pid)
    if [ "$pid" ]
    then
        kill -9 $pid
        echo "Stop success!"
    else
        echo "Blog is not running!"
    fi
}

show_log(){
    if [ ! -d "${LOG_DIR}" ]; then
        mkdir $LOG_DIR
        touch $LOG_DIR/blog.log
    fi
    tail -f $LOG_DIR/blog.log
}

show_help(){
    echo -e "Usage: sh blog.sh start|stop|restart|status|log"
    exit
}

show_status(){
    pid=$(get_pid)
    if [ "$pid" ]
    then
        echo "Blog is running with pid: $pid"
    else
        echo "Blog is not running!"
    fi
}

if [ ! -n "$1" ] ;then
    show_help
else
    case "$1" in
        "start")
            start_up
            sleep 1
            show_log
            ;;
        "stop")
            shut_down
            ;;
        "restart")
            shut_down
            sleep 1
            start_up
            sleep 1
            show_log
            ;;
        "status")
            show_status
            ;;
        "log")
            show_log
            ;;
        *)
            echo 'Invalid command!'
            show_help
            ;;
    esac
fi
