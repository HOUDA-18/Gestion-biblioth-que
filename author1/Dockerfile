# Utiliser l'image de base OpenJDK 17
FROM openjdk:17

# Exposer le port sur lequel l'application s'exécutera
EXPOSE 8081

# Copier le fichier .jar dans l'image et le renommer pour plus de simplicité
ADD target/author1-0.0.1-SNAPSHOT.jar authormicroservice.jar

# Définir le point d'entrée pour exécuter l'application
ENTRYPOINT ["java", "-jar", "authormicroservice.jar"]
