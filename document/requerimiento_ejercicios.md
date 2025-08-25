# Requerimiento 1.4 - Gestión de Ejercicios

Este documento detalla las especificaciones técnicas y contratos de API para la gestión de la biblioteca de Ejercicios en el sistema FITPOWER.

---
### User Story: manage-exercises-crud

| | Horas estimadas |
| :--- | :---: |
| **COMO** Entrenador o Administrador <br> **QUIERO** gestionar la biblioteca de ejercicios del sistema <br> **PARA** poder añadir nuevos ejercicios, consultarlos, actualizarlos y eliminarlos, asegurando un catálogo completo y estandarizado para la creación de rutinas. | **?** |

---
### User Story: manage-exercises-crud-create

| | Horas estimadas |
| :--- | :---: |
| **ESCENARIO 1:** Creación exitosa de un nuevo ejercicio. | **?** |
| **DADO QUE** el formulario de creación de ejercicios está disponible <br> **Y QUE** el entrenador o administrador ingresa información válida <br> **CUANDO** envía el formulario <br> **ENTONCES** se crea un nuevo ejercicio en la biblioteca del sistema. | |
| **VALIDACIÓN** <br> - Autenticarse como Entrenador o Administrador. <br> - Acceder a la sección de "Biblioteca de Ejercicios". <br> - Ingresar datos válidos para un nuevo ejercicio y guardarlo. <br> - Confirmar que el nuevo ejercicio aparece en la lista de la biblioteca. | |

---

#### Representación JSON

**Request (POST)**
*Endpoint: `/api/v1/ejercicios`*
```json
{
  "nombre": "Press de Banca",
  "descripcion": "Ejercicio de empuje horizontal para el desarrollo del pectoral, hombros y tríceps.",
  "grupoMuscular": "Pecho"
}
```

**Response (POST)**
*Status Code: 201 CREATED*
```json
{
  "idEjercicio": 101,
  "nombre": "Press de Banca",
  "descripcion": "Ejercicio de empuje horizontal para el desarrollo del pectoral, hombros y tríceps.",
  "grupoMuscular": "Pecho"
}
```

---
### User Story: manage-exercises-crud-read

| | Horas estimadas |
| :--- | :---: |
| **ESCENARIO 2:** Visualización de la lista de ejercicios. | **?** |
| **DADO QUE** un entrenador necesita consultar los ejercicios disponibles <br> **CUANDO** accede a la biblioteca de ejercicios <br> **ENTONCES** puede ver una lista de todos los ejercicios existentes, con opción de filtrarlos por grupo muscular. | |
| **VALIDACIÓN** <br> - Autenticarse como Entrenador o Administrador. <br> - Acceder a la biblioteca de ejercicios y verificar que se listen los ejercicios. <br> - Probar el filtro por grupo muscular (ej: "Pierna") y confirmar que solo muestra los ejercicios correspondientes. | |

---

#### Representación JSON

**Response (GET)**
*Endpoint: `/api/v1/ejercicios`*
*Status Code: 200 OK*
```json
[
  {
    "idEjercicio": 101,
    "nombre": "Press de Banca",
    "grupoMuscular": "Pecho"
  },
  {
    "idEjercicio": 102,
    "nombre": "Sentadilla con barra",
    "grupoMuscular": "Pierna"
  },
  {
    "idEjercicio": 103,
    "nombre": "Dominadas",
    "grupoMuscular": "Espalda"
  }
]
```

---
### User Story: manage-exercises-crud-update

| | Horas estimadas |
| :--- | :---: |
| **ESCENARIO 3:** Actualización de un ejercicio existente. | **?** |
| **DADO QUE** la descripción de un ejercicio es incorrecta o necesita ser mejorada <br> **CUANDO** un entrenador o administrador modifica los datos del ejercicio <br> **ENTONCES** la información actualizada se refleja en la biblioteca. | |
| **VALIDACIÓN** <br> - Autenticarse como Entrenador o Administrador. <br> - Seleccionar un ejercicio de la biblioteca y editar sus campos. <br> - Guardar los cambios y confirmar que la información del ejercicio se ha actualizado. | |

---

#### Representación JSON

**Request (PUT)**
*Endpoint: `/api/v1/ejercicios/{idEjercicio}`*
```json
{
  "nombre": "Press de Banca con Barra",
  "descripcion": "Ejercicio de empuje horizontal para el desarrollo del pectoral mayor, deltoides anterior y tríceps.",
  "grupoMuscular": "Pecho"
}
```

**Response (PUT)**
*Status Code: 200 OK*
```json
{
  "idEjercicio": 101,
  "nombre": "Press de Banca con Barra",
  "descripcion": "Ejercicio de empuje horizontal para el desarrollo del pectoral mayor, deltoides anterior y tríceps.",
  "grupoMuscular": "Pecho"
}
```

---
### User Story: manage-exercises-crud-delete

| | Horas estimadas |
| :--- | :---: |
| **ESCENARIO 4:** Eliminación de un ejercicio. | **?** |
| **DADO QUE** un ejercicio ya no se utiliza o fue creado por error <br> **CUANDO** un administrador lo elimina <br> **ENTONCES** el ejercicio ya no está disponible en la biblioteca para ser añadido a nuevas rutinas. | |
| **VALIDACIÓN** <br> - Autenticarse como Administrador. <br> - Seleccionar un ejercicio y eliminarlo. <br> - Confirmar que el ejercicio ya no aparece en la lista general. | |

**Nota de Diseño:** Si un ejercicio ya forma parte de rutinas existentes, una eliminación física podría causar problemas. Se podría optar por una eliminación lógica o impedir la eliminación si el ejercicio está en uso.

---

### Contratos referentes a la User Story

| MÉTODO | URI | Descripción | Status Code |
| :--- | :--- | :--- | :--- |
| **POST** | `/api/v1/ejercicios` | Crea un nuevo ejercicio en la biblioteca. | 201 CREATED, <br> 400 BAD REQUEST |
| **GET** | `/api/v1/ejercicios` | Obtiene la lista de todos los ejercicios disponibles. Permite filtrar por `grupoMuscular`. | 200 OK |
| **GET** | `/api/v1/ejercicios/{idEjercicio}` | Obtiene la información de un ejercicio específico. | 200 OK, <br> 404 NOT FOUND |
| **PUT** | `/api/v1/ejercicios/{idEjercicio}` | Actualiza un ejercicio existente. | 200 OK, <br> 400 BAD REQUEST, <br> 404 NOT FOUND |
| **DELETE** | `/api/v1/ejercicios/{idEjercicio}` | Elimina un ejercicio de la biblioteca. | 204 NO CONTENT, <br> 404 NOT FOUND |
