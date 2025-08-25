```mermaid
stateDiagram-v2
direction LR

    [*] --> Creado: Administrador crea el perfil

    Creado --> Activo: Se asignan planes y profesionales
    note on link
        El cliente puede empezar a
        registrar sus actividades.
    end note

    Activo --> Suspendido: Se pausa la membresía (ej: por falta de pago, lesión)
    Suspendido --> Activo: Se reactiva la membresía

    Activo --> DadoDeBaja: El cliente cancela su suscripción
    Suspendido --> DadoDeBaja: El cliente no renueva tras la suspensión

    DadoDeBaja --> [*]: Fin del ciclo de vida