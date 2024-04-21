# Github Repo fetcher

This application fetches Github repositories in descending order on the basis of stargazers count. 
 [Github Rest API](https://docs.github.com/en/rest?apiVersion=2022-11-28)
 
## Technologies :wrench:
- Java 21
- Spring Boot 3.2.4
- JUnit 5
- Mockito
- Maven

## Requirements
- Java JDK 21
- Maven 

## How to :rocket:

Clone the repository in a desired directory

```
git clone https://github.com/NavdeepMann008/PracticalTask.git
```


Go to a project directory and run the application

```
mvn clean install
mvn spring-boot:run
```

On boot up 
It runs on **localhost:8080** and swagger can be used to access API documentation


## Swagger Url

```
http://localhost:8080/swagger-ui/index.html#/
```


## Run Unit Test
```
mvn test
```
