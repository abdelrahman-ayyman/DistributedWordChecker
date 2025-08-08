# Distributed Alphanumeric Word Checker

## Overview

This project is a practice implementation of a distributed system using **Spring Boot** and **REST APIs**. It demonstrates the interaction between a client, a load balancer, and multiple servers. The main goal is to check if a given word is alphanumeric, using a scalable architecture.

The system consists of three main components:

- **Client**: Provides a web interface for users to submit strings.
- **Load Balancer**: Receives requests from the client and forwards them to available servers using a round-robin algorithm.
- **Server(s)**: Validate if the submitted string is alphanumeric and log the requests.

## Aim

This project is designed to:

- Practice building RESTful APIs with Spring Boot.
- Understand distributed system concepts such as load balancing and service registration.
- Gain experience with modular Java projects and microservice communication.

## Project Structure

- `client/` - Spring Boot application serving the web UI for user input.
- `loadbalancer/` - Spring Boot application acting as a RESTful load balancer.
- `server/` - Spring Boot application(s) that process and log string validation requests.

## How It Works

1. **User Input**: The user enters a string in the client web interface.
2. **Request Routing**: The client sends the string to the load balancer via a REST API.
3. **Load Balancing**: The load balancer forwards the request to one of the registered servers.
4. **Validation**: The server checks if the string is alphanumeric and logs the request.
5. **Response**: The result is sent back to the client and displayed to the user.

## Usage

### Prerequisites

- Java 21+
- Maven

### Running the Applications

1. **Start the Server(s):**
   - Navigate to the `server/demo` directory.
   - Run:
     ```sh
     ./mvnw spring-boot:run
     ```
   - (You can start multiple server instances on different ports by changing `server.port` in `application.properties`.)

2. **Start the Load Balancer:**
   - Navigate to the `loadbalancer/demo` directory.
   - Run:
     ```sh
     ./mvnw spring-boot:run
     ```
   - (Default port can be configured by changing `server.port` in `application.properties`.)

3. **Start the Client:**
   - Navigate to the `client/demo` directory.
   - Run:
     ```sh
     ./mvnw spring-boot:run
     ```
   - Open [http://localhost:8082](http://localhost:8082) in your browser.

### Example

- Enter a string in the client UI and submit.
- The result (whether the string is alphanumeric) will be displayed.
<div align='center'>
   <img width="600" alt="Screenshot 2025-08-08 085238" src="https://github.com/user-attachments/assets/e06ec125-4f5a-4159-a252-7251953a27d9"/>
</div>

- Server logs can be viewed at [http://localhost:8081/log](http://localhost:8081/log) (if running on the default port, 8081).
<div align='center'>
   <img width="600" alt="Screenshot 2025-08-08 085220" src="https://github.com/user-attachments/assets/8dc87991-d886-44e2-8e71-6a86a850e7ad" />
</div>

---

**Note:** This project is for educational purposes and does not include production-level features such as authentication, persistent storage, or advanced error handling.
