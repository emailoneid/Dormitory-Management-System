# 🏫 Dormitory Management System

A Spring Boot backend project for managing university dormitory data — including students, rooms, and enrollments. Built with layered architecture, REST APIs, DTO mapping, and optional Postman mock server integration.

---

## ✅ Overview

This project enables management of:

- **Students**
- **Rooms**
- **Enrollments**

It supports full CRUD operations and provides an aggregated view of room assignments. Integration with Postman mock server allows importing sample data through dedicated endpoints.

---

## 🧱 Core Modules

### 1. 👨‍🎓 Student Module
Manages student records.

- Fields: `studentId` (external ID), `name`, `email`
- Endpoints:  
  - `POST /api/students`  
  - `GET /api/students`  
  - `PUT /api/students/{id}`  
  - `DELETE /api/students/{id}`
  - `GET /api/students/mocked` (Postman mock server upsert)

---

### 2. 🚪 Room Module
Manages dormitory room data.

- Fields: `roomNumber`, `capacity`, `available`
- Endpoints:  
  - `POST /api/rooms`  
  - `GET /api/rooms`  
  - `PUT /api/rooms/{id}`  
  - `DELETE /api/rooms/{id}`

---

### 3. 📝 Enrollment Module
Maps students to rooms using date ranges.

- Fields: `studentId` (external), `roomId`, `startDate`, `endDate`
- Each enrollment references:
  - One student (via `@ManyToOne`)
  - One room (via `@ManyToOne`)
- Endpoints:
  - `POST /api/enrollments`  
  - `GET /api/enrollments`  
  - `PUT /api/enrollments/{id}`  
  - `DELETE /api/enrollments/{id}`  
  - `GET /api/enrollments/aggregated` (aggregated view)

---

## 🔄 Integration Features

### ✅ Postman Mock API Support

- Endpoints for importing mock data:
  - `GET /api/students/mocked`
  - `GET /api/enrollments/mocked`
- Upsert logic prevents duplicate records by checking:
  - `studentId` for students
  - `id` for enrollments

---

## 📊 Aggregated View

- Endpoint: `GET /api/enrollments/aggregated`
- Combines data from:
  - `Student` (name, email)
  - `Room` (roomNumber)
  - `Enrollment` (startDate, endDate)

Example response:
```json
{
  "studentId": "00001",
  "studentName": "Kim",
  "email": "kim@example.com",
  "roomNumber": "A101",
  "startDate": "2025-07-01",
  "endDate": "2025-12-31"
}
```

## 🛠 Technologies Used
- Spring Boot 3
- Spring Web
- Spring Data JPA
- H2 / PostgreSQL (via Docker Compose)
- Docker (containerized backend + DB)
- Postman Mock Server
- DTO Pattern
- Layered Architecture: Controller → Service → Repository
