# Usar una imagen base de Maven para construir el proyecto
FROM maven:3.9.4-eclipse-temurin-17 AS builder

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar solo los archivos necesarios para las dependencias
COPY pom.xml ./

# Descargar las dependencias sin construir el proyecto
RUN mvn dependency:go-offline

# Copiar el resto de los archivos del proyecto
COPY src ./src

# Construir el proyecto y generar el .jar
RUN mvn clean package -DskipTests

# Usar una imagen base de Java para ejecutar la aplicación
FROM openjdk:17-jdk-slim

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el .jar generado desde la etapa de construcción
COPY --from=builder /app/target/FTP-Project-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto en el que corre tu aplicación
EXPOSE 8081

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]