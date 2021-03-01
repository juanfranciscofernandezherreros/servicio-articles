FROM maven:3.6-jdk-8 as maven
WORKDIR /app
COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B
COPY ./src ./src
# TODO: jollof-* should be replaced with the proper prefix
RUN mvn package && cp target/articles-0.0.1-SNAPSHOT.jar articles-0.0.1-SNAPSHOT.jar
# Rely on Docker's multi-stage build to get a smaller image based on JRE
FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=maven /app/articles-0.0.1-SNAPSHOT.jar ./articles-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/blogs-3.0.2-SNAPSHOT.jar"]