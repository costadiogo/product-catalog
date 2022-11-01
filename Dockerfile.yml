FROM openjdk:11
ARG JAR_FILE=target/product-catalog.jar
ADD ${JAR_FILE} product-catalog.jar
ENTRYPOINT ["java","-jar","/product-catalog.jar"]