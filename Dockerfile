FROM openjdk:latest

COPY target/signupservice-0.0.1-SNAPSHOT.jar signupservice.jar

ENTRYPOINT ["java","-jar","signupservice.jar"]

EXPOSE 8080