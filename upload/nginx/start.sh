#/bin/bash

docker build -t nginx-rtmp .
docker rm nginx-rtmp -f
docker run -d -p 1935:1935 -p 8080:8080 -p 18081:18080 --name nginx-rtmp nginx-rtmp