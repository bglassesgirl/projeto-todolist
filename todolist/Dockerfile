FROM ubuntu:latest AS build

RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven

WORKDIR /app

COPY . .

RUN mvn -N io.takari:maven:wrapper
RUN ./mvnw clean install

FROM ubuntu:latest

COPY --from=build /app/target/todolist-1.0.0.jar /app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]
