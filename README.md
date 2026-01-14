# Projeto Full Stack – Spring Boot + React (Vite + Tailwind)

Aplicação web **full-stack** desenvolvida para a Pós-Graduação, integrando **backend Spring Boot** com **frontend React**, comunicando-se via **APIs RESTful**, com **CRUD completo**, **validação**, **tratamento de erros**, **autenticação/autorização com JWT** e **logout seguro**.

---

## 🧩 Visão Geral

A aplicação permite o gerenciamento de **Carros**, oferecendo:

- CRUD completo (Create, Read, Update, Delete)
- Busca por critérios (modelo, fabricante e país)
- Paginação de resultados
- Autenticação com JWT
- Rotas protegidas no backend
- Logout seguro com revogação de token
- Validação de dados e tratamento de erros padronizado
- Frontend moderno desenvolvido com React, Vite e Tailwind CSS

---

## 🏗️ Estrutura do Projeto

O projeto utiliza um **monorepo**, onde o frontend está localizado dentro do backend:

```
projeto-pos-fullstack/
├── pom.xml
├── src/
│   └── main/
│       ├── java/
│       └── resources/
└── frontend/
    ├── package.json
    ├── vite.config.ts
    ├── tailwind.config.js
    └── src/
```

---

## 🔧 Tecnologias Utilizadas

### Backend
- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- JWT (Auth0)
- Bean Validation
- Lombok
- H2 Database (ambiente de desenvolvimento)
- Maven

### Frontend
- React
- Vite
- TypeScript
- Tailwind CSS
- Fetch API
- LocalStorage para armazenamento do token JWT

---

## 🔐 Segurança

- Autenticação via endpoint `/api/usuarios/login`
- Autorização baseada em JWT
- Token enviado via header:
  ```
  Authorization: Bearer <token>
  ```
- Rotas protegidas no backend com Spring Security
- Logout seguro com blacklist de tokens
- Senhas criptografadas com BCrypt

---

## ▶️ Como Executar o Projeto

### Pré-requisitos
- Java 21
- Maven
- Node.js e npm instalados no sistema

---

### Backend (Spring Boot)

Na raiz do projeto (onde está o `pom.xml`):

```bash
mvn spring-boot:run
```

Backend disponível em:
```
http://localhost:8080
```

---

### Frontend (React + Vite)

Em ambientes corporativos onde não é permitido instalar plugins no IntelliJ, o frontend deve ser executado via **Terminal**.

```bash
cd frontend
npm install
npm run dev
```

Frontend disponível em:
```
http://localhost:5173
```

---

## 🔄 Integração Frontend ↔ Backend

O frontend utiliza um proxy configurado no Vite:

```ts
server: {
  proxy: {
    '/api': 'http://localhost:8080'
  }
}
```

Isso evita problemas de CORS durante o desenvolvimento.

---

## 🧪 Banco de Dados

- Banco em memória H2
- Dados iniciais carregados via `data.sql`
- Console H2 disponível em:
```
http://localhost:8080/h2-console
```

---

## 🚀 Deploy (Opcional)

O projeto está preparado para:
- Containerização com Docker
- Deploy do backend e frontend
- Uso de Docker Compose
- Implantação em ambientes de produção (AWS, Netlify, etc.)

---

## 📚 Considerações Finais

Este projeto demonstra:
- Integração completa entre frontend e backend
- Aplicação de boas práticas em APIs REST
- Implementação de segurança em aplicações web
- Organização e clareza de código
- Fluxo profissional de desenvolvimento full-stack

---

## 👤 Autor

**Thiago Siqueira dos Santos**  
Projeto desenvolvido como parte da Pós-Graduação em Desenvolvimento Full Stack.
