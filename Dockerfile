FROM maven:3.6.3 AS build

WORKDIR /app

COPY pom.xml /app/
COPY src /app/src

RUN mvn -f /app/pom.xml clean package

FROM openjdk:8-jdk-alpine

COPY --from=build /app/target/app*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]