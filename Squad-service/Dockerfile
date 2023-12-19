FROM eclipse-temurin:17
ARG JAR_FILE=*.jar
COPY build/libs/Squad-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]