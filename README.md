# Proxy Caching Server

## Table of Contents
1. [Introduction](#introduction)
2. [Features](#features)
3. [Technologies Used](#technologies-used)
4. [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Running the Server](#running-the-server)
5. [Usage](#usage)
6. [API Endpoints](#api-endpoints)
7. [Cache Management](#cache-management)
8. [Contributing](#contributing)

## Introduction
The Proxy Caching Server is a lightweight proxy server that caches responses from an origin server to improve performance and reduce latency for subsequent requests. This project allows you to set up a caching mechanism that can serve cached data for faster response times.

## Features
- Caches API responses for faster retrieval.
- Supports dynamic configuration of server port and origin server URL.
- Command-line options for clearing the cache.
- Handles HTTP requests and responses effectively.
- Simple and easy-to-use structure for potential enhancements.

## Technologies Used
- Java 17
- Jetty (for HTTP server implementation)
- Apache Commons CLI (for command-line argument parsing)

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven
- A terminal or command prompt

### Installation
**1. Clone the repository:**
   ```bash
   git clone https://github.com/latecoder10/proxy-caching.git
   ```
**2. Navigate to the project directory:**
   ```bash
   cd proxy-caching
```
**1. Build the project using Maven:**
   ```bash
   mvn clean package
```
### Running the Server
- To run the server, execute the following command in your terminal:
   ```bash
   java -jar target/proxy-caching-0.0.1-SNAPSHOT.jar -p <port> -o <origin-url>
   ```
Replace <port> with the desired port number (e.g., 3000) and <origin-url> with the API endpoint you want to proxy (e.g., https://jsonplaceholder.typicode.com).
## Usage
- Once the server is running, you can make GET requests to the proxy server to fetch data. The server will cache the response and serve it for subsequent requests.
### Example:
- To fetch posts from the origin API through the proxy:
   ```bash
   curl http://localhost:<port>/posts
   ```
 ## Sample JSON Data

Here's a sample JSON response from the API:

```json
[
  {
    "userId": 1,
    "id": 1,
    "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
    "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
  },
  {
    "userId": 1,
    "id": 2,
    "title": "qui est esse",
    "body": "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"
  },
  {
    "userId": 1,
    "id": 3,
    "title": "ea molestias quasi exercitationem repellat qui ipsa sit aut",
    "body": "et iusto sed quo iure\nvoluptatem occaecati omnis eligendi aut ad\nvoluptatem doloribus vel accusantium quis pariatur\nmolestiae porro eius odio et labore et velit aut"
  }
]
```
## API Endpoints
- GET /posts - Fetches all posts from the origin server.
- GET /posts/{id} - Fetches a post by ID from the origin server.
- POST /clear-cache - Clears the cache of the proxy server.
## Cache Management
- To clear the cache, you can run the server with the -c option:
 ```bash
   java -jar target/proxy-caching-0.0.1-SNAPSHOT.jar -c
   ```
## Contributing
- Contributions are welcome! Please follow these steps:

- 1. Fork the repository.
- 2. Create a new branch (git checkout -b feature-branch).
- 3. Make your changes and commit them (git commit -m 'Add new feature').
- 4. Push to the branch (git push origin feature-branch).
- 5. Create a pull request.

### Notes:
- Replace `0.0.1-SNAPSHOT` with your actual version number if it's different.
- Ensure that the API endpoints in the **API Endpoints** section match what your application supports.
- You can add more sections or modify existing ones to better fit the specifics of your project.
- Open the port in your browser to checkup and dont forget to clean the cache
