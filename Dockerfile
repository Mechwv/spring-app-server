#FROM openjdk:11-jdk AS build-backend
#WORKDIR /app
#COPY . /app
#RUN target=/root/.gradle ./gradlew --no-daemon build
#
#FROM openjdk:11-jre-slim
#WORKDIR /app
#COPY --from=build-backend /app/server/build/libs/app.jar /app/app.jar
#CMD ["java", "-jar", "app.jar"]

FROM gradle:7.0-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:11-jdk
COPY --from=build /home/gradle/src/build/libs/app.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

#FROM openjdk:11-jre-slim
#EXPOSE 9000
#ARG JAR_FILE=out/artifacts/server_jar/server.jar
#ADD ${JAR_FILE} server.jar
#ENTRYPOINT ["java","-cp","server.jar","app.placeeventmap.server.ServerApplicationKt"]