# ---------- Build Stage ----------
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /build

# Önce pom.xml'i kopyala ve bağımlılıkları indir (layer caching için)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Kaynak kodları kopyala ve build et
COPY src ./src
RUN mvn clean package -DskipTests -B

# ---------- Runtime Stage ----------
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Güvenlik için non-root user oluştur
RUN groupadd -r spring && useradd -r -g spring -u 1001 spring

# JAR dosyasını kopyala
COPY --from=builder --chown=spring:spring /build/target/*.jar app.jar

# Non-root user'a geç
USER spring

# Port expose et
EXPOSE 8080

# Health check ekle (opsiyonel)
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# JVM parametreleri ile uygulamayı başlat
ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-jar", \
    "app.jar"]