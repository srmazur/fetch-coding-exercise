# Fetch Coding Exercise

## Overview

This project is a solution to the Fetch Coding Exercise. The goal is to find a fake gold bar among 9
bars using a balance scale, and automate this process.


## Running process Using Docker

You can run the process using Docker on your machine to ensure a consistent environment and avoid dependency issues.

### Prerequisites

- [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- Install and Open the Docker

### Setup and Run Instructions

1. **Clone the Repository and navigate to the project**:
    ```sh
    git clone https://github.com/srmazur/fetch-coding-exercise.git
    cd fetch-coding-exercise
    ```

2. **Build the Docker Image and Run the Docker Containers**:
    ```sh
    docker-compose up --build --abort-on-container-exit --exit-code-from test-runner
    ```
### Accessing Test Report

Once the process completed, the test report and screenshots will be available in the following directories:
- `./fetch-coding-exercise/test-output`: Contains test execution screenshots.
- `./fetch-coding-exercise/test-reports/index.html`: Contains the simple TestNG report.

### Cleanup

To stop and remove the Docker containers, run:
```sh
docker-compose down
```

## Running process Using GitHub Actions

You can use GitHub Actions to automate the process for this project. The provided workflow will set up the necessary
environment, run the test, and save the test report and screenshots as artifacts.

### Instructions

**Trigger the Workflow**:

- Go to the `Actions` tab of the repository.
- Select the `find_the_bar` workflow.
- Click on the `Run workflow` button to manually trigger the workflow.

**Accessing Test Report**:
- Once the workflow is completed, you can check the result and download the artifact from the `Actions` tab.
- The process report and screenshots will be saved as `fetch-coding-exercise`.
- `./fetch-coding-exercise/screenhots`: Contains test execution screenshots.
- `./fetch-coding-exercise/test-reports/index.html`: Contains the simple TestNG report.


## Running process Locally

### Prerequisites

- Java Development Kit (JDK) 21
- Maven

### Setup Instructions

**Install JDK and Maven**:
   #### Windows
1. **Install Java (JDK 21)**:
    - Download the JDK from the [Oracle website](https://www.oracle.com/java/technologies/javase-jdk21-downloads.html).
    - Follow the installation instructions provided on the website.
    - Set the `JAVA_HOME` environment variable to the JDK installation path.

2. **Install Maven**:
    - Download Maven from the [Apache Maven website](https://maven.apache.org/download.cgi).
    - Unzip the downloaded archive to a directory.
    - Add the `bin` directory of the unzipped Maven directory to the `PATH` environment variable.

#### Mac
1. **Install Java (JDK 21)**:
- ```sh
   brew install openjdk@21
   ```
2. **Install Maven**:
-  ```sh
    brew install maven
   ```

#### Clone the Repository and navigate to the project
- ```sh
    git clone https://github.com/srmazur/fetch-coding-exercise.git
    cd fetch-coding-exercise
    ```

#### Update configuration.properties file
   - open`./fetch-coding-exercise/configuration.properties`
   - update `runLocally=false` to `runLocally=true`

### Executing process and Accessing Test Report
**Execute the process**
```sh
mvn test
```
**Once the process completed, the test report and screenshots will be available in the following directories:**
- `./fetch-coding-exercise/test-output`: Contains test execution screenshots.
- `./fetch-coding-exercise/target/test-reports/index.html`: Contains the simple TestNG report.


### NOTE: It is also possible to run the process locally using a Selenium Grid setup.
