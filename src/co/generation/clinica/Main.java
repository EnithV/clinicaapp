package co.generation.clinica;


import co.generation.clinica.service.ClinicaService;
import co.generation.clinica.datos.DatosCSV;
import co.generation.clinica.model.Paciente;
import co.generation.clinica.model.Medico;
import co.generation.clinica.model.Turno;
import co.generation.clinica.model.Especialidad;
import co.generation.clinica.model.EstadoTurno;

// Para manejar fechas
import java.time.LocalDate;
import java.time.LocalDateTime;
// Para manejar listas y leer del teclado
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Crear el servicio que controla el sistema
        ClinicaService servicio = new ClinicaService();
        // Cargar los datos guardados anteriormente (si existen)
        DatosCSV.cargar(servicio);

        // Leer lo que el usuario escriba
        Scanner sc = new Scanner(System.in);
        // Inicializar opcion con un valor que no sea 0 para que entre al bucle
        int opcion = -1;

        // Bucle while: se repite mientras el usuario no elija 0 (Salir)
        while (opcion != 0) {
            // Mostrar el menu con el formato que hizo el compañero
            System.out.println("========================================");
            System.out.println("         CLINICAAPP — MENÚ");
            System.out.println("========================================");
            System.out.println("1. Registrar paciente");
            System.out.println("2. Registrar médico");
            System.out.println("3. Asignar turno");
            System.out.println("4. Listar turnos del día");
            System.out.println("5. Cancelar turno");
            System.out.println("6. Ver turnos por médico");
            System.out.println("7. Ver turnos por paciente");
            System.out.println("8. Cambiar estado de turno");
            System.out.println("9. Listar pacientes");
            System.out.println("10. Listar médicos");
            System.out.println("0. Salir");
            System.out.println("========================================");
            System.out.print("Seleccione una opción: ");

            // Leer la opcion del usuario
            opcion = sc.nextInt();
            sc.nextLine();

            // Hacer lo que el usuario eligió
            switch (opcion) {
                case 1: // Registrar paciente: pide cedula, nombre, apellido, telefono
                    System.out.print("Cédula: ");
                    String cedula = sc.nextLine();
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Apellido: ");
                    String apellido = sc.nextLine();
                    System.out.print("Teléfono: ");
                    String telefono = sc.nextLine();
                    // Crear el paciente y enviarlo al servicio para que lo registre
                    servicio.registrarPaciente(new Paciente(cedula, nombre, apellido, telefono));
                    break;

                case 2: // Registrar medico: pide nombre, apellido y especialidad
                    System.out.print("Nombre: ");
                    String nom = sc.nextLine();
                    System.out.print("Apellido: ");
                    String ape = sc.nextLine();
                    System.out.print("Especialidad (GENERAL, PEDIATRIA, CARDIOLOGIA, URGENCIAS): ");
                    // Convertir a mayusculas y quitar todas las tildes
                    String espInput = sc.nextLine().toUpperCase()
                            .replace("Á", "A")
                            .replace("É", "E")
                            .replace("Í", "I")
                            .replace("Ó", "O")
                            .replace("Ú", "U");
                    Especialidad esp = Especialidad.valueOf(espInput);
                    servicio.registrarMedico(new Medico(nom, ape, esp));
                    break;

                case 3: // Asignar turno: pide cedula del paciente, nombre y apellido del medico, año, mes, dia, hora, minuto
                    System.out.print("Cédula del paciente: ");
                    String cedPac = sc.nextLine();
                    // Buscar si el paciente existe
                    Paciente paciente = servicio.buscarPorCedula(cedPac);
                    if (paciente == null) {
                        System.out.println("Paciente no encontrado.");
                        break;
                    }
                    System.out.print("Nombre del médico: ");
                    String nomMed = sc.nextLine();
                    System.out.print("Apellido del médico: ");
                    String apeMed = sc.nextLine();
                    // Buscar si el medico existe
                    Medico medico = servicio.buscarPorNombreApellido(nomMed, apeMed);
                    if (medico == null) {
                        System.out.println("Médico no encontrado.");
                        break;
                    }
                    // Pedir fecha y hora por separado (como pide el taller)
                    System.out.print("Año: ");
                    int anio = sc.nextInt();
                    System.out.print("Mes: ");
                    int mes = sc.nextInt();
                    System.out.print("Día: ");
                    int dia = sc.nextInt();
                    System.out.print("Hora: ");
                    int hora = sc.nextInt();
                    System.out.print("Minuto: ");
                    int minuto = sc.nextInt();
                    sc.nextLine();
                    // Construir la fecha y hora con lo que escribio el usuario
                    LocalDateTime fechaHora = LocalDateTime.of(anio, mes, dia, hora, minuto);
                    servicio.asignarTurno(new Turno(paciente, medico, fechaHora));
                    break;

                case 4: // Listar turnos del dia: pide fecha y muestra los turnos de ese dia
                    System.out.print("Fecha (YYYY-MM-DD): ");
                    String fechaStr = sc.nextLine();
                    LocalDate fecha = LocalDate.parse(fechaStr);
                    List<Turno> turnosDia = servicio.listarTurnosDelDia(fecha);
                    if (turnosDia.isEmpty()) {
                        System.out.println("No hay turnos para esa fecha.");
                    } else {
                        for (Turno t : turnosDia) {
                            System.out.println(t);
                        }
                    }
                    break;

                case 5: // Cancelar turno: pide el id del turno
                    System.out.print("ID del turno: ");
                    int idTurno = sc.nextInt();
                    sc.nextLine();
                    servicio.cancelarTurno(idTurno);
                    break;

                case 6: // Ver turnos por medico: pide nombre y apellido del medico
                    System.out.print("Nombre del médico: ");
                    String nomMedico = sc.nextLine();
                    System.out.print("Apellido del médico: ");
                    String apeMedico = sc.nextLine();
                    Medico med = servicio.buscarPorNombreApellido(nomMedico, apeMedico);
                    if (med == null) {
                        System.out.println("Médico no encontrado.");
                    } else {
                        List<Turno> turnosMed = servicio.buscarPorMedico(med);
                        if (turnosMed.isEmpty()) {
                            System.out.println("No hay turnos para ese médico.");
                        } else {
                            for (Turno t : turnosMed) {
                                System.out.println(t);
                            }
                        }
                    }
                    break;

                case 7: // Ver turnos por paciente: pide cedula del paciente
                    System.out.print("Cédula del paciente: ");
                    String cedPaciente = sc.nextLine();
                    Paciente pac = servicio.buscarPorCedula(cedPaciente);
                    if (pac == null) {
                        System.out.println("Paciente no encontrado.");
                    } else {
                        List<Turno> turnosPac = servicio.buscarPorPaciente(pac);
                        if (turnosPac.isEmpty()) {
                            System.out.println("No hay turnos para ese paciente.");
                        } else {
                            for (Turno t : turnosPac) {
                                System.out.println(t);
                            }
                        }
                    }
                    break;

                case 8: // Cambiar estado de turno: pide id del turno y el nuevo estado
                    System.out.print("ID del turno: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nuevo estado (PENDIENTE, ATENDIDO, CANCELADO): ");
                    // Convertir a mayusculas y quitar todas las tildes
                    String estadoInput = sc.nextLine().toUpperCase()
                            .replace("Á", "A")
                            .replace("É", "E")
                            .replace("Í", "I")
                            .replace("Ó", "O")
                            .replace("Ú", "U");
                    EstadoTurno nuevoEstado = EstadoTurno.valueOf(estadoInput);
                    servicio.cambiarEstadoTurno(id, nuevoEstado);
                    break;

                case 9: // Listar pacientes: muestra todos los pacientes ordenados
                    servicio.listarPacientes();
                    break;

                case 10: // Listar medicos: muestra todos los medicos ordenados
                    servicio.listarMedicos();
                    break;

                case 0: // Salir: guardar los datos antes de salir
                    DatosCSV.guardar(servicio);
                    System.out.println("Hasta pronto. Datos guardados.");
                    break;

                default: // Si el usuario escribe una opcion que no existe
                    System.out.println("Opción no válida.");
            }
        }
    }
}