FROM openjdk:8-jdk-alpine
ARG WAR_FILE=build/libs/*.war
COPY ${WAR_FILE} app.war
EXPOSE 80
ENTRYPOINT ["java","-jar","/app.war"]