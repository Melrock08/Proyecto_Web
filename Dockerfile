# Imagen base con Java 21
FROM openjdk:21-jdk-slim

# Copiar el JAR ya construido por Maven y renombrarlo como app.jar
COPY target/*.jar app.jar

# Exponer el puerto donde corre Spring Boot
EXPOSE 8080

# Comando para arrancar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
