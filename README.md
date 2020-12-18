# Postsapp
Simple Java spring boot application with REST API. 
## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)

## General info
The project allows users to register, log in and after logging in add new posts to database.
Users can view and comment created posts.
## Technologies
Project is created with:
* Java 13
* Spring boot 2.4.0
* Maven
* MySQL
## Setup
MySQL database dump file is located in /src/main/resources/sql/postsapp.sql

Default database username is 'posts_user' and password is 'postspasswd' and database port is 3306

To run tests use:
```
$ git clone "https://github.com/maciejp12/postsapp.git"
$ cd postsapp/
$ mvn test
```

To run application use:
```
$ git clone "https://github.com/maciejp12/postsapp.git"
$ cd postsapp/
$ mvn spring-boot:run
```
Default port is 8080




