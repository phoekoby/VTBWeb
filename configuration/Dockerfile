FROM openjdk:11-slim
LABEL maintainer="Phoekoby Team"
RUN apt-get update && apt-get install -y \curl
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]