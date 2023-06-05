FROM openjdk:11-jdk
COPY ./build/libs/*.jar jns-server.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/jns-server.jar"]
