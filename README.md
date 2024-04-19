# Pharmacy-App

### Introduction

This application consists of two modules: admin and user. The admin module allows administrators to create orders and
manage medication details, while the user module enables regular users to access and retrieve medication information.

### Technologies Used

1. Java
2. Spring Boot
3. REST APIs
4. MySQL
5. JWT Token

### Required Software

1. IntellijIdea / VSCode
2. Java Version - 17 LTS
3. MySQL Workbench
4. Postman

### Available APIs

#### For Admin

- localhost:8080/register
- localhost:8080/login
- localhost:8080/logout
- localhost:8080/admin/updatePassword
- localhost:8080/admin/getAdminDetails
- localhost:8080/admin/updateInformation
- localhost:8080/admin/medication/add
- localhost:8080/admin/medication/get
- localhost:8080/admin/medication/get/{id}
- localhost:8080/admin/medication/deleteMedication/{medicationID}
- localhost:8080/admin/medication/updateMedication
- localhost:8080/admin/order/new
- localhost:8080/admin/order/getOrder/{order_id}
- localhost:8080/admin/order/getOrders
- localhost:8080/admin/order/getOrdersByUser
- localhost:8080/admin/order/getOrderByDate

#### For User

- localhost:8080/register
- localhost:8080/login
- localhost:8080/logout
- localhost:8080/home
- localhost:8080/user/updatePassword
- localhost:8080/user/getUserDetails
- localhost:8080/user/updateInfo
- localhost:8080/user/medication/getByName/{name}
- localhost:8080/user/medication/getById/{id}

### How to run the project

- Install IntelliJIdea in your laptop.
- Download the project from GitHub.
- Install the required dependencies.
- Update the database information in application.properties file.
- Then run the spring boot application.
- You can test this application using Postman.
