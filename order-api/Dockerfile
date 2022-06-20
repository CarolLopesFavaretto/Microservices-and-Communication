FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} order-0.0.1-SNAPSHOT

EXPOSE 8080

ENTRYPOINT ["java","-jar","/order-0.0.1-SNAPSHOT"]