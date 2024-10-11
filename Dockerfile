FROM openjdk:21 as builder
WORKDIR /app
COPY . /app/.
RUN chmod +x mvnw
RUN ./mvnw -f /app/pom.xml clean package -D maven.test.skip=true

FROM openjdk:21
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/*.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/*.jar"]