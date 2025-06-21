# 🌱 Sanem - Backend da Aplicação da ONG

Este é o backend da aplicação **Sanem**, uma plataforma desenvolvida para gerenciar operações internas de uma ONG, incluindo controle de usuários, doações, e autenticação segura via JWT.

---

## 🛠️ Tecnologias Utilizadas

- Java 17
- Spring Boot 3.x
- Spring Security + JWT
- Spring Data JPA
- MySQL 8.0.39
- Maven

---

## 🚀 Como executar localmente

### Pré-requisitos

- Java 17 instalado
- MySQL 8.0.39 configurado
- Maven instalado
- IDE de sua preferência (IntelliJ, Eclipse, VS Code...)

### 1. Clone o repositório

    
    bash- git clone https://github.com/bruno-gmodesto/sanem-share.git
    cd sanem-backend


### 2. Configure o banco de dados

Crie um banco no MySQL:

    CREATE DATABASE sanem;

### 3. Rode a aplicação
    ./mvnw spring-boot:run


### 4.Endpoints relevantes:

    POST /auth/login — autentica o usuário e retorna o token

    POST /auth/register — registra um novo usuário
    
    Todos os endpoints protegidos exigem o header:

    Authorization: Bearer <seu_token>

### Desenvolvedores

    Nome do responsável pelo backend
    Bruno Gonçalves Modesto, Murilo Gabriel Silva Carvalho
