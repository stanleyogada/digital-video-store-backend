#!/bin/bash

git pull origin main;
./mvnw clean;
./mvnw clean install;
docker container rm -f $(docker container ls -aq);
docker image build -t chineduogada/digital-video-store .;
docker push chineduogada/digital-video-store:lastest;
docker container run -dp 80:8080 --env-file src/main/resources/.env chineduogada/digital-video-store;
