package co.generation.clinica.model;

import java.time.LocalDateTime;

public class Turno {
    // Atributos
    private int id;
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime fechaHora;
    private EstadoTurno estado;

    // Constructor para menu (sin id) - el estado arranca como PENDIENTE
    public Turno(Paciente paciente, Medico medico, LocalDateTime fechaHora) {
        this.paciente = paciente;
        this.medico = medico;
        this.fechaHora = fechaHora;
        this.estado = EstadoTurno.PENDIENTE;
    }

    // Constructor para csv (con id)
    public Turno(int id, Paciente paciente, Medico medico, LocalDateTime fechaHora, EstadoTurno estado) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("El paciente no puede ser nulo");
        }
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        if (medico == null) {
            throw new IllegalArgumentException("El medico no puede ser nulo");
        }
        this.medico = medico;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        if (fechaHora == null) {
            throw new IllegalArgumentException("La fecha y hora no puede ser nula");
        }
        this.fechaHora = fechaHora;
    }

    public EstadoTurno getEstado() {
        return estado;
    }

    public void setEstado(EstadoTurno estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo");
        }
        this.estado = estado;
    }

    // equals: mismo medico y misma fechaHora
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Turno turno = (Turno) o;
        return this.medico != null && this.fechaHora != null &&
                this.medico.equals(turno.medico) &&
                this.fechaHora.equals(turno.fechaHora);
    }

    // toString: "[PENDIENTE] Maria Garcia — Dr. Carlos Perez (CARDIOLOGIA) — 2026-06-10T09:30"
    @Override
    public String toString() {
        return String.format("[%s] %s %s — %s — %s",
                estado,
                paciente.getNombre(),
                paciente.getApellido(),
                medico.toString(),
                fechaHora.toString());
    }
}