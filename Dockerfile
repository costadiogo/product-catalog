FROM openjdk:11

ARG FILE_JAR=build/libs/backend-0.0.1-SNAPSHOT.jar

COPY ${FILE_JAR} backend-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "backend-0.0.1-SNAPSHOT.jar"]