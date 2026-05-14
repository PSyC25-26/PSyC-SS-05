FROM maven:3.9.6-eclipse-temurin-21
WORKDIR /app

# Copiamos todo el proyecto al contenedor
COPY . .

# Exponer el puerto
EXPOSE 8080

# Iniciar la aplicación en modo desarrollo con Spring Boot
ENTRYPOINT ["mvn", "spring-boot:run"]