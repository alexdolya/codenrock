# FROM openjdk:17-oracle
FROM openjdk:17-slim-bullseye

RUN mkdir /app

WORKDIR /app

COPY src ./src
COPY pom.xml ./pom.xml
COPY mvnw ./mvnw
COPY .mvn ./.mvn
COPY lombok.config ./lombok.config
# COPY mvnw.cmd ./mvnw.cmd

RUN apt update && apt install --no-install-recommends -y wget
RUN wget "https://storage.yandexcloud.net/cloud-certs/CA.pem" \
    --output-document /etc/ssl/certs/yandex.pem && \
    update-ca-certificates

RUN bash ./mvnw clean package
ARG JAR_FILE=target/*.jar
RUN cp ${JAR_FILE} ./

EXPOSE 8080
ENTRYPOINT ["java","-jar","./taskFlow-0.0.1-SNAPSHOT.jar"]