# Stage 1: Build with Maven 3.9.9 and Java 21
FROM eclipse-temurin:21-jdk AS build

# Install Maven 3.9.9
ARG MAVEN_VERSION=3.9.9
ARG USER_HOME_DIR="/root"
ARG BASE_URL=https://downloads.apache.org/maven/maven-3/${MAVEN_VERSION}/binaries
RUN apt-get update && apt-get install -y curl tar && \
    curl -fsSL ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz | tar -xz -C /opt && \
    ln -s /opt/apache-maven-${MAVEN_VERSION} /opt/maven && \
    ln -s /opt/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /opt/maven
ENV PATH ${MAVEN_HOME}/bin:${PATH}

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Runtime with Java 21 only
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]