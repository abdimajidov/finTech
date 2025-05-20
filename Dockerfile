FROM openjdk:23-jdk-slim-bullseye

WORKDIR /app
RUN apt-get update && apt-get install -y libfreetype6 fontconfig && rm -rf /var/lib/apt/lists/*

COPY *.jar app.jar
COPY application.yaml application.yaml

CMD ["java", "-jar", "app.jar", "--spring.config.location=file:./application.yaml"]

EXPOSE 8282