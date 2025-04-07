FROM openjdk:17
EXPOSE 8089
ADD target/Genre-0.0.1-SNAPSHOT.jar Genre-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "Genre-0.0.1-SNAPSHOT.jar"]