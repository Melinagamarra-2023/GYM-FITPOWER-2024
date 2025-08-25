# Historias de Usuario - FITPOWER

Este documento traduce los requerimientos del sistema a Historias de Usuario, centradas en el valor aportado a cada rol. Esta versión ha sido refinada para mejorar la granularidad y asegurar la cobertura de todas las funcionalidades necesarias.

---

### Roles de Usuario

* **Cliente:** Miembro del gimnasio que utiliza la plataforma para seguir sus planes y registrar su progreso.
* **Entrenador:** Personal trainer responsable de crear y gestionar las rutinas de ejercicio de los clientes.
* **Nutricionista:** Especialista responsable de crear y gestionar los planes de nutrición de los clientes.
* **Administrador:** Colaborador del gimnasio con permisos para gestionar la operativa general (clientes, personal, clases).

---

### 1. Gestión de Perfiles y Usuarios

* **HU-01: Crear Perfil de Cliente**
    * **Como** `Administrador`, **quiero** `crear un perfil para un nuevo cliente con su información personal básica y gimnasio asignado` **para** `registrarlo oficialmente en el sistema`.
* **HU-02: Ver y Actualizar Perfil Propio**
    * **Como** `Cliente`, **quiero** `ver y actualizar mi información personal y mis objetivos de fitness` **para** `mantener mis datos al día y que mis coaches tengan la información correcta`.
* **HU-03: Asignar Profesionales a Cliente**
    * **Como** `Administrador`, **quiero** `asignar entrenadores y nutricionistas a nuevos clientes` **para** `garantizar que cada miembro reciba la atención adecuada desde el inicio`.

### 2. Gestión de Ejercicios y Rutinas

* **HU-04: Gestionar Biblioteca de Ejercicios**
    * **Como** `Entrenador` o `Administrador`, **quiero** `gestionar una biblioteca de ejercicios (añadir, editar, ver)` **para** `tener un catálogo estandarizado y disponible al crear rutinas`.
* **HU-05: Crear Rutina Personalizada**
    * **Como** `Entrenador`, **quiero** `crear rutinas personalizadas seleccionando ejercicios de la biblioteca, y definiendo series, repeticiones y descansos,` **para** `adaptar el entrenamiento a las necesidades de cada cliente`.
* **HU-06: Asignar Rutina a Cliente**
    * **Como** `Entrenador`, **quiero** `asignar una rutina creada a un cliente específico` **para** `que pueda comenzar con su plan de entrenamiento`.
* **HU-07: Visualizar Rutina Asignada**
    * **Como** `Cliente`, **quiero** `ver mi rutina de entrenamiento detallada para el día o la semana` **para** `saber exactamente qué ejercicios debo realizar`.
* **HU-08: Registrar Entrenamiento Diario**
    * **Como** `Cliente`, **quiero** `registrar mis entrenamientos diarios (peso utilizado, repeticiones logradas, etc.)` **para** `llevar un control de mi progreso y compartirlo con mi entrenador`.
#### **Criterios de Aceptación:**

**Escenario 1: Registro Exitoso de un Ejercicio**
* **Dado** que el cliente ha iniciado sesión y está viendo su "Rutina del Día".
* **Cuando** selecciona un ejercicio asignado de la lista (ej: "Press de Banca").
* **Y** completa los campos obligatorios: "Peso Utilizado", "Repeticiones Realizadas" y "Series Completadas".
* **Y** presiona el botón "Guardar Registro".
* **Entonces** el sistema debe validar que los datos son correctos (ej: numéricos y dentro de un rango lógico).
* **Y** el sistema debe guardar la entrada en el historial de entrenamiento del cliente, asociándola con la fecha actual y el ejercicio correspondiente.
* **Y** el sistema debe mostrar un mensaje de confirmación visible y claro (ej: "¡Entrenamiento registrado con éxito!").
* **Y** el ejercicio en la lista de la rutina del día debe marcarse visualmente como "Completado".

**Escenario 2: Intento de Registro con Datos Inválidos o Faltantes**
* **Dado** que el cliente está en la pantalla de registro de un ejercicio.
* **Cuando** deja en blanco un campo obligatorio como "Peso Utilizado".
* **Y** presiona el botón "Guardar Registro".
* **Entonces** el sistema **no** debe guardar el registro.
* **Y** debe mostrar un mensaje de error específico junto al campo correspondiente (ej: "Este campo es obligatorio").
* **Y** los datos que sí fueron ingresados correctamente deben permanecer en sus campos.

**Escenario 3: Registro con Observaciones Opcionales**
* **Dado** que el cliente está registrando un ejercicio de manera exitosa.
* **Cuando** además escribe una nota en el campo opcional de "Observaciones" (ej: "Sentí una ligera molestia en el hombro izquierdo en la última serie").
* **Y** presiona el botón "Guardar Registro".
* **Entonces** la observación debe guardarse junto con los demás datos del registro del ejercicio.
* **Y** el entrenador asignado debe poder ver esta observación al revisar el historial del cliente.

**Escenario 4: Visualización del Registro en el Historial**
* **Dado** que el cliente ha guardado exitosamente el registro de uno o más ejercicios.
* **Cuando** navega a su sección de "Historial de Progreso".
* **Entonces** debe poder ver una entrada para el día de hoy con los detalles de los ejercicios registrados (peso, repeticiones, series y observaciones si las hubiera).

### 3. Gestión de Planes de Nutrición

* **HU-09: Crear Plan de Nutrición**
    * **Como** `Nutricionista`, **quiero** `crear planes de alimentación personalizados` **para** `ayudar a los clientes a alcanzar sus objetivos de salud`.
* **HU-10: Asignar Plan de Nutrición**
    * **Como** `Nutricionista`, **quiero** `asignar un plan de alimentación a un cliente específico` **para** `que pueda seguir las pautas nutricionales recomendadas`.
* **HU-11: Visualizar Plan de Nutrición Asignado**
    * **Como** `Cliente`, **quiero** `consultar mi plan de nutrición asignado` **para** `saber qué debo comer cada día`.
* **HU-12: Registrar Ingesta Diaria**
    * **Como** `Cliente`, **quiero** `registrar mi ingesta diaria de alimentos` **para** `que mi nutricionista pueda evaluar mi adherencia al plan y hacer ajustes`.
#### **Criterios de Aceptación:**

**Escenario 1: Registro Exitoso de una Comida del Plan**
* **Dado** que el cliente está viendo su "Plan de Nutrición del Día".
* **Cuando** selecciona una comida asignada (ej: "Almuerzo").
* **Y** confirma o ajusta las porciones de los alimentos sugeridos en su plan.
* **Y** presiona el botón "Registrar Comida".
* **Entonces** el sistema debe guardar la ingesta en el historial de nutrición del cliente para la fecha actual.
* **Y** el sistema debe mostrar un mensaje de confirmación (ej: "¡Comida registrada con éxito!").
* **Y** la comida en el plan del día debe marcarse visualmente como "Registrada".

**Escenario 2: Registro de Alimentos Adicionales (Fuera del Plan)**
* **Dado** que el cliente está registrando una comida.
* **Cuando** añade un alimento que no estaba en su plan original (ej: "1 porción de tarta").
* **Y** guarda el registro.
* **Entonces** el sistema debe guardar la ingesta completa, incluyendo el alimento adicional.
* **Y** el alimento adicional debe estar claramente marcado como "Fuera del Plan" en el historial.
* **Y** el nutricionista debe poder identificar fácilmente estas desviaciones al revisar el progreso.

**Escenario 3: Intento de Registro con Datos Incompletos**
* **Dado** que el cliente está añadiendo un alimento manualmente.
* **Cuando** deja en blanco un campo obligatorio como "Nombre del Alimento" o "Cantidad".
* **Y** intenta guardar el registro.
* **Entonces** el sistema **no** debe guardar la ingesta.
* **Y** debe mostrar un mensaje de error claro indicando qué campo falta por completar.

**Escenario 4: Visualización del Registro en el Historial Nutricional**
* **Dado** que el cliente ha registrado una o más comidas durante el día.
* **Cuando** navega a su sección de "Historial de Nutrición".
* **Entonces** debe poder ver un resumen de su ingesta del día, con el detalle de los alimentos consumidos en cada comida y las calorías/macros correspondientes (si aplica).

### 4. Seguimiento, Evaluación y Comunicación

* **HU-13: Evaluar Progreso del Cliente**
    * **Como** `Entrenador` o `Nutricionista`, **quiero** `visualizar el progreso de mis clientes con gráficos y métricas` **para** `evaluar su rendimiento y tomar decisiones informadas`.
#### **Criterios de Aceptación:**

**Escenario 1: Vista General del Progreso del Cliente**
* **Dado** que un Entrenador o Nutricionista ha iniciado sesión y ha seleccionado a uno de sus clientes.
* **Cuando** navega a la sección de "Evaluar Progreso" de ese cliente.
* **Entonces** el sistema debe mostrar un panel de control (dashboard) con métricas clave resumidas del último mes.
* **Y** este panel debe incluir secciones separadas para "Rendimiento de Entrenamiento" (ej: total de entrenamientos completados) y "Adherencia Nutricional" (ej: promedio de calorías diarias).

**Escenario 2: Visualización de Gráficos de Progreso en Entrenamiento**
* **Dado** que el profesional está en el panel de progreso.
* **Cuando** selecciona un ejercicio específico (ej: "Sentadilla") y un rango de fechas.
* **Entonces** el sistema debe generar un gráfico de líneas que muestre la evolución del "Peso Utilizado" para ese ejercicio a lo largo del tiempo.
* **Y** el gráfico debe tener ejes claramente etiquetados (Fecha y Peso) y ser fácil de interpretar.

**Escenario 3: Análisis de Adherencia Nutricional**
* **Dado** que el profesional está en el panel de progreso.
* **Cuando** selecciona la pestaña de "Nutrición" y un rango de fechas.
* **Entonces** el sistema debe mostrar un gráfico de barras comparando las "Calorías Consumidas" versus las "Calorías Objetivo" para cada día.
* **Y** debe mostrar un gráfico circular (pie chart) con la distribución promedio de macronutrientes (proteínas, carbohidratos, grasas) durante ese período.

**Escenario 4: Manejo de Ausencia de Datos**
* **Dado** que el profesional está visualizando el progreso de un cliente.
* **Cuando** selecciona un rango de fechas en el que el cliente no ha registrado ninguna actividad.
* **Entonces** el sistema no debe mostrar un error.
* **Y** en lugar del gráfico, debe mostrar un mensaje claro como: "El cliente no ha registrado datos en este período."

* **HU-14: Ajustar Planes Asignados**
    * **Como** `Entrenador` o `Nutricionista`, **quiero** `modificar fácilmente los planes de un cliente` **para** `adaptarlos a su evolución y necesidades cambiantes`.
#### **Criterios de Aceptación:**

**Escenario 1: Modificación Exitosa de una Rutina de Entrenamiento**
* **Dado** que un Entrenador está viendo la rutina activa de uno de sus clientes.
* **Cuando** edita un ejercicio existente (ej: aumenta el peso objetivo de "Press de Banca" de 60kg a 65kg).
* **Y** presiona el botón "Guardar Cambios".
* **Entonces** el sistema debe actualizar la rutina del cliente con la nueva configuración.
* **Y** el sistema debe registrar que se ha creado una nueva versión del plan, manteniendo la versión anterior en el historial para futuras consultas.
* **Y** el sistema debe mostrar un mensaje de confirmación ("La rutina ha sido actualizada con éxito").
* **Y** el sistema debe generar una notificación para el cliente informándole del ajuste (ver HU-15).

**Escenario 2: Reemplazo Completo de un Plan de Nutrición**
* **Dado** que un Nutricionista ha decidido cambiar por completo el plan de un cliente.
* **Cuando** asigna un nuevo plan de nutrición al cliente, seleccionando la opción de "reemplazar el plan actual".
* **Y** confirma la acción.
* **Entonces** el plan de nutrición activo del cliente debe ser el nuevo plan asignado.
* **Y** el plan anterior debe ser archivado automáticamente en el historial del cliente.
* **Y** el sistema debe mostrar un mensaje de confirmación ("El nuevo plan de nutrición ha sido asignado correctamente").

**Escenario 3: Intento de Guardar un Ajuste con Datos Inválidos**
* **Dado** que un Entrenador está modificando una rutina.
* **Cuando** introduce un valor no numérico en un campo que requiere un número (ej: "muchas" en el campo "Repeticiones").
* **Y** intenta guardar los cambios.
* **Entonces** el sistema **no** debe guardar la modificación.
* **Y** debe mostrar un mensaje de error claro y específico junto al campo inválido (ej: "El valor debe ser un número entero").

* **HU-15: Recibir Notificación de Ajustes**
    * **Como** `Cliente`, **quiero** `recibir una notificación cuando mi plan ha sido ajustado` **para** `estar siempre al tanto de las últimas recomendaciones`.
#### **Criterios de Aceptación:**

**Escenario 1: Recepción de Notificación de Ajuste de Rutina**
* **Dado** que un Entrenador ha modificado y guardado exitosamente la rutina de un cliente (según HU-14).
* **Cuando** el cliente inicia sesión en la plataforma o la abre en su dispositivo.
* **Entonces** debe aparecer un indicador de notificación visible (ej: un punto rojo sobre un ícono de campana).
* **Y** al abrir el centro de notificaciones, debe ver un mensaje claro y conciso, como: "Tu entrenador ha actualizado tu rutina de entrenamiento. ¡Échale un vistazo!".

**Escenario 2: Interacción con la Notificación**
* **Dado** que el cliente ha recibido una notificación de ajuste.
* **Cuando** hace clic o pulsa sobre la notificación.
* **Entonces** el sistema debe redirigirlo directamente a la pantalla donde puede ver su nuevo plan o el plan actualizado.
* **Y** una vez que la notificación ha sido vista, su estado debe cambiar de "no leída" a "leída".

**Escenario 3: Notificación por Ajuste de Plan de Nutrición**
* **Dado** que un Nutricionista ha modificado el plan de nutrición de un cliente.
* **Cuando** el cliente revisa sus notificaciones.
* **Entonces** debe ver un mensaje específico para ese cambio, como: "Tu nutricionista ha realizado ajustes en tu plan de alimentación."

**Escenario 4: Múltiples Notificaciones**
* **Dado** que el cliente no ha iniciado sesión en varios días y se han realizado múltiples ajustes.
* **Cuando** abre su centro de notificaciones.
* **Entonces** debe ver una lista de todas las notificaciones no leídas, ordenadas cronológicamente (la más reciente primero).

* **HU-16: Acceder a Historial Completo**
    * **Como** `Cliente`, `Entrenador` o `Nutricionista`, **quiero** `acceder a un historial completo de actividades, planes y evaluaciones` **para** `tener una visión 360° del recorrido del cliente`.
* **HU-17: Detectar Estancamiento**
    * **Como** `Entrenador` o `Nutricionista`, **quiero** `que el sistema me alerte sobre posibles estancamientos en el progreso de un cliente` **para** `poder intervenir de manera proactiva`.
* **HU-18: Generar Informes de Rendimiento**
    * **Como** `Entrenador` o `Nutricionista`, **quiero** `generar informes de rendimiento detallados` **para** `discutir el progreso con el cliente en las sesiones de evaluación`.

### 5. Gestión Operativa del Gimnasio

* **HU-19: Planificar Clases y Sesiones**
    * **Como** `Administrador`, **quiero** `programar clases grupales y sesiones individuales en un calendario` **para** `gestionar la oferta de actividades del gimnasio`.
* **HU-20: Reservar Asistencia a Clases**
    * **Como** `Cliente`, **quiero** `ver el calendario de clases y reservar mi asistencia` **para** `planificar mi semana de entrenamiento y asegurar mi lugar`.