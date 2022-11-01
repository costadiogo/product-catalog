FROM openjdk:11
ARG JAR_FILE=target/backend.jar
ADD ${JAR_FILE} backend.jar
ENTRYPOINT ["java","-jar","/backend.jar"]