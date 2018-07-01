# coreLang

coreLang is a probabilistic modeling and simulation language for basic IT infrastructures. More specifically, it is a domain specific language (DSL) created with [MAL (the Meta Attack Language)](https://github.com/pontusj101/MAL).

## Getting Started

These instructions will guide you on how to have a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

For this project to work, the MAL compiler, found on the [MAL project](https://github.com/pontusj101/MAL) is needed.

### Project's file structure

The language itself is found on the *.mal files of this project while the test cases are located on the [src/test/java](src/test/java) folder.
The [index.html](/index.html) file provides a useful representation of all the modeled attack steps on every modeled asset.

### Usage

Since this is a Maven project it is ought to be opened by any compatible IDE or to be used with the mvn command line tool.

To build the project and run the test cases simply issue the following command on the command line:

```
mvn clean install
```

If you want to just built the project without running the test cases do the following:

```
mvn clean install -DskipTests
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

* **Pontus Johnson**
* **Robert Lagerström**
* **Mathias Ekstedt**

## Contributors

* **Sotirios Katsikeas**
