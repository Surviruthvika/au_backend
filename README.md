# AU Exam Hall Locator — Spring Boot Backend

## Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8.0+

## Setup

### 1. Create MySQL Database
```sql
CREATE DATABASE au_exam_hall;
```

### 2. Configure application.properties
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.password=YOUR_MYSQL_PASSWORD
twilio.account-sid=YOUR_TWILIO_ACCOUNT_SID
twilio.auth-token=YOUR_TWILIO_AUTH_TOKEN
twilio.phone-number=+1XXXXXXXXXX
```

### 3. Run
```bash
mvn spring-boot:run
```
Backend runs on: http://localhost:8080

## API Endpoints

### Auth (Public)
| Method | URL | Description |
|--------|-----|-------------|
| POST | /api/auth/student/signup | Register student |
| POST | /api/auth/student/login | Student login |
| POST | /api/auth/admin/signup | Register admin |
| POST | /api/auth/admin/login | Admin login |

### Admin (Role: ADMIN)
| Method | URL | Description |
|--------|-----|-------------|
| GET | /api/admin/students | All students |
| GET | /api/admin/students/branch/{branch} | Filter by branch |
| POST | /api/admin/hall-allocations | Create allocation |
| GET | /api/admin/hall-allocations | All allocations |
| DELETE | /api/admin/hall-allocations/{id} | Delete allocation |
| POST | /api/admin/notifications | Send notification |
| GET | /api/admin/notifications | All notifications |

### Student (Role: STUDENT)
| Method | URL | Description |
|--------|-----|-------------|
| GET | /api/student/profile | My profile |
| GET | /api/student/hall-allocations | My hall allocation |
| GET | /api/student/notifications | My notifications |

## Project Structure
```
src/main/java/com/anurag/examhall/
├── ExamHallApplication.java
├── config/
│   ├── SecurityConfig.java
│   └── TwilioConfig.java
├── controller/
│   ├── AuthController.java
│   ├── AdminController.java
│   ├── StudentController.java
│   └── GlobalExceptionHandler.java
├── dto/
│   ├── AuthDTOs.java
│   ├── HallAllocationDTO.java
│   └── NotificationDTO.java
├── model/
│   ├── Student.java
│   ├── Admin.java
│   ├── HallAllocation.java
│   └── Notification.java
├── repository/
│   ├── StudentRepository.java
│   ├── AdminRepository.java
│   ├── HallAllocationRepository.java
│   └── NotificationRepository.java
├── security/
│   ├── JwtUtils.java
│   └── JwtAuthFilter.java
└── service/
    ├── AuthService.java
    ├── StudentService.java
    ├── HallAllocationService.java
    ├── NotificationService.java
    └── TwilioSmsService.java
```
