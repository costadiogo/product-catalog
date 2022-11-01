FROM maven:3.6.3 AS maven

WORKDIR /app

COPY . /app

RUN mvn package

FROM adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE=backend-0.0.1-SNAPSHOT.jar

WORKDIR /opt/app

COPY --from=maven /usr/src/app/target${JAR_FILE} /opt/app/

EXPOSE 8080

ENTRYPOINT ["java", "-jar","/backend-0.0.1-SNAPSHOT.jar"]