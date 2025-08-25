```mermaid
classDiagram
    direction LR

    class Usuario {
        <<abstract>>
        +String cuit
        +String name
        +String lastname
        +String email
        +String phone
        +LocalDate createdAt
        +Boolean enabled
    }

    class Client {
        +LocalDate birthdate
        +String goals
        +Float desiredWeight
    }

    class Trainer {
        +String specialization
    }

    class Nutritionist {
        +String specialization
    }

    class Gym {
        +String address
        +String domain
        +String email
        +String phone
        +Boolean enabled
    }

    class Routine {
        +String name
        +String description
        +LocalDate createdAt
    }

    class RoutineExercise {
        +int reps
        +int sets
        +int restInMinutes
    }

    class Exercise {
        +String name
        +String description
        +String muscleGroup
    }

    class NutritionPlan {
        +LocalDate createdAt
        +Float dailyCalories
        +Float dailyCarbohydrates
        +Float dailyProteins
        +Float dailyFats
    }

    class TrainingDiary {
        +LocalDateTime createdAt
        +String commentary
    }

    class NutritionDiary {
        +LocalDate updatedAt
        +Float actualWeight
        +String commentary
    }

    class Meal {
        +MEALS mealType
        +String description
    }

    class MEALS {
        <<enumeration>>
        BREAKFAST
        LUNCH
        SNACKS
        DINNER
    }

    %% Herencia
    Usuario <|-- Client
    Usuario <|-- Trainer
    Usuario <|-- Nutritionist

    %% Relaciones
    Gym "1" -- "*" Client : "tiene"
    Gym "1" -- "*" Trainer : "tiene"
    Gym "1" -- "*" Nutritionist : "tiene"

    Client "1" -- "*" Routine : "tiene"
    Trainer "1" -- "*" Routine : "crea"

    Client "1" -- "*" NutritionPlan : "tiene"
    Nutritionist "1" -- "*" NutritionPlan : "crea"

    Client "1" -- "*" TrainingDiary : "registra"
    Client "1" -- "*" NutritionDiary : "registra"

    Routine "1" -- "1..*" RoutineExercise : "compuesta por"
    Exercise "1" -- "*" RoutineExercise : "incluido en"

    NutritionDiary "1" -- "*" Meal : "contiene"
    Meal "1" -- "1" MEALS : "es de tipo"