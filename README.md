# 🏨 API REST de Gestión Hotelera - HelloMongoDBSpringBoot2026

Este proyecto es una API REST profesional desarrollada con **Spring Boot 3.4.2** para la gestión integral de un catálogo de hoteles y sus reservas, utilizando **MongoDB** como base de datos NoSQL.

---

## 🛠️ Stack Tecnológico

* **Java**: Versión 17.
* **Framework**: Spring Boot 3.4.2.
* **Base de Datos**: MongoDB (vía Spring Data MongoDB).
* **Seguridad**: Spring Security con soporte para Basic Auth y Form Login.
* **Documentación**: OpenAPI 3 / Swagger UI (springdoc-openapi v2.8.4).
* **Productividad**: Lombok para la autogeneración de código.

---

## 🔐 Seguridad y Autenticación

El sistema implementa un modelo de seguridad robusto configurado en `SecurityConfig.java`:

* **Acceso Público (Sin Autenticación)**:
    * Consultas de lectura: `GET /hoteles`, `GET /hoteles/{id}`, `GET /hoteles/buscar`.
    * Interfaz de Swagger: `/swagger-ui/**`, `/v3/api-docs/**`.
    * Recursos estáticos: `index.html`, `/css/**`, `/javascript/**`.
* **Acceso Protegido (Requiere Rol ADMIN)**:
    * Operaciones de escritura: `POST`, `PUT`, `DELETE`.
    * Gestión de reservas: `POST /hoteles/reservas`.
* **Gestión de Usuarios**: Los usuarios se cargan desde MongoDB a través del servicio `AppUserDetailsService`.

---

## 🚀 Endpoints de la API

### 1. Gestión de Hoteles (`/hoteles`)

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **GET** | `/hoteles` | Lista todos los hoteles registrados. |
| **GET** | `/hoteles/{id}` | Obtiene un hotel por su ID de MongoDB. |
| **POST** | `/hoteles` | Crea un nuevo hotel (Protegido). |
| **PUT** | `/hoteles/{id}` | Actualiza un hotel existente (Protegido). |
| **DELETE** | `/hoteles/{id}` | Elimina un hotel del sistema (Protegido). |
**Ejemplo de crear un hotel (POST):**
```bash
curl -X 'POST' \
  'http://localhost:8080/hoteles' \
  -H 'accept: */*' \
  -H 'Authorization: Basic dXN1YXJpbzoxMjM0' \
  -H 'Content-Type: application/json' \
  -d '{
  "nombre": "Cesur Hotel",
  "calificacion": 8,
  "ubicacion": "El palo",
  "precioPorNoche": 500,
  "estrellas": 5
}'
 ```
### 2. Filtros y Búsquedas Avanzadas

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **GET** | `/hoteles/busqueda?ubicacion={txt}` | Filtra por texto en la ubicación. |
| **GET** | `/hoteles/nombre/{nombre}` | Busca un hotel por su nombre exacto. |
| **GET** | `/hoteles/calificacion/{val}` | Filtra por calificación exacta. |
| **GET** | `/hoteles/calificacion/superior/{val}` | Filtra hoteles con calificación > valor. |
| **GET** | `/hoteles/precio/superior/{precio}` | Busca hoteles con precio > valor. |
| **GET** | `/hoteles/precio/inferior/{precio}` | Busca hoteles con precio < valor. |

**Ejemplo de busqueda de nombre de hotel (GET):**
```json
{
  "id": "698cca4431d9501c657db778",
  "nombre": "Cesur Hotel",
  "calificacion": 8,
  "ubicacion": "El palo",
  "precioPorNoche": 500,
  "estrellas": 5
}
 ```
### 3. Reservas (`/hoteles/reservas`)

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **POST** | `/hoteles/reservas` | Crea una reserva validando hotel y noches. |

**Ejemplo de reserva (POST):**
```json
{
  "mensaje": "Reserva confirmada para el hotel: Eurostars Hotel Real"
  }
 ```
---



## ⚠️ Manejo de Errores Centralizado

La API utiliza un `RestControllerAdvice` para garantizar que todos los errores devuelvan un formato JSON consistente (`ErrorResponseDTO`):

* **404 Not Found**: Lanzado cuando un hotel no existe (`HotelNotFoundException`).
* **400 Bad Request**: Errores de parámetros faltantes o solicitudes de reserva inválidas.
* **401 Unauthorized**: Errores de autenticación personalizados con mensaje JSON.
* **503 Service Unavailable**: Problemas de conexión con MongoDB.

**Ejemplo de error (404):**
```json
{
  "mensaje": "Hotel no encontrado",
  "detalles": "El hotel con ID 123 no ha sido encontrado en nuestro sistema.",
  "codigo": 404
}
