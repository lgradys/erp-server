<h1 align="center">Warehouse Management REST API</h1>

## 📜 Description
REST API for warehouse management, deployed from Docker image on AWS EC2 Instance with basic CI/CD implemented with Jenkins. However, only Heroku deployment was made available to the public: **https://erp-server-prod-profile.herokuapp.com**. Mainly due to pricing conditions. Below you can find sample credentials to login. All available endpoints are included in OpenApi documentation.
```
{
    "username" : "user",
    "password" : "password"
}
```

## 🛠 Technology stack
- Java
- Maven
- Spring Boot
- Hibernate
- H2 Database
- PostgreSQL
- Lombok
- JSON Web Token
- JUnit
- Mockito
- Amazon Web Services
- Docker
- Jenkins

## ✅ Installation requirements
- Maven
- JDK 11+

## 🏃‍♂️ Installation
- Development profile:
  - Run the application:
  ```
  mvn clean -Pdev spring-boot:run
  ```
  
- Production profile (for local use):
  - Add properties to data source connection in **application-prod.properties** file
  - Run the application:
  ```
  mvn clean -Pprod spring-boot:run
  ```
  
## 🎮 OpenApi documentation

https://erp-server-prod-profile.herokuapp.com/swagger-ui/index.html

## 🎯 Future features
- Integration JWT with Spring Security architecture (for authorities handling)
- Registration page with email confirmation