package co.generation.clinica.interfaces;

import co.generation.clinica.model.Medico;
import co.generation.clinica.model.Paciente;
import co.generation.clinica.model.Turno;

import java.time.LocalDate;
import java.util.List;

public interface Consultable {

    // Retorna todos los turnos de un día específico, ordenados por hora
    List<Turno> listarTurnosDelDia(LocalDate fecha);

    // Retorna todos los turnos asignados a un médico
    List<Turno> buscarPorMedico(Medico medico);

    // Retorna todos los turnos de un paciente
    List<Turno> buscarPorPaciente(Paciente paciente);
}