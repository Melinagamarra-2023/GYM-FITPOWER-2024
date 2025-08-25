# Requerimiento 1.3 - Gestión de Planes de Nutrición

Este documento detalla las especificaciones técnicas y contratos de API para la gestión de Planes de Nutrición en el sistema FITPOWER.

---
### User Story: manage-nutrition-plans-crud

| | Horas estimadas |
| :--- | :---: |
| **COMO** Nutricionista del gimnasio <br> **QUIERO** gestionar planes de nutrición para los clientes <br> **PARA** crear, visualizar, actualizar y eliminar planes de alimentación personalizados. | **?** |

---
### User Story: manage-nutrition-plans-crud-create

| | Horas estimadas |
| :--- | :---: |
| **ESCENARIO 1:** Creación exitosa de un plan de nutrición. | **?** |
| **DADO QUE** el formulario de creación de planes está disponible <br> **Y QUE** el nutricionista ingresa información válida para un nuevo plan <br> **CUANDO** el nutricionista envía el formulario <br> **ENTONCES** se crea un nuevo plan de nutrición en el sistema <br> **Y** se le asigna al cliente correspondiente. | |
| **VALIDACIÓN** <br> - Autenticarse como nutricionista y acceder al perfil de un cliente. <br> - Ingresar datos válidos en el formulario de creación del plan. <br> - Confirmar que el plan de nutrición se crea y se asigna correctamente al cliente. | |

---

#### Representación JSON

**Request (POST)**
*Endpoint: `/api/v1/clientes/{dni}/planes-nutricion`*
```json
{
  "nombrePlan": "Plan de Definición Muscular",
  "descripcion": "Plan enfocado en la reducción de grasa corporal manteniendo la masa muscular.",
  "caloriasDiariasObjetivo": 2200,
  "carbosDiariosObjetivo": 180,
  "protesDiariasObjetivo": 200,
  "grasasDiariasObjetivo": 70,
  "comidas": [
    {
      "tipoComida": "DESAYUNO",
      "descripcion": "Avena con proteína y frutas."
    },
    {
      "tipoComida": "ALMUERZO",
      "descripcion": "Pechuga de pollo a la plancha con arroz integral y brócoli."
    },
    {
      "tipoComida": "CENA",
      "descripcion": "Salmón al horno con ensalada de hojas verdes."
    }
  ]
}
```

**Response (POST)**
*Status Code: 201 CREATED*
```json
{
  "idPlan": 123,
  "dniCliente": "40.000.000",
  "nombrePlan": "Plan de Definición Muscular",
  "descripcion": "Plan enfocado en la reducción de grasa corporal manteniendo la masa muscular.",
  "caloriasDiariasObjetivo": 2200,
  "carbosDiariosObjetivo": 180,
  "protesDiariasObjetivo": 200,
  "grasasDiariasObjetivo": 70,
  "fechaAsignacion": "2024-08-26T10:00:00Z",
  "activo": true
}
```

---
### User Story: manage-nutrition-plans-crud-read

| | Horas estimadas |
| :--- | :---: |
| **ESCENARIO 2:** Visualización del plan de nutrición de un cliente. | **?** |
| **DADO QUE** un nutricionista necesita consultar el plan de nutrición activo de un cliente <br> **CUANDO** accede a la sección de planes del perfil del cliente <br> **ENTONCES** puede ver toda la información detallada del plan de nutrición asignado. | |
| **VALIDACIÓN** <br> - Autenticarse como nutricionista. <br> - Acceder a la lista de clientes y seleccionar uno. <br> - Navegar a la sección de "Plan de Nutrición" y confirmar que se muestra el plan activo con todos sus detalles. | |

---

#### Representación JSON

**Response (GET)**
*Endpoint: `/api/v1/clientes/{dni}/planes-nutricion/activo`*
*Status Code: 200 OK*
```json
{
  "idPlan": 123,
  "dniCliente": "40.000.000",
  "nombrePlan": "Plan de Definición Muscular",
  "descripcion": "Plan enfocado en la reducción de grasa corporal manteniendo la masa muscular.",
  "caloriasDiariasObjetivo": 2200,
  "carbosDiariosObjetivo": 180,
  "protesDiariasObjetivo": 200,
  "grasasDiariasObjetivo": 70,
  "fechaAsignacion": "2024-08-26T10:00:00Z",
  "activo": true,
  "comidas": [
    {
      "idComida": 501,
      "tipoComida": "DESAYUNO",
      "descripcion": "Avena con proteína y frutas."
    },
    {
      "idComida": 502,
      "tipoComida": "ALMUERZO",
      "descripcion": "Pechuga de pollo a la plancha con arroz integral y brócoli."
    },
    {
      "idComida": 503,
      "tipoComida": "MERIENDA",
      "descripcion": "Yogur griego con un puñado de almendras."
    },
    {
      "idComida": 504,
      "tipoComida": "CENA",
      "descripcion": "Salmón al horno con ensalada de hojas verdes."
    }
  ]
}
```

---
### User Story: manage-nutrition-plans-crud-update

| | Horas estimadas |
| :--- | :---: |
| **ESCENARIO 3:** Actualización de un plan de nutrición existente. | **?** |
| **DADO QUE** un nutricionista necesita ajustar el plan de nutrición de un cliente basado en su progreso <br> **CUANDO** modifica los datos del plan y guarda los cambios <br> **ENTONCES** la información actualizada se refleja en el plan de nutrición activo del cliente. | |
| **VALIDACIÓN** <br> - Autenticarse como nutricionista y acceder al plan de nutrición activo de un cliente. <br> - Modificar uno o más campos del plan (ej: aumentar las calorías objetivo). <br> - Guardar los cambios y confirmar que se actualizan correctamente al volver a consultar el plan. | |

---

#### Representación JSON

**Request (PUT)**
*Endpoint: `/api/v1/planes-nutricion/{idPlan}`*
```json
{
  "nombrePlan": "Plan de Definición Muscular v2",
  "descripcion": "Plan ajustado para incrementar la ingesta calórica en días de entrenamiento intenso.",
  "caloriasDiariasObjetivo": 2400,
  "carbosDiariosObjetivo": 200,
  "protesDiariasObjetivo": 220,
  "grasasDiariasObjetivo": 80,
  "comidas": [
    {
      "idComida": 501,
      "tipoComida": "DESAYUNO",
      "descripcion": "Avena con proteína y frutas."
    },
    {
      "idComida": 502,
      "tipoComida": "ALMUERZO",
      "descripcion": "Pechuga de pollo a la plancha con arroz integral y brócoli."
    },
    {
      "idComida": 505,
      "tipoComida": "SNACK",
      "descripcion": "Batido de proteínas post-entrenamiento."
    },
    {
      "idComida": 504,
      "tipoComida": "CENA",
      "descripcion": "Salmón al horno con ensalada de hojas verdes y aguacate."
    }
  ]
}
```

**Response (PUT)**
*Status Code: 200 OK*
```json
{
  "idPlan": 123,
  "dniCliente": "40.000.000",
  "nombrePlan": "Plan de Definición Muscular v2",
  "descripcion": "Plan ajustado para incrementar la ingesta calórica en días de entrenamiento intenso.",
  "caloriasDiariasObjetivo": 2400,
  "carbosDiariosObjetivo": 200,
  "protesDiariasObjetivo": 220,
  "grasasDiariasObjetivo": 80,
  "fechaAsignacion": "2024-08-26T10:00:00Z",
  "fechaUltimaActualizacion": "2024-09-15T14:30:00Z",
  "activo": true
}
```

---
### User Story: manage-nutrition-plans-crud-delete

| | Horas estimadas |
| :--- | :---: |
| **ESCENARIO 4:** Eliminación (o desactivación) de un plan de nutrición. | **?** |
| **DADO QUE** un plan de nutrición ya no es relevante o fue creado por error <br> **CUANDO** el nutricionista selecciona la opción de eliminar el plan <br> **ENTONCES** el plan se desactiva y ya no se muestra como el plan activo del cliente. | |
| **VALIDACIÓN** <br> - Autenticarse como nutricionista y acceder al plan de nutrición de un cliente. <br> - Seleccionar la opción de eliminar. <br> - Confirmar que el plan ya no aparece como activo para el cliente (se puede verificar que el plan queda en el historial pero con estado "inactivo"). | |

---

### Contratos referentes a la User Story

| MÉTODO | URI | Descripción | Status Code |
| :--- | :--- | :--- | :--- |
| **POST** | `/api/v1/clientes/{dni}/planes-nutricion` | Crea un nuevo plan de nutrición y lo asigna a un cliente. Devuelve el nuevo plan creado. | 201 CREATED, <br> 400 BAD REQUEST |
| **GET** | `/api/v1/clientes/{dni}/planes-nutricion/activo` | Obtiene el plan de nutrición activo de un cliente específico. Devuelve el perfil completo del plan. | 200 OK, <br> 404 NOT FOUND |
| **PUT** | `/api/v1/planes-nutricion/{idPlan}` | Actualiza un plan de nutrición existente. Devuelve el plan actualizado. | 200 OK, <br> 400 BAD REQUEST, <br> 404 NOT FOUND |
| **DELETE** | `/api/v1/planes-nutricion/{idPlan}` | Realiza una eliminación lógica de un plan de nutrición (lo marca como inactivo). | 204 NO CONTENT, <br> 404 NOT FOUND |
