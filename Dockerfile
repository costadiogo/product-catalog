FROM openjdk:11

RUN mkdir app

ADD /target/${JAR_FILE} backend-0.0.1-SNAPSHOT.jar

COPY ${FILE_JAR} app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "backend-0.0.1-SNAPSHOT.jar"]