# Github Repo fetcher

This application fetches Github repositories in descending order on the basis of stargazers count. 
 [Github Rest API](https://docs.github.com/en/rest?apiVersion=2022-11-28)
 
## Technologies :wrench:
- Java 21
- Spring Boot 3.2.4
- JUnit 5
- Mockito
- Maven

## Requirements :fuelpump:
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


## Swagger Url :link:

```
http://localhost:8080/swagger-ui/index.html#/
```
## Variables :information_source:

| Name  | Type | Value  | Example | 
| :---: | :---: | :---: | :---: |
| Number of repos  | Integer  | Any postive Integer  | 10,20,30   |
| Language  | Text/String  | Comma separated text/string values  | java,go,c  |
| Date  | Date  | date in yyyy-mm-dd format  | 2020-02-20  |

**Note:** 0 in numberofrepos will display all the repos and it is the defaultvalue.

![Screenshot (2)](https://github.com/NavdeepMann008/PracticalTask/assets/167414404/d0bd9f8e-1ebd-4a54-89c3-7fe0c78a0668)


## Run Unit Test :runner:
```
mvn test
```
