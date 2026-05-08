package co.generation.clinica.interfaces;

public interface Registrable {

    // Retorna los datos del objeto listos para mostrar en consola
    String getDatosRegistro();

    // Retorna true si todos los atributos son válidos
    boolean esValido();
}