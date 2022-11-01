FROM openjdk:11
ARG JAR_FILE=target/backend-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/backend-0.0.1-SNAPSHOT.jar"]