package co.generation.clinica.model;

import co.generation.clinica.interfaces.Registrable;

public class Paciente implements Registrable {
    private int id;
    private String cedula;
    private String nombre;
    private String apellido;
    private String telefono;

    // Constructor sin id (para menú)
    public Paciente(String cedula, String nombre, String apellido, String telefono) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }

    // Constructor con id (para CSV)
    public Paciente(int id, String cedula, String nombre, String apellido, String telefono) {
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            throw new IllegalArgumentException("La cédula no puede ser nula ni vacía.");
        }
        this.cedula = cedula.trim();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo ni vacío.");
        }
        this.nombre = nombre.trim();
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede ser nulo ni vacío.");
        }
        this.apellido = apellido.trim();
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono == null || !telefono.matches("^[0-9]{7,10}$")) {
            throw new IllegalArgumentException("El teléfono debe tener entre 7 y 10 dígitos.");
        }
        this.telefono = telefono;
    }

    // equals: dos pacientes son iguales si tienen la misma cédula
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paciente paciente = (Paciente) o;
        return this.cedula != null && this.cedula.equals(paciente.cedula);
    }

    // toString: "María García - 1020304050 - 3001234567"
    @Override
    public String toString() {
        return String.format("%s %s - %s - %s", nombre, apellido, cedula, telefono);
    }

    // Métodos de Registrable
    @Override
    public String getDatosRegistro() {
        return toString();
    }

    @Override
    public boolean esValido() {
        return true;
    }
}