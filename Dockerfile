FROM openjdk:21-jdk

WORKDIR /app

COPY ./target/digitalstore.jar /app/digitalstore.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "digitalstore.jar"]