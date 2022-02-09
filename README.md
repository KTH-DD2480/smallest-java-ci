# Gradle Continuous Integration Server
<img align="right" width="150" height="150" src="https://cdn0.iconfinder.com/data/icons/people-occupations-icons-rounded/110/Construction-Worker-4-512.png">

### DD2480 | Assignment 2 | Group 24

[![CI Build](https://github.com/lucianozapata/DD2480VT221/actions/workflows/gradle.yml/badge.svg)](https://github.com/lucianozapata/smallest-java-ci/actions/workflows/gradle.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Summary

This is a simple continous integration server for Gradle projects. The server expects a POST request from Github webhooks. It clones the repository, builds and tests the project. The post response contains information if everything worked.

## Setup 

### Requirements 
* Java JDK 11

This project can be built with the help of the included wrapper without downloading Gradle.
### Build 
```bash
$ ./gradlew build       (Linux/Mac)
$ gradlew.bat build     (Windows)
```
### Tests
```bash
$ ./gradlew test        (Linux/Mac)
$ gradlew.bat test      (Windows)
```

## Usage

1. Create a tunnel to your localhost on port 8080. One popular tool for that is ngrok.
2. Setup Github webhook to the tunnel URL.
3. Start the server 
```bash
$ ./gradlew run        (Linux/Mac)
$ gradlew.bat run      (Windows)
```
4. Commit, Push or redeliver webhook POST request from a Gradle project. 

## Documentation 

The JavaDoc documentation can be found [here](https://lucianozapata.github.io/smallest-java-ci). <br>
To generate the documentation locally:
```bash
$ ./gradlew javadoc     (Linux/Mac)
$ gradlew.bat javadoc   (Windows)
```
To open the documentation, open build/docs/javadoc/index.html with your browser.

## Statement of contributions

| Name      |                          Contributions                          |
|:----------|:---------------------------------------------------------------:|
| Yilin Chang    |  Git-Handler		|
| Nikolai Limbrunner |	Build-Handler						|
| Pontus Persman | Gradle-skeleton  			 |
| Rafi Youssef | Git-Handler              |
| Luciano Zapata | Request-Parsing     		|

All members contributed to the unit tests and the utility functions used for geometric calculations.

## Ways of working (Essence)

# TODO