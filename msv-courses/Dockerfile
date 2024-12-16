#FROM eclipse-temurin:21-alpine
#WORKDIR /app/msv-users
#COPY ./pom.xml /app
#COPY ./msv-users .
#RUN ./mvnw clean package -DskipTests
#EXPOSE 8001
#ENTRYPOINT ["java", "-jar", "./target/msv-users-0.0.1-SNAPSHOT.jar"]

FROM amazoncorretto:21-alpine AS builder

WORKDIR /app/msv-courses

COPY ./pom.xml /app
COPY ./msv-courses/.mvn ./.mvn
COPY ./msv-courses/mvnw .
COPY ./msv-courses/pom.xml .

#RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
RUN ./mvnw dependency:go-offline
COPY ./msv-courses/src ./src

RUN ./mvnw clean package -DskipTests

FROM amazoncorretto:21-alpine

WORKDIR /app

COPY --from=builder /app/msv-courses/target/msv-courses-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "msv-courses-0.0.1-SNAPSHOT.jar"]
EXPOSE 8002
