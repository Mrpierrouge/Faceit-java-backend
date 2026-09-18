FROM maven:3.9.4-eclipse-temurin-21-alpine AS build

COPY pom.xml ./

COPY src ./src
RUN mvn -q clean package -DskipTests -B

FROM eclipse-temurin:21-jre AS runtime

COPY --from=build /target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]