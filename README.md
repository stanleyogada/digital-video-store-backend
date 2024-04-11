# Dray View Center Backend

## Description
A project assignment to demonstrate Java Spring Boot with MongoDB skills

## Usage
1. `git clone https://github.com/dray/digital-video-store-backend`: TODO change the this to Dray's real username
2. Enter or `cd` into the project's directory
3. `./mvnw clean`
4. `./mvnw clean install`

### Prerequisites
1. create a `.env` file in the `./src/main/resources/` directory
2. check the `./src/main/resources/env-skel` for a template to use for your `.env` file
3. populate the `.env` file with your own MongoDB atlas credentials

### Usage (Docker)
1. `docker image build -t dray-view-center:latest .`
2. `docker container run -p 8080:8080 --env-file ./src/main/resources/.env dray-view-center:latest`

### Usage (Local machine)
> Make sure you have downloaded and installed `JDK version 22` for your machine

1. `java -jar ./target/digitalstore.jar`

### Then visit `http:localhost:8080/api/v1/movies`

#### [See API documentation =>](https://documenter.getpostman.com/view/11253311/2sA35LUyeG)
