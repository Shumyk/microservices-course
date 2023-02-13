FROM openjdk:17-jdk-alpine

COPY target/api-users-0.0.1-SNAPSHOT.jar ApiUsers.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "ApiUsers.jar"]
