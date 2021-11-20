[![Java CI with Maven](https://github.com/m4j-git/meteo-ya/actions/workflows/maven.yml/badge.svg)](https://github.com/m4j-git/meteo-ya/actions/workflows/maven.yml)

# spring-boot-app for yandex-weather api

Description
-----------
....

Building
--------
##### Requirements
* Maven 3.6+
* Java 17+
* MariaDb 10+

##### Environment variables
* METEO_USER
* METEO_PASSWD

* MYSQL_HOST
* MYSQL_PORT
* MYSQL_USER
* MYSQL_PASSWD

* YANDEX_API_KEY

##### /etc/hosts
* meteo-host

##### Check out (with submodule) and build:
    git clone --recurse-submodules -j8 git://github.com/m4j-git/meteo-ya.git
    ./make test
    ./make prod





