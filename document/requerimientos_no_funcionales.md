# Requerimientos No Funcionales - FITPOWER

Este documento describe los Requerimientos No Funcionales (RNF) para el sistema FITPOWER. Estos requerimientos definen los estándares de calidad, rendimiento y operación de la plataforma, asegurando una experiencia de usuario robusta y confiable.

---

### 1. Rendimiento (Performance)

* **RNF-01: Tiempo de Respuesta de la Interfaz:** La aplicación debe ser ágil. Las acciones comunes del usuario (navegar entre pantallas, abrir un plan) no deben tardar más de **2 segundos** en cargar.
* **RNF-02: Tiempo de Inicio de Sesión:** El proceso de autenticación de un usuario no debe superar **1 segundo**.
* **RNF-03: Generación de Informes:** La generación de informes de progreso (HU-18) debe completarse en menos de **5 segundos**, incluso con un historial de datos de un año completo para un cliente.

### 2. Seguridad (Security)

* **RNF-04: Autenticación:** Todos los accesos a la plataforma, sin excepción, deben requerir autenticación previa. No existirá ninguna funcionalidad accesible para usuarios anónimos.
* **RNF-05: Autorización Basada en Roles:** El acceso a la información estará estrictamente limitado por el rol del usuario:
    * Un `Cliente` solo puede ver y modificar su propia información.
    * Un `Entrenador` solo puede acceder a la información de los clientes que le han sido asignados.
    * Un `Nutricionista` sigue la misma regla que el entrenador para los planes de nutrición.
* **RNF-06: Protección de Datos Sensibles:** La información personal y de salud de los clientes es confidencial. Las contraseñas de los usuarios deben ser almacenadas en la base de datos utilizando un algoritmo de hashing robusto (ej: bcrypt).

### 3. Usabilidad (Usability)

* **RNF-07: Interfaz Intuitiva:** El diseño de la plataforma debe ser claro y autoexplicativo. Un usuario nuevo (`Cliente`, `Entrenador`) debe ser capaz de realizar sus tareas principales (ej: registrar un entrenamiento) sin necesidad de un manual o capacitación.
* **RNF-08: Diseño Responsivo (Responsive):** La aplicación web debe ser completamente funcional y visualmente agradable tanto en navegadores de escritorio como en dispositivos móviles (tablets y smartphones).

### 4. Disponibilidad (Availability)

* **RNF-09: Tiempo de Actividad (Uptime):** El sistema debe estar disponible para los usuarios el **99.5%** del tiempo, excluyendo ventanas de mantenimiento planificadas.
* **RNF-10: Mantenimiento Programado:** Cualquier mantenimiento que requiera dar de baja el servicio debe ser comunicado a los usuarios con al menos 24 horas de antelación y programado para horarios de bajo tráfico (ej: 2:00 AM - 4:00 AM).

### 5. Mantenibilidad (Maintainability)

* **RNF-11: Adhesión al Patrón MVC:** El código fuente debe seguir estrictamente el patrón de arquitectura Modelo-Vista-Controlador, como se especifica en el enunciado del proyecto.
* **RNF-12: Versionado de Código:** Todo el código fuente debe estar gestionado a través de Git, utilizando mensajes de commit claros y descriptivos que indiquen la funcionalidad o corrección implementada, como se solicita en el enunciado.