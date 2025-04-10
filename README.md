# Spring Boot CRUD Application

Esta es una aplicación CRUD (Create, Read, Update, Delete) desarrollada con Spring Boot y MongoDB.

## Requisitos Previos

- Java 21
- Docker y Docker Compose
- Maven

## Tecnologías Utilizadas

- Spring Boot 3.2.3
- MongoDB
- Docker
- Maven
- Lombok
- MapStruct
- SpringDoc OpenAPI (Swagger)

## Configuración del Entorno

1. Clona el repositorio:
```bash
git clone <url-del-repositorio>
cd crud
```

2. Configura las variables de entorno:
   Crea o modifica el archivo `.env` en la raíz del proyecto con las siguientes variables:
```env
MONGO_USERNAME=tu_usuario
MONGO_PASSWORD=tu_contraseña
MONGO_DB=nombre_de_tu_db
```

## Ejecutar la Aplicación

### Usando Docker Compose (Recomendado)

1. Construye y levanta los contenedores:
```bash
docker-compose up -d --build
```

2. La aplicación estará disponible en:
   - API: http://localhost:8080
   - Swagger UI: http://localhost:8080/swagger-ui.html

### Desarrollo Local

1. Compila el proyecto:
```bash
./mvnw clean install
```

2. Ejecuta la aplicación:
```bash
./mvnw spring-boot:run
```

## Documentación API

La documentación de la API está disponible a través de Swagger UI:
- URL: http://localhost:8080/swagger-ui.html

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/
│   │   └── com/crud/
│   │       ├── controllers/    # Controladores REST
│   │       ├── models/         # Entidades y DTOs
│   │       ├── repositories/   # Repositorios MongoDB
│   │       └── services/       # Lógica de negocio
│   └── resources/
│       └── application.properties  # Configuración de la aplicación
```

## Características

- CRUD completo
- Validación de datos
- Documentación API con OpenAPI 3.0
- Tests unitarios con JUnit y Mockito
- Mapeo de objetos con MapStruct
- MongoDB como base de datos
- Contenedorización con Docker

## Desarrollo

Para desarrollo local, la aplicación incluye Spring Boot DevTools para recargas automáticas.

## Tests

Ejecuta los tests con:
```bash
./mvnw test
```

## Despliegue

1. Construye la imagen Docker:
```bash
docker build -t crud-app .
```

2. Despliega con docker-compose:
```bash
docker-compose up -d
```