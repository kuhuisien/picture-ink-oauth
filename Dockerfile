FROM openjdk:17-jdk-slim AS build

# Install Maven in the build stage
RUN apt-get update && apt-get install -y maven

WORKDIR /workdir
COPY pom.xml .
COPY src ./src

# Run Maven to build the project
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=build /workdir/target/*.jar /oauth.jar
CMD ["java", "-jar", "/oauth.jar"]