# Requerimiento 1.2 - Gestión de Rutinas de Ejercicio

Este documento detalla las especificaciones técnicas y contratos de API para la gestión de Rutinas de Ejercicio en el sistema FITPOWER.

---
### User Story: manage-exercise-routines-and-exercises-crud

| | Horas estimadas |
| :--- | :---: |
| **COMO** Entrenador del gimnasio <br> **QUIERO** gestionar rutinas de ejercicio y sus ejercicios <br> **PARA** crear, visualizar, actualizar y eliminar rutinas personalizadas y ejercicios específicos para los clientes. | **?** |

---
### User Story: manage-exercise-routines-crud-create

| | Horas estimadas |
| :--- | :---: |
| **ESCENARIO 1:** Rutina de ejercicio creada exitosamente. | **?** |
| **DADO QUE** el formulario de creación de rutina está disponible <br> **Y QUE** el entrenador ingresa información válida para una nueva rutina <br> **CUANDO** el entrenador envía el formulario <br> **ENTONCES** se crea una nueva rutina de ejercicio <br> **Y** se le asigna al cliente correspondiente. | |
| **VALIDACIÓN** <br> - Autenticarse como entrenador y acceder al perfil de un cliente. <br> - Ingresar datos válidos en el formulario de creación de la rutina. <br> - Confirmar que la rutina se crea y se asigna correctamente al cliente. | |

---

#### Representación JSON

**Request (POST)**
*Endpoint: `/api/v1/clientes/{dni}/rutinas`*
```json
{
  "nombreRutina": "Rutina de Fuerza - Tren Superior",
  "descripcion": "Enfoque en pecho, hombros y tríceps.",
  "ejercicios": [
    {
      "idEjercicio": 101,
      "series": 4,
      "repeticiones": 10,
      "tiempoDescansoSeg": 60
    },
    {
      "idEjercicio": 105,
      "series": 3,
      "repeticiones": 12,
      "tiempoDescansoSeg": 45
    },
    {
      "idEjercicio": 108,
      "series": 3,
      "repeticiones": 15,
      "tiempoDescansoSeg": 45
    }
  ]
}
```

**Response (POST)**
*Status Code: 201 CREATED*
```json
{
  "idRutina": 201,
  "dniCliente": "40.000.000",
  "nombreRutina": "Rutina de Fuerza - Tren Superior",
  "descripcion": "Enfoque en pecho, hombros y tríceps.",
  "fechaAsignacion": "2024-08-26T11:00:00Z",
  "activa": true
}
```

---
### User Story: manage-exercise-routines-crud-read

| | Horas estimadas |
| :--- | :---: |
| **ESCENARIO 2:** Visualización de una rutina de ejercicio. | **?** |
| **DADO QUE** un entrenador necesita consultar la rutina activa de un cliente <br> **CUANDO** accede a la sección de rutinas del perfil del cliente <br> **ENTONCES** puede ver toda la información detallada de la rutina asignada, incluyendo sus ejercicios. | |
| **VALIDACIÓN** <br> - Autenticarse como entrenador. <br> - Acceder al perfil de un cliente que tenga una rutina activa. <br> - Confirmar que se muestra la rutina con el detalle de cada ejercicio (series, repeticiones, etc.). | |

---

#### Representación JSON

**Response (GET)**
*Endpoint: `/api/v1/clientes/{dni}/rutinas/activa`*
*Status Code: 200 OK*
```json
{
  "idRutina": 201,
  "dniCliente": "40.000.000",
  "nombreRutina": "Rutina de Fuerza - Tren Superior",
  "descripcion": "Enfoque en pecho, hombros y tríceps.",
  "fechaAsignacion": "2024-08-26T11:00:00Z",
  "activa": true,
  "ejercicios": [
    {
      "ejercicio": {
        "idEjercicio": 101,
        "nombre": "Press de Banca",
        "grupoMuscular": "Pecho"
      },
      "series": 4,
      "repeticiones": 10,
      "tiempoDescansoSeg": 60
    },
    {
      "ejercicio": {
        "idEjercicio": 105,
        "nombre": "Press Militar con mancuernas",
        "grupoMuscular": "Hombros"
      },
      "series": 3,
      "repeticiones": 12,
      "tiempoDescansoSeg": 45
    },
    {
      "ejercicio": {
        "idEjercicio": 108,
        "nombre": "Fondos en paralelas",
        "grupoMuscular": "Tríceps"
      },
      "series": 3,
      "repeticiones": 15,
      "tiempoDescansoSeg": 45
    }
  ]
}
```

---
### User Story: manage-exercise-routines-crud-update

| | Horas estimadas |
| :--- | :---: |
| **ESCENARIO 3:** Actualización de una rutina de ejercicio. | **?** |
| **DADO QUE** un entrenador necesita ajustar la rutina de un cliente <br> **CUANDO** modifica los datos de la rutina y guarda los cambios <br> **ENTONCES** la información actualizada se refleja en la rutina activa del cliente. | |
| **VALIDACIÓN** <br> - Autenticarse como entrenador y acceder a la rutina activa de un cliente. <br> - Modificar algún dato (ej: cambiar las repeticiones de un ejercicio). <br> - Guardar los cambios y confirmar que la rutina se ha actualizado. | |

---

#### Representación JSON

**Request (PUT)**
*Endpoint: `/api/v1/rutinas/{idRutina}`*
```json
{
  "nombreRutina": "Rutina de Fuerza - Tren Superior v1.1",
  "descripcion": "Ajuste de repeticiones para mayor hipertrofia.",
  "ejercicios": [
    {
      "idEjercicio": 101,
      "series": 4,
      "repeticiones": 8,
      "tiempoDescansoSeg": 60
    },
    {
      "idEjercicio": 105,
      "series": 3,
      "repeticiones": 10,
      "tiempoDescansoSeg": 45
    },
    {
      "idEjercicio": 108,
      "series": 3,
      "repeticiones": 12,
      "tiempoDescansoSeg": 45
    }
  ]
}
```

**Response (PUT)**
*Status Code: 200 OK*
```json
{
  "idRutina": 201,
  "dniCliente": "40.000.000",
  "nombreRutina": "Rutina de Fuerza - Tren Superior v1.1",
  "descripcion": "Ajuste de repeticiones para mayor hipertrofia.",
  "fechaAsignacion": "2024-08-26T11:00:00Z",
  "fechaUltimaActualizacion": "2024-09-10T09:00:00Z",
  "activa": true
}
```

---
### User Story: manage-exercise-routines-crud-delete

| | Horas estimadas |
| :--- | :---: |
| **ESCENARIO 4:** Eliminación (desactivación) de una rutina. | **?** |
| **DADO QUE** una rutina asignada a un cliente ya no es necesaria <br> **CUANDO** el entrenador la elimina <br> **ENTONCES** la rutina se desactiva y deja de ser la rutina activa del cliente. | |
| **VALIDACIÓN** <br> - Autenticarse como entrenador y acceder a la rutina de un cliente. <br> - Seleccionar la opción de eliminar. <br> - Confirmar que el cliente ya no tiene esa rutina como activa. | |

---

### Contratos referentes a la User Story

| MÉTODO | URI | Descripción | Status Code |
| :--- | :--- | :--- | :--- |
| **POST** | `/api/v1/clientes/{dni}/rutinas` | Crea una nueva rutina y la asigna a un cliente. Devuelve la rutina creada. | 201 CREATED, <br> 400 BAD REQUEST |
| **GET** | `/api/v1/clientes/{dni}/rutinas/activa` | Obtiene la rutina activa de un cliente específico. Devuelve el detalle de la rutina. | 200 OK, <br> 404 NOT FOUND |
| **PUT** | `/api/v1/rutinas/{idRutina}` | Actualiza una rutina existente. Devuelve la rutina actualizada. | 200 OK, <br> 400 BAD REQUEST, <br> 404 NOT FOUND |
| **DELETE** | `/api/v1/rutinas/{idRutina}` | Realiza una eliminación lógica de una rutina (la marca como inactiva). | 204 NO CONTENT, <br> 404 NOT FOUND |
