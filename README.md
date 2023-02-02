## Description

News Service implemented using Spring Boot and MySQL.

## Installation

You can install the project using the following command:

```
git clone https://github.com/serhhatsari/news-service
```

## Running with Docker Compose

You can start the server using the following command:

```
docker compose up --build -d
```

Server is now accessible at the

```
localhost:8080
```

You can delete the containers using the following command:

```
docker compose down
```

#### Swagger Page

Any endpoint that is exposed by the server can be accessible at the /swagger-ui/index.html endpoint.

```
eg: http://localhost:8080/swagger-ui/index.html
```

## Local Development

### Database

First, you need to create MySQL container using the following command:

```
docker container run -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=news-db -e MYSQL_USER=dev -e MYSQL_PASSWORD=dev -d --name mysql mysql  
```

- After finishing development, you can delete the database using the following command:

```
docker container rm mysql -f
```