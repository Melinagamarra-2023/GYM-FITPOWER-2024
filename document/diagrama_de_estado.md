```mermaid
stateDiagram-v2
direction LR

    [*] --> Creado: Administrador crea el perfil

    Creado --> Activo: Se asignan planes y profesionales
    
    Activo --> Suspendido: Se pausa la membresia por falta de pago o lesion
    Suspendido --> Activo: Se reactiva la membresia

    Activo --> DadoDeBaja: El cliente cancela su suscripcion
    Suspendido --> DadoDeBaja: El cliente no renueva tras la suspension

    DadoDeBaja --> [*]: Fin del ciclo de vida