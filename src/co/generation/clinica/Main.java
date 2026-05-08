package co.generation.clinica;

import java.util.Scanner;

import co.generation.clinica.model.Paciente;

import co.generation.clinica.service.ClinicaService;

public class Main {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        ClinicaService servicio = new ClinicaService();

        System.out.println("ClínicaApp");
        System.out.println("1. Registrar paciente");
        System.out.println("2. Registrar médico");
        System.out.println("3. Listar pacientes");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");

        int opcion = entrada.nextInt();
        entrada.nextLine();

        if (opcion == 1) {
            servicio.registrarPaciente();
        }

        if (opcion == 2) {
            servicio.registrarMedico();
        }

        if (opcion == 3) {
            servicio.listarPacientes();
        }

        if (opcion == 4) {
            System.out.println("Salir");
        }
    }
}