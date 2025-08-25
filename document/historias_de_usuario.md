# Historias de Usuario - FITPOWER

Este documento traduce los requerimientos del sistema a Historias de Usuario, centradas en el valor aportado a cada rol.

---

### Roles de Usuario

* **Cliente:** Miembro del gimnasio que utiliza la plataforma para seguir sus planes y registrar su progreso.
* **Entrenador:** Personal trainer responsable de crear y gestionar las rutinas de ejercicio de los clientes.
* **Nutricionista:** Especialista responsable de crear y gestionar los planes de nutrición de los clientes.
* **Administrador:** Colaborador del gimnasio con permisos para gestionar la operativa general (clientes, personal, clases).

---

### 1. Gestión de Clientes y Rutinas de Ejercicio

* **HU-01: Gestión de Perfil de Cliente**
    * **Como** Administrador, **quiero** crear y gestionar un perfil para cada cliente con su información personal, objetivos y gimnasio asignado **para** mantener un registro centralizado y completo de todos los miembros.

* **HU-02: Creación de Rutinas**
    * **Como** Entrenador, **quiero** una herramienta para crear rutinas personalizadas, seleccionando ejercicios, series, repeticiones y descansos, **para** adaptar el entrenamiento a las necesidades específicas de cada cliente.

* **HU-03: Asignación de Rutinas**
    * **Como** Entrenador, **quiero** asignar las rutinas creadas a mis clientes **para** que puedan comenzar con su plan de entrenamiento.

* **HU-04: Registro de Entrenamiento (Diario)**
    * **Como** Cliente, **quiero** registrar mis entrenamientos diarios (ejercicios, peso, etc.) **para** llevar un control de mi progreso y compartirlo con mi entrenador.

### 2. Gestión de Planes de Nutrición

* **HU-05: Creación de Planes de Nutrición**
    * **Como** Nutricionista, **quiero** una herramienta para crear planes de alimentación personalizados **para** ayudar a los clientes a alcanzar sus objetivos de salud.

* **HU-06: Asignación de Planes de Nutrición**
    * **Como** Nutricionista, **quiero** asignar los planes de alimentación a mis clientes **para** que puedan seguir las pautas nutricionales recomendadas.

* **HU-07: Registro de Nutrición (Diario)**
    * **Como** Cliente, **quiero** registrar mi ingesta diaria de alimentos **para** que mi nutricionista pueda evaluar mi adherencia al plan y hacer ajustes.

### 3. Evaluación y Ajuste de Planes

* **HU-08: Evaluación de Progreso**
    * **Como** Entrenador/Nutricionista, **quiero** visualizar el progreso de mis clientes con gráficos y métricas **para** evaluar su rendimiento y tomar decisiones informadas.

* **HU-09: Ajuste de Planes**
    * **Como** Entrenador/Nutricionista, **quiero** poder modificar fácilmente los planes de un cliente **para** adaptarlos a su evolución y necesidades cambiantes.

* **HU-10: Notificación de Ajustes**
    * **Como** Cliente, **quiero** recibir una notificación cuando mi plan de entrenamiento o nutrición ha sido ajustado **para** estar siempre al tanto de las últimas recomendaciones.

### 4. Gestión de Información y Comunicación

* **HU-11: Historial del Cliente**
    * **Como** Cliente, Entrenador o Nutricionista, **quiero** acceder a un historial completo de actividades, planes y evaluaciones **para** tener una visión 360° del recorrido del cliente.

### 5. Monitoreo y Optimización

* **HU-12: Detección de Estancamiento**
    * **Como** Entrenador/Nutricionista, **quiero** que el sistema me alerte sobre posibles estancamientos en el progreso de un cliente **para** poder intervenir de manera proactiva.

* **HU-13: Generación de Informes**
    * **Como** Entrenador/Nutricionista, **quiero** generar informes de rendimiento detallados **para** discutir el progreso con el cliente en las sesiones de evaluación.

### 6. Gestión Operativa del Gimnasio

* **HU-14: Asignación de Entrenadores/Nutricionistas**
    * **Como** Administrador, **quiero** asignar entrenadores y nutricionistas a nuevos clientes **para** garantizar que cada miembro reciba la atención adecuada desde el inicio.

* **HU-15: Planificación de Clases y Sesiones**
    * **Como** Administrador, **quiero** programar clases grupales y sesiones individuales en un calendario **para** gestionar la oferta del gimnasio.

* **HU-16: Reserva de Clases**
    * **Como** Cliente, **quiero** ver el calendario de clases y reservar mi asistencia **para** planificar mi semana de entrenamiento.