# Usa una versione base di OpenJDK 17 (compatibile con Spring Boot 3.x)
FROM eclipse-temurin:17-jdk-alpine as builder

# Aggiungi i tool di build necessari
RUN apk add --no-cache maven

# Imposta la directory di lavoro
WORKDIR /app

# Copia il file pom.xml e scarica le dipendenze
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copia l'intero progetto e costruisci l'applicazione
COPY . ./
RUN mvn package -DskipTests

# Fase finale, usa una versione runtime più leggera di OpenJDK
FROM eclipse-temurin:17-jre-alpine

# Imposta il volume per i log
VOLUME /tmp

# Copia il JAR dalla fase di build
COPY --from=builder /app/target/*.jar app.jar

# Esponi la porta su cui gira la tua applicazione Spring Boot
EXPOSE 8080

# Esegui l'applicazione
ENTRYPOINT ["java", "-jar", "/app.jar"]
