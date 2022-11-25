FROM openjdk:19-jdk-alpine
ARG JAR_FILE=out/artifacts/webapi_asi_notification_jar/webapi-asi-notification.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]