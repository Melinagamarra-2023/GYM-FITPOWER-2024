```mermaid
classDiagram
    direction TD

    %% -------------------
    %% --- Core Users ---
    %% -------------------
    class Usuario {
        <<abstract>>
        +String cuit
        +String dni
        +String firstName
        +String lastName
        +String email
        +String phone
        +String address
        +LocalDate createdAt
        +Boolean isActive
    }

    class Cliente {
        +LocalDate birthDate
        +String goals
        +String initialPhysicalState
    }

    class Entrenador {
        +String specialization
    }

    class Nutricionista {
        +String specialization
    }

    class Gimnasio {
        +String address
        +String email
        +String phone
        +Boolean isActive
    }

    %% -------------------
    %% --- Training ---
    %% -------------------
    class Rutina {
        +String name
        +String description
        +Boolean isTemplate
    }

    class RutinaEjercicio {
        +int sets
        +int reps
        +int restTimeInSeconds
    }

    class Ejercicio {
        +String name
        +String description
        +String muscleGroup
    }

    %% -------------------
    %% --- Nutrition ---
    %% -------------------
    class PlanNutricion {
        +String name
        +String description
        +Float targetDailyCalories
        +Float targetDailyCarbs
        +Float targetDailyProteins
        +Float targetDailyFats
    }

    class Comida {
        +TIPO_COMIDA mealType
        +String description
        +int estimatedCalories
    }

    class TIPO_COMIDA {
        <<enumeration>>
        BREAKFAST
        LUNCH
        SNACK
        DINNER
    }

    %% -------------------
    %% --- Progress & Logging (Major Refinement) ---
    %% -------------------
    class PlanAsignado {
        <<association>>
        +LocalDate assignedAt
        +Boolean isActive
    }

    class SesionEntrenamiento {
        +LocalDateTime dateTime
        +String clientComments
    }

    class EjercicioRealizado {
        +int performedSets
        +int performedReps
        +float weightUsed
    }

    class RegistroComida {
        +LocalDateTime dateTime
        +String clientComments
    }

    class RegistroProgreso {
        +LocalDate date
        +float currentWeight
        +String professionalNotes
    }

    %% -------------------
    %% --- Missing Classes from User Stories ---
    %% -------------------
    class Notificacion {
        +String message
        +LocalDateTime sentAt
        +Boolean isRead
    }

    class ClaseGrupal {
        +String name
        +String description
        +LocalDateTime startDateTime
        +int durationInMinutes
        +int maxCapacity
    }

    class Reserva {
        +LocalDateTime bookingDate
        +String status
    }


    %% ===================
    %% === INHERITANCE ===
    %% ===================
    Usuario <|-- Cliente
    Usuario <|-- Entrenador
    Usuario <|-- Nutricionista


    %% ===================
    %% === RELATIONSHIPS ===
    %% ===================

    %% --- Gym & Users ---
    Gimnasio "1" -- "*" Cliente : "pertenecen a"
    Gimnasio "1" -- "*" Entrenador : "trabajan en"
    Gimnasio "1" -- "*" Nutricionista : "trabajan en"

    %% --- Plan Assignment (Key Change) ---
    Cliente "1" -- "*" PlanAsignado : "tiene planes"
    Rutina .. PlanAsignado
    PlanNutricion .. PlanAsignado
    Entrenador "1" -- "*" Rutina : "crea"
    Nutricionista "1" -- "*" PlanNutricion : "crea"

    %% --- Routine Composition ---
    Rutina "1" -- "1..*" RutinaEjercicio : "compuesta por"
    Ejercicio "1" -- "*" RutinaEjercicio : "es parte de"

    %% --- Nutrition Plan Composition ---
    PlanNutricion "1" -- "1..*" Comida : "contiene"
    Comida "1" -- "1" TIPO_COMIDA : "es de tipo"

    %% --- Logging Progress (Key Change) ---
    Cliente "1" -- "*" SesionEntrenamiento : "registra"
    SesionEntrenamiento "1" -- "1..*" EjercicioRealizado : "incluye"
    Ejercicio "1" -- "*" EjercicioRealizado : "se realiza"

    Cliente "1" -- "*" RegistroComida : "registra"
    Comida "1" -- "*" RegistroComida : "se consume en"

    Cliente "1" -- "*" RegistroProgreso : "tiene"

    %% --- Notifications & Bookings ---
    Cliente "1" -- "*" Notificacion : "recibe"
    Cliente "1" -- "*" Reserva : "realiza"
    ClaseGrupal "1" -- "*" Reserva : "tiene"
    Gimnasio "1" -- "*" ClaseGrupal : "ofrece"