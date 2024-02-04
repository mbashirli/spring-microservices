FROM eclipse-temurin:21.0.2_13-jre
WORKDIR application
COPY target/*.jar app.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "app.jar"]