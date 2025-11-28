# 🏥 SGHSS – Sistema de Gestão Hospitalar e Serviços de Saúde

Sistema completo desenvolvido em **Spring Boot 3**, integrando **PostgreSQL**, **JPA/Hibernate**, **JWT Authentication**, **Flyway**, **.env**, e arquitetura REST.

O SGHSS oferece recursos essenciais para gestão de uma unidade de saúde:

- 👨‍⚕️ Cadastros de Pacientes e Profissionais  
- 📅 Agendamentos de Consultas  
- 📜 Prescrições Médicas  
- 📂 Prontuários Clínicos  
- 🔐 Login e Autenticação JWT  
- 🔄 Migrations automáticas com Flyway  
- 💽 Criação automática de tabelas via JPA  

---

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.3**
- **Spring Web**
- **Spring Security**
- **Spring Data JPA**
- **Spring Validation**
- **JWT (JJWT 0.12.x)**
- **PostgreSQL**
- **Flyway**
- **HikariCP**
- **Lombok**
- **spring-dotenv (variáveis .env)**

---

## 📂 Estrutura do Projeto

```
Psghss/
 ├── src/main/java/com/pauloricardo/sghss/
 │    ├── controller/
 │    ├── entity/
 │    ├── repository/
 │    ├── service/
 │    ├── security/
 │    └── util/
 ├── src/main/resources/
 │    ├── application.properties
 │    ├── db/migration/
 │    └── .env
 └── README.md
```

---

## ⚙️ Configuração do Ambiente
1️⃣ Criar arquivo .env

```
DATABASE_URL=jdbc:postgresql://localhost:5432/sghss
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=123456

JWT_SECRET=3c4f9281abfe993829ffeab032ff29acbb2310ded0019fe11ccd89aa219ac9d1
JWT_EXPIRATION=3600000

2️⃣ Configurar application.properties

spring.application.name=sghss
server.port=9090
spring.config.import=optional:file:.env

spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DATABASE_USERNAME}
spring.datasource.password=${DATABASE_PASSWORD}

spring.flyway.enabled=true
spring.flyway.url=${DATABASE_URL}
spring.flyway.user=${DATABASE_USERNAME}
spring.flyway.password=${DATABASE_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

jwt.secret=${JWT_SECRET}
jwt.expiration=${JWT_EXPIRATION}

🗄️ Banco de Dados (PostgreSQL)

CREATE DATABASE sghss;

🔐 Autenticação JWT

{
  "username": "",
  "password": ""
}

```
## 📌 Endpoints Principais

### 🧑‍⚕️ Patients
```
GET /api/patients

POST /api/patients
{
  "name": "João Silva",
  "cpf": "11122233344"
}
```

### 👨‍⚕️ Professionals
```
GET /api/professionals
POST /api/professionals
PUT /api/professionals/{id}
```

### 📅 Appointments (Consultas)
```
POST /api/appointments
{
  "patientId": "UUID",
  "professionalId": "UUID",
  "dateTime": "2025-01-20T14:00:00-03:00",
  "telemedicine": false
}
```

### 📁 Medical Records (Prontuários)
```
POST /api/medical-records
GET /api/medical-records/{id}
PUT /api/medical-records/{id}
```

### 💊 Prescriptions (Prescrições)
```
POST /api/prescriptions
POST /api/prescriptions/{id}/sign
```

### 🧪 Testes com Insomnia
```
insomnia_sghss.json
```

## 👤 Autor
Paulo Ricardo Soares da Trindade
Desenvolvedor Java 
Projeto SGHSS criado para estudos e portfolio
