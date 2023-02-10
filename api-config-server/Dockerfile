FROM openjdk:17-jdk-alpine

VOLUME /tmp

COPY apiEncryptionKey.jks apiEncryptionKey.jks
COPY target/api-config-server-0.0.1-SNAPSHOT.jar ConfigServer.jar

ENTRYPOINT ["java", "-jar", "ConfigServer.jar"]
