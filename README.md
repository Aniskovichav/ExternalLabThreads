# Seaport

<p align="center">
  <p align="center">
    <a href="https://github.com/Aniskovichav/ExternalLabThreads" target="_blank">
    </a>
  </p>
  <p align="center"><i>
    Seaport 
  </i></p>

# Seaport Simulation Project

## Description
This project simulates a port where ships dock to load and unload containers. The simulation is multi-threaded, with each ship running in its own thread. Ships can dock at available docks in the port to perform loading and unloading operations. If all docks are occupied, ships can exchange containers with each other. The project demonstrates the use of Java concurrency utilities to manage shared resources and avoid deadlocks.

## Lab Information
- **Lab Work:** Stage 20 - "Seaport"
- **Version:** 1.0
- **Developer:** Aniskovich Artem
- **Group:** J1023c
- **Date:** 02.06.2024
- **Updated:** 04.06.2024

## Features
- Multithreaded simulation of ships docking at a port.
- Ships can load and unload containers at docks.
- Ships can exchange containers with each other when all docks are occupied.
- Use of Java concurrency utilities (`java.util.concurrent.locks`) to manage synchronization.
- Logging of activities using Log4j.
- Exception handling and logging of errors.

## Classes

### `Port`
- Manages loading and unloading of containers.
- Manages docking of ships.
- Uses a `ReentrantLock` for synchronization.
- Contains methods to load/unload containers, request/release docks, and manage waiting ships.

### `Ship`
- Represents a ship that can load and unload containers.
- Runs as a separate thread.
- Can dock at the port to load/unload containers.
- Can exchange containers with other waiting ships if no docks are available.

### `PortLogger`
- Utility class for logging messages and errors using Log4j.

## UML Diagram

![UML Diagram](D:\16%20Анискович\Java\ExternalLabThreads.png "Seaport")

## Log4j Configuration
The logging configuration is specified in the `log4j.properties` file. Logs are output to a file named `test.log`.

### `log4j.properties`
```properties
# Root logger configuration
log4j.rootLogger=DEBUG, file

# File appender configuration
log4j.appender.file=org.apache.log4j.FileAppender
log4j.appender.file.File=logs/port.log
log4j.appender.file.Append=true
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} [%t] %-5p %c - %m%n
```

## Usage
### Running the Simulation
1. Ensure you have Java installed.
2. Compile the project:
    ```sh
    javac -cp .:log4j-1.2.17.jar by/itstep/aniskovich/java/externallabthreads/port/model/logic/*.java
    ```
3. Run the simulation:
    ```sh
    java -cp .:log4j-1.2.17.jar by.itstep.aniskovich.java.externallabthreads.port.model.logic.Port
    ```

### Running Tests
The project includes unit tests to verify the functionality of the `Port` class.

1. Compile the test classes:
    ```sh
    javac -cp .:junit-4.12.jar:hamcrest-core-1.3.jar by/itstep/aniskovich/java/externallabthreads/port/model/logic/*.java
    ```
2. Run the tests:
    ```sh
    java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore by.itstep.aniskovich.java.externallabthreads.port.model.logic.PortTest
    ```

## Dependencies
- [Log4j](https://logging.apache.org/log4j/1.2/)
- [JUnit](https://junit.org/junit4/)

## License
This project is licensed under the MIT License.

```

This README file provides an overview of the project, including its purpose, features, usage instructions, and dependencies. It ensures that anyone looking at the project can understand its structure and how to run or test it.