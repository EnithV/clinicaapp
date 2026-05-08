package co.generation.clinica.model;  // Paquete donde está la clase

public enum EstadoTurno {  // Enum que define los posibles estados de un turno
    PENDIENTE,   // Estado inicial cuando se crea el turno
    ATENDIDO,    // Estado cuando el turno ya fue atendido
    CANCELADO    // Estado cuando el turno fue cancelado
}