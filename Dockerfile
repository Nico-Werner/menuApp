FROM gradle:7.0-jdk11 AS build

COPY . /home/gradle/src/
WORKDIR /home/gradle/src
RUN gradle assemble

FROM openjdk:11-jre-slim

EXPOSE 8080
RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/menu-0.0.1-SNAPSHOT-plain.jar /app/spring-boot-application.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "/app/spring-boot-application.jar"]
