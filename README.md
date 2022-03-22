<h1 align="center">Warehouse Management REST API</h1>

## 📜 Description
Simple REST API for warehouse management, deployed from Docker image on AWS EC2 Instance with basic CI/CD implemented with Jenkins. Development profile deployed on AWS is available on **http://ec2-35-178-184-67.eu-west-2.compute.amazonaws.com:8080**. Below you can find sample credentials to login:
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
- Development profile (for local use):
  - Maven
  - JDK 11+

## 🏃‍♂️ Installation
- Development profile (for local use):
  - Build the application:
  ```
  mvn clean package
  ```
  - Run the application:
  ```
  mvn spring-boot:run
  ```
  - Use sample credentials:
  ```
  {
  "username" : "user",
  "password" : "password"
  }
  ```

## 🎮 Sample responses
### Login stage
```
{
    "status": 401,
    "error": [
        {
            "message": "Incorrect username!"
        }
    ],
    "resource": []
}
```

### Profile module
```
{
    "status": 200,
    "error": [],
    "resource": [
        {
            "id": 1,
            "name": "Laptop Dell",
            "quantity": 50,
            "quantityUnit": "psc",
            "warehouseId": 1
        }
    ]
}
```

## 🎯 Future features
- Integration JWT with Spring Security architecture (for authorities handling)
- Deployment production profile on AWS with online database
- Registration page with email confirmation
