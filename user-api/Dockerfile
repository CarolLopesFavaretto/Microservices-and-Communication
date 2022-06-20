FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} user-0.0.1-SNAPSHOT

EXPOSE 8080 //qual porta rodara a aplicação

ENTRYPOINT ["java","-jar","/user-0.0.1-SNAPSHOT"]