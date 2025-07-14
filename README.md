<div align="center">

![Play View Banner](https://github.com/OlavoMDSilva/Play-View/blob/main/PlayViewBanner.png?raw=true)

![Java](https://img.shields.io/badge/Java-8B0000)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2F6F3F)
![REST API](https://img.shields.io/badge/REST-0099CC)

</div>

## 🚧 DEPRECATED!!

**WARNING** this project will not be updated anymore, but I want to make a version using Docker and AWS to learn more about DevOps.

## 📌 About Play View

**Play View** is a REST API platform designed to help users discover, review, and explore video games. The API provides endpoints for managing game data, including searching, filtering, and reviewing games.

This project is a **work in progress** and is built using Spring Boot for the backend. The project aims to deliver a complete backend solution for a game review platform

### 💻 Features

- **Game Management**: API endpoints to create, update, read, and delete games.
- **Game Reviews**: Users can leave and view reviews for games through the API.
- **Filtering**: Advanced search and filtering options for games based on various attributes like genre, company, and publisher.
- **Swagger Documentation**: Integrated Swagger UI (coming soon) for API documentation.

## 🛠️ Technologies Used

- **Backend:** Spring Boot (REST API)
- **Database:** MySQL
- **Version Control:** Git
- **Testing:** JUnit, Spring Test
- **API Documentation:** Swagger "http://localhost:8080/api/docs" (Deprecated)

## 🚀 Installation

- ### Clone this repository
  ```bash
    git clone git@github.com:OlavoMDSilva/Play-View.git
  ```

- ### Database setup

    - #### Make sure you have MySQL properly installed
    - #### The Application will create and reset the database automatically when started for showcasing purpose

- ### Configure application

    - #### Open the application.yaml file and update the database connection settings as needed (e.g., your MySQL password). You can set your MySQL password via an environment variable or directly in the file.

- ### Run the Application Locally

  - #### Open the project in your favorite Java IDE (IntelliJ IDEA is recommended) and run the main application class (PlayViewApplication).
  - #### Alternatively, build and run the project using Maven:
  ```bash
  ./mvnw spring-boot:run
  ```
  
- ### Test API

  - #### Once running, the API will be available at: http://localhost:8080/api
  - #### Use Postman, cURL, or your preferred tool to interact with the endpoints.
