# FITPOWER — Proyecto Final

## 🧩 Pautas para la planificación y dinámica de trabajo

### Entregables requeridos

1. Proyecto final versionado con GIT en GitHub.
2. Resolución de los requerimientos utilizando tecnología **Java**.
3. Aplicación del patrón de arquitectura **Modelo-Vista-Controlador (MVC)**.
4. Diagramas necesarios:
    - Diagrama de clases
    - Diagramas de secuencia
    - Diagramas de estado

---

## 📦 Documentación del proyecto final

### Estructura de entrega

- Repositorio en GitHub con nombre `[nombre-proyecto]`.
- Archivo `README.md` con los nombres de todos los integrantes del grupo (2 a 4 personas).
- Commits por cada requerimiento terminado, con mensajes descriptivos.
    - Ejemplo: `feat: Requerimiento V.0.0.1`
- Script de carga incluido en el proyecto.
- Carpeta `document/` dentro del repositorio con:
    - Documentación del enunciado
    - Requerimientos

---

## 🧪 Modalidad de evaluación

- Presentación grupal con todos los elementos del punto anterior.
- Exposición individual del proyecto con preguntas teóricas.

---

## 🏋️ Enunciado del Proyecto

FITPOWER es el gimnasio líder en América Latina, presente en 15 países. Busca desarrollar una plataforma integrada para seguimiento personalizado de clientes, incluyendo:

- Rutinas de ejercicio
- Planes de nutrición
- Registro de progresos

### Objetivos de la plataforma

- Acceso del cliente a su perfil desde app o sitio web.
- Visualización de planes personalizados.
- Registro diario de actividades.
- Comunicación directa con entrenadores y nutricionistas.
- Retroalimentación en tiempo real.
- Coaching dinámico y ajustes personalizados.

---

## 🛠️ Artefactos del Sistema FITPOWER

### 1. Gestión de Clientes y Rutinas

#### A. Gestión de Clientes
- Perfil con datos personales, objetivos, estado físico inicial y gimnasio asignado.

#### B. Gestión de Rutinas
- Creación y asignación de rutinas personalizadas por entrenadores.
- Detalles: ejercicios, series, repeticiones, descansos.

#### C. Registro de Progreso
- Diario de entrenamiento con ejercicios realizados, repeticiones, peso y observaciones.

---

### 2. Gestión de Planes de Nutrición

#### A. Asignación de Planes
- Creación y asignación de planes personalizados por nutricionistas.

#### B. Seguimiento Nutricional
- Registro diario de alimentos consumidos.

---

### 3. Evaluación y Ajuste de Planes

#### A. Evaluación del Progreso
- Métricas y gráficos para ajustar planes.

#### B. Notificaciones
- Avisos automáticos al cliente sobre ajustes realizados.

---

### 4. Gestión de Información y Comunicación

#### A. Historial del Cliente
- Registro completo de actividades, rutinas, nutrición y evaluaciones.

---

### 5. Monitoreo y Optimización

#### A. Detección de Estancamiento
- Algoritmo que sugiere intervenciones ante falta de progreso.

#### B. Informe de Rendimiento
- Reportes detallados para sesiones de evaluación.

---

### 6. Gestión Operativa del Gimnasio

#### A. Asignación de Personal
- Sistema para asignar entrenadores y nutricionistas según disponibilidad y especialización.

#### B. Planificación de Clases
- Calendario para clases grupales y sesiones individuales.

---

## 🧬 Relaciones del Sistema

- Un gimnasio tiene muchos clientes, entrenadores y nutricionistas.
- Cliente, entrenador y nutricionista pertenecen a un gimnasio.
- Cliente tiene múltiples planes de nutrición, rutinas y progresos.
- Entrenador crea rutinas para clientes.
- Nutricionista crea planes de nutrición para clientes.
- Cliente registra su progreso.
- Cliente recibe notificaciones.
- Rutina contiene múltiples ejercicios.
- Ejercicio pertenece a una rutina.

