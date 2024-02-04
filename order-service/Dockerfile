FROM  eclipse-temurin:21.0.2_13-jre
COPY target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]