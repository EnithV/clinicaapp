package co.generation.clinica.service;  // paquete donde está la clase

import co.generation.clinica.interfaces.Consultable;
import co.generation.clinica.model.*;
import java.time.LocalDate;                           // para manejar fechas solo día
import java.time.LocalDateTime;                       // para manejar fechas con hora
import java.util.ArrayList;
import java.util.Comparator;                          // para ordenar listas
import java.util.List;
// Declara tres listas privadas que DatosCSV llena al arrancar y que se mantienen sincronizadas durante la sesión.
//  Los métodos de Consultable operan exclusivamente sobre esas listas — no leen archivos.
public class ClinicaService implements Consultable {
    // Listas Privadas
    private List<Paciente> pacientes; // Guarda los pacientes
    private List<Medico> medicos;   // Guarda los médicos
    private List<Turno> turnos; // Guarda los turnos

    // Constructor vacio para inicializar las listas
    // Las tres listas deben ser accesibles para DatosCSV.
    public ClinicaService() {
        this.pacientes = new ArrayList<>();
        this.medicos = new ArrayList<>();
        this.turnos = new ArrayList<>();
    }

    // Getters: Declara getters para cada una: getPacientes(), getMedicos(), getTurnos().
    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

    // Métodos de Paciente
    public void registrarPaciente(Paciente p) {
        //1. Llama a p.esValido() — si retorna false, imprime error y sale.
        if(!p.esValido()) {
            System.out.println("Error: entrada inválida");
            return;
        }
        // 2. Verifica que no exista otro paciente con la misma cédula (usa contains()).
        if(pacientes.contains(p)) {
            System.out.println("Error: Ya existe un paciente con esa cédula");
            return;
        }
        // 3. Asigna el id: toma el máximo id existente en la lista y suma 1 (o usa 1 si la lista está vacía).
        int maxId = 0;
        for(Paciente paciente : pacientes) { // Recorre la lista de pacientes guardados
            if(paciente.getId() > maxId) { // Si encuentra un id más grande que el actual
                maxId = paciente.getId(); // actualiza maxId con ése número más grande
            }
        }
        p.setId(maxId + 1); // Al nuevo paciente le asigna el maxId+1

        // 4. Agrega p a la lista.
        pacientes.add(p);

        // 5. Imprime mensaje de éxito con los datos del paciente.
        System.out.println("Paciente registrado con éxito" + p.getDatosRegistro());
    }
    // Method buscarPorCedula(String cedula)
    public Paciente buscarPorCedula(String cedula) {
        // Recorre la lista con un for. Retorna el Paciente cuya cédula coincida exactamente.
        for(Paciente p : pacientes) {
            if(p.getCedula().equals(cedula)) {
                return p;
            }
        }
        // Retorna null si no encuentra ninguno. No imprime nada.

        return null;
    }
    // Method listarPacientes()
    public void listarPacientes() {
        // Si la lista está vacía, imprime un mensaje informativo
        if(pacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados en la lista");
            return;
        }
        List<Paciente> copia = new ArrayList<>(pacientes);
        // Ordena una copia de la lista por apellido y luego por nombre usando Comparator.
        copia.sort(Comparator.comparing(Paciente::getApellido).thenComparing(Paciente::getNombre));
        // Imprime cada paciente con toString().
        for(Paciente p : copia) {
            System.out.println(p.toString());
        }
    }
    // Métodos de Médico
    public void registrarMedico(Medico m) {
        // 1. Llama a m.esValido().
        if (!m.esValido()) {
            System.out.println("Error: entrada inválida");
            return;
        }
        // 2. Verifica duplicado con contains() — invoca el equals() de Medico.
        if (medicos.contains(m)) {
            System.out.println("Error: Ya existe un médico con ese nombre");
            return;
        }
        // 3. Asigna el id igual que en registrarPaciente.
        int maxId = 0;
        for(Medico medico : medicos) {
            if(medico.getId() > maxId) {
                maxId = medico.getId();
            }
        }
        m.setId(maxId + 1);
        // 4. Agrega a la lista.
        medicos.add(m);
        // 5. Imprime éxito.
        System.out.println("Médico registrado con éxito" + m.getDatosRegistro());
    }
    public Medico buscarPorNombreApellido (String nombre, String apellido) {
        // Recorre la lista. Retorna el Medico cuyo nombre y apellido coincidan sin distinguir mayúsculas (equalsIgnoreCase).
        for(Medico m : medicos) {
            if(m.getNombre().equalsIgnoreCase(nombre) && m.getApellido().equalsIgnoreCase(apellido)) {
                return m;
            }
        }
        // Retorna null si no existe.
        return null;
    }
    // listarMedicos()
    public void listarMedicos() {
        // Si la lista está vacía, imprime un mensaje informativo.
        if(medicos.isEmpty()) {
            System.out.println("No hay medicos registrados en la lista");
            return;
        }
        // Ordena una copia por especialidad (orden natural del enum) y luego por apellido.
        List<Medico> copia = new ArrayList<>(medicos);
        copia.sort(Comparator.comparing(Medico::getEspecialidad).thenComparing(Medico::getApellido));
        //Imprime cada médico con toString().
        for(Medico m : copia) {
            System.out.println(m.toString());
        }
    }
    // Métodos de Turno
    public void asignarTurno(Turno t) {
        // 1. Verifica que el paciente del turno exista en la lista (busca por cédula).
        Paciente pacienteExistente = buscarPorCedula(t.getPaciente().getCedula());
        // 2. Verifica que el médico del turno exista (busca por nombre y apellido).
        Medico medicoExistente = buscarPorNombreApellido(t.getMedico().getNombre(), t.getMedico().getApellido());
        if (medicoExistente == null) {
            System.out.println("Error: medico no está registrado");
            return;
        }
        // 3. Verifica que no exista ya un turno con el mismo médico y fechaHora (usa contains()).
        boolean existeConflicto = false;
        for (Turno turnoExistente : turnos) {
            if (turnoExistente.getMedico().equals(medicoExistente) &&
                    turnoExistente.getFechaHora().equals(t.getFechaHora())) {
                existeConflicto = true;
                break;
            }
        }
        if (existeConflicto) {
            System.out.println("Error: El médico ya tiene un turno asignado en ese espacio");
            return;
        }

        // 4. Asigna el id. Agrega a la lista. Imprime éxito con toString().
        int maxId = 0;
        for(Turno turno : turnos) {
            if (turno.getId() > maxId) {
                maxId = turno.getId();
            }
        }
        t.setId(maxId + 1);
        turnos.add(t);
        System.out.println("Turno asignado con éxito: " + t.toString());
    }
    public void cancelarTurno(int idTurno) {
        // Busca el turno por id
        Turno turnoEncontrado = null;
        for (Turno t : turnos) {
            if (t.getId() == idTurno) {
                turnoEncontrado = t;
                break;
            }
        }
        // Si no existe: imprime "Turno no encontrado."
        if (turnoEncontrado == null) {
            System.out.println("Turno no encontrado.");
            return;
        }
        // Si existe pero su estado es ATENDIDO o CANCELADO: imprime que no se puede cancelar
        if (turnoEncontrado.getEstado() == EstadoTurno.ATENDIDO || turnoEncontrado.getEstado() == EstadoTurno.CANCELADO) {
            System.out.println("No se puede cancelar el turno porque ya está " + turnoEncontrado.getEstado().toString().toLowerCase() + ".");
            return;
        }
        // Si está PENDIENTE: cambia el estado a CANCELADO
        turnoEncontrado.setEstado(EstadoTurno.CANCELADO);
        // Imprime confirmación
        System.out.println("Turno cancelado con éxito.");
    }
    // 8. Cambiar estado de turno
    public void cambiarEstadoTurno(int idTurno, EstadoTurno nuevo) {
        // Busca el turno por id
        Turno turnoEncontrado = null;
        for (Turno t : turnos) {
            if (t.getId() == idTurno) {
                turnoEncontrado = t;
                break;
            }
        }
        // Si no existe: imprime "Turno no encontrado."
        if (turnoEncontrado == null) {
            System.out.println("Turno no encontrado.");
            return;
        }
        // Si existe: cambia el estado con setEstado()
        turnoEncontrado.setEstado(nuevo);
        // Imprime confirmación con el nuevo estado
        System.out.println("Estado del turno actualizado a: " + nuevo);
    }

    @Override
    public List<Turno> listarTurnosDelDia(LocalDate fecha) {
        // Lista vacía donde vamos a guardar los turnos del día
        List<Turno> resultado = new ArrayList<>();
        // Recorremos cada turno de la lista de turnos
        for (Turno t : turnos) {
            // Si la fecha del turno es igual a la fecha que buscamos
            if (t.getFechaHora().toLocalDate().equals(fecha)) {
                // Agregamos ese turno a la lista resultado
                resultado.add(t);
            }
        }
        // Ordenamos los turnos por hora de menor a mayor
        resultado.sort(Comparator.comparing(Turno::getFechaHora));
        // Devolvemos la lista de turnos del día
        return resultado;
    }

    @Override
    public List<Turno> buscarPorMedico(Medico medico) {
        // Lista vacía donde vamos a guardar los turnos del médico
        List<Turno> resultado = new ArrayList<>();
        // Recorremos cada turno de la lista de turnos
        for (Turno t : turnos) {
            // Si el médico del turno es igual al médico que buscamos (usa equals de Medico)
            if (t.getMedico().equals(medico)) {
                // Agregamos ese turno a la lista resultado
                resultado.add(t);
            }
        }
        // Devolvemos la lista de turnos del médico
        return resultado;
    }

    @Override
    public List<Turno> buscarPorPaciente(Paciente paciente) {
        // Lista vacía donde vamos a guardar los turnos del paciente
        List<Turno> resultado = new ArrayList<>();
        // Recorremos cada turno de la lista de turnos
        for (Turno t : turnos) {
            // Si el paciente del turno es igual al paciente que buscamos (usa equals de Paciente)
            if (t.getPaciente().equals(paciente)) {
                // Agregamos ese turno a la lista resultado
                resultado.add(t);
            }
        }
        // Devolvemos la lista de turnos del paciente
        return resultado;
    }
}
