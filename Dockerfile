FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# 1. Copiar solo el pom.xml para aprovechar la caché de Docker
COPY pom.xml .

# 2. Descargar todas las dependencias. Esta capa solo se reconstruirá si el pom.xml cambia.
RUN mvn dependency:go-offline

# 3. Copiar el código fuente. Esto es lo que cambia con más frecuencia.
COPY src ./src

# 4. Empaquetar la aplicación. Las dependencias ya están descargadas y cacheadas.
RUN mvn clean package -DskipTests -Dmaven.test.skip=true

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/fit-power-0.0.1-SNAPSHOT.jar app_fitpower.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app_fitpower.jar"]