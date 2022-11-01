FROM openjdk:11

ARG FILE_JAR=target/backend-0.0.1-SNAPSHOT.jar

COPY ${FILE_JAR} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]