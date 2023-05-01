#FROM openjdk:19-oracle
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]


FROM openjdk:19-jdk-slim AS build

RUN apt-get update && \
    apt-get install -y maven

COPY ./ ./
RUN mvn clean package -DskipTests=true

FROM openjdk:19-jdk-slim
ARG JAR_FILE=target/*.jar
COPY --from=build ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
