FROM maven:3.9.6-eclipse-temurin-21-alpine

WORKDIR /fetch-coding-exercise
COPY src ./src
COPY pom.xml .
COPY testng.xml .
COPY configuration.properties .

RUN apk update && \
  apk upgrade

RUN mvn test -DskipTests

CMD mvn test && \
    cp -r /fetch-coding-exercise/test-output /fetch-coding-exercise/local-test-output && \
    cp -r /fetch-coding-exercise/target/test-reports /fetch-coding-exercise/local-test-reports && \
    rm -rf /fetch-coding-exercise/local-test-reports/test-reports/junitreports && \
    rm -rf /fetch-coding-exercise/local-test-reports/test-reports/old && \
    rm -rf /fetch-coding-exercise/local-test-reports/test-reports/Suite