FROM openjdk:17-jdk-slim
EXPOSE 8091
ADD target/book-0.0.1-SNAPSHOT.jar book.jar

ENTRYPOINT ["java","-jar", "book.jar"]