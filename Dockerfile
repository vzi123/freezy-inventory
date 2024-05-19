# First stage: build
FROM maven:3.8.3-openjdk-17 AS build
COPY . /app
WORKDIR /app
RUN chmod +x mvnw
RUN chmod +x ./mvnw
RUN ls -al /app

RUN echo \
    "<settings xmlns='http://maven.apache.org/SETTINGS/1.0.0\' \
    xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' \
    xsi:schemaLocation='http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd'> \
        <localRepository>/root/Users/myname/.m2/repository</localRepository> \
        <interactiveMode>true</interactiveMode> \
        <usePluginRegistry>false</usePluginRegistry> \
        <offline>false</offline> \
    </settings>" \
    > /usr/share/maven/conf/settings.xml;

COPY . /usr/src/app
RUN mvn --batch-mode -f /usr/src/app/pom.xml clean package


# Second stage: runtime
FROM openjdk:17-jdk-slim
COPY --from=build /app/target/*.jar /app/my-app.jar
WORKDIR /app
CMD ["java", "-jar", "my-app.jar"]
