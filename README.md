# Signup Service 

Sign up backend service using Spring Boot.

- [x] Spring Boot App (No configuration required)
- [x] SignUp User
- [x] Get User
- [x] Only authenticated user can access
- [x] H2 Database (JPA)
- [x] Swagger2 API Documentation
- [x] Docker Container Support

## Package
mvn clean package

## Start
java -jar target/signupservice-0.0.1-SNAPSHOT.jar

## Sample requests
### Postman
#### SignUp (POST)
localhost:8080/signup/

{
    "firstName": "admin",
    "lastName": "admin",
    "email": "admin@admin.com",
    "password": "password"
}

#### Get User (GET)
localhost:8080/users/admin@live.com

### CURL
```
curl -d "{\"firstName\": \"admin\",\"lastName\": \"admin\", \"email\":\"admin@admin.com\", \"password\":\"password\"}" -H "Content-Type:application/json" -X POST http://localhost:8080/signup/
```

## H2 Database
http://localhost:8080/h2-console/

## Swagger Documentation
http://localhost:8080/swagger-ui.html

## Docker
#### Build
docker build -t signup-service-image .

#### Run
docker run -p 8080:8080 signup-service-image
