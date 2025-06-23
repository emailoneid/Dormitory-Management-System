# 🏫 Dormitory Management System

A Spring Boot backend system for managing university dormitory data — including students, rooms, and enrollments.  
The system follows a layered architecture, uses DTOs for clean data transfer, and integrates with an external Postman mock API for student data.

---

## ✅ Overview

This project supports management of:

- **Rooms**: Stored locally in the database
- **Enrollments**: Stored locally and reference room data and external student IDs
- **Students**: Fetched dynamically from an external API (not stored locally)

Full CRUD operations are supported for rooms and enrollments.

---

## 🧱 Core Modules

### 👨‍🎓 Student Module (External API Only)

- **Description**:  
  Student data is not persisted locally. All information is retrieved from a Postman mock server using the `studentId`.

- **Fields**:  
  `studentId` (external ID), `name`, `email`

- **Source URL**:  
  `https://41743d1d-d185-4483-b97c-124c16a44d98.mock.pstmn.io/mock/students`

- **API Integration Endpoint**:  
  `GET /api/students/mocked`

---

### 🚪 Room Module

- **Description**:  
  Manages dormitory room data using a local JPA entity.

- **Fields**:  
  `roomNumber`, `capacity`, `available`

- **Endpoints**:
  - `POST   /api/rooms`
  - `GET    /api/rooms`
  - `PUT    /api/rooms/{id}`
  - `DELETE /api/rooms/{id}`

---

### 📝 Enrollment Module

- **Description**:  
  Assigns students to rooms over a date range. The `studentId` is stored as a string and is used to fetch student data dynamically.

- **Fields**:  
  `studentId` (external), `roomId`, `startDate`, `endDate`

- **Database Relations**:  
  - `studentId`: stored as a string (no foreign key)  
  - `room`: `@ManyToOne` relationship

- **Endpoints**:
  - `POST   /api/enrollments`
  - `GET    /api/enrollments`
  - `PUT    /api/enrollments/{id}`
  - `DELETE /api/enrollments/{id}`

---

## 🔄 Integration Features

### ✅ Postman Mock API

Student data is dynamically fetched from the mock API during enrollment operations.

- `GET /api/students/mocked` — returns mock student data
- Enrollments enrich their responses with student name and email using the `studentId` field

---

## 🛠 Technologies Used

- Spring Boot 3  
- Spring Web (REST API)  
- Spring Data JPA (for Room and Enrollment)  
- H2 / PostgreSQL (via Docker Compose)  
- Docker (for containerized deployment)  
- Postman Mock Server (for external Student API)  
- DTO Pattern  
- Layered Architecture: Controller → Service → Repository
