# Requerimiento 1.1 - Gestión de Clientes (CRUD)

Este documento detalla las especificaciones técnicas y contratos de API para la gestión de Clientes en el sistema FITPOWER.

---
### User Story: manage-clients-crud

| | Horas estimadas |
| :--- | :---: |
| **COMO** Administrador del gimnasio <br> **QUIERO** gestionar perfiles de clientes <br> **PARA** registrar, visualizar, actualizar y eliminar su información personal, objetivos, estado físico inicial y asignación a un gimnasio específico. | **?** |

---
### User Story: manage-clients-crud-create

| | Horas estimadas |
| :--- | :---: |
| **ESCENARIO 1:** Cliente se registra exitosamente. | **?** |
| **DADO QUE** el formulario de registro está disponible <br> **Y QUE** el administrador ingresa información válida <br> **CUANDO** el administrador envía el formulario <br> **ENTONCES** se crea un nuevo perfil de cliente <br> **Y** se le asigna a un gimnasio específico. | |
| **VALIDACIÓN** <br> - Autenticarse como administrador y acceder al formulario de registro. <br> - Ingresar datos válidos en el formulario de registro. <br> - Confirmar que el perfil del cliente se crea y se asigna correctamente al gimnasio. | |

---

#### Representación JSON

**Request (POST)**
*Endpoint: `/api/v1/clientes`*
```json
{
  "nombre": "Juan",
  "apellido": "Perez",
  "dni": "40.000.000",
  "email": "juan.perez@example.com",
  "telefono": "+12-(376)-4345-6789",
  "direccion": "calle falsa 123",
  "objetivos": "Perder peso",
  "estadoFisicoInicial": "Sobrepeso",
  "gimnasioAsignado": "Gimnasio Central"
}

---
### User Story: manage-clients-crud-read

**ESCENARIO 2:** Visualización de la información de un cliente.

**DADO QUE** el administrador necesita consultar la información de un cliente por DNI
**CUANDO** accede al perfil del cliente
**ENTONCES** puede ver toda la información registrada del cliente.

**VALIDACIÓN**
- Autenticarse como administrador.
- Acceder a la lista de clientes.
- Seleccionar un cliente y visualizar su perfil completo.

**Horas estimadas:** ?

---
#### Representación JSON

**Response (GET)**
*Endpoint: `/api/v1/clientes/{dni}`*
*Status Code: 200 OK*
```json
{
  "idCliente": 1,
  "nombre": "Juan",
  "apellido": "Perez",
  "dni": "40.000.000",
  "email": "juan.perez@example.com",
  "telefono": "+12-(376)-4345-6789",
  "direccion": "calle falsa 123",
  "objetivos": "Perder peso",
  "estadoFisicoInicial": "Sobrepeso",
  "gimnasioAsignado": "Gimnasio Central",
  "fechaRegistro": "2024-08-26T10:00:00Z",
  "fechaUltimaActualizacion": "2024-08-26T12:00:00Z"
}

---
### User Story: manage-clients-crud-update

**ESCENARIO 3:** Actualización de la información de un cliente.

**DADO QUE** el administrador necesita actualizar la información de un cliente
**CUANDO** modifica los datos del cliente y guarda los cambios
**ENTONCES** la información actualizada se refleja en el perfil del cliente.

**VALIDACIÓN**
- Autenticarse como administrador y acceder al perfil del cliente.
- Modificar datos del cliente.
- Guardar los cambios y confirmar que se actualizan correctamente.

**Horas estimadas:** ?

---
#### Representación JSON

**Request (PUT)**
*Endpoint: `/api/v1/clientes/{dni}`*
```json
{
  "nombre": "Juan",
  "apellido": "Perez",
  "email": "juan.perez.nuevo@example.com",
  "telefono": "+12-(376)-4111-2222",
  "direccion": "calle verdadera 456",
  "objetivos": "Ganar masa muscular",
  "gimnasioAsignado": "Gimnasio Norte"
}

---
### User Story: manage-clients-crud-delete

**ESCENARIO 4:** Eliminación de un cliente.

**DADO QUE** el administrador necesita eliminar un cliente del sistema
**CUANDO** selecciona la opción de eliminar
**ENTONCES** el cliente se elimina y ya no está disponible en la lista de clientes.

**VALIDACIÓN**
- Autenticarse como administrador y acceder a la lista de clientes.
- Seleccionar un cliente y eliminarlo.
- Confirmar que el cliente se elimina de la lista.

**Horas estimadas:** ?

---
### Contratos referentes a la User Story

| MÉTODO | URI | Descripción | Status Code |
| :--- | :--- | :--- | :--- |
| **POST** | `/api/v1/clientes` | Crear un nuevo perfil de cliente. Devuelve el nuevo perfil creado. | 201 CREATED, <br> 400 BAD REQUEST |
| **GET** | `/api/v1/clientes` | Obtener la lista de todos los clientes. Devuelve una lista de perfiles. | 200 OK |
| **GET** | `/api/v1/clientes/{dni}` | Obtener la información de un cliente específico. Devuelve el perfil del cliente. | 200 OK, <br> 404 NOT FOUND |
| **PUT** | `/api/v1/clientes/{dni}` | Actualiza un cliente existente. Devuelve el cliente actualizado. | 200 OK, <br> 400 BAD REQUEST, <br> 404 NOT FOUND |
| **DELETE** | `/api/v1/clientes/{dni}` | Eliminar un cliente existente. | 204 NO CONTENT, <br> 404 NOT FOUND |

