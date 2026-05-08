package co.generation.clinica.model;

import co.generation.clinica.interfaces.Registrable;

public class Medico implements Registrable {
    // Atributos
    private int id;
    private String nombre;
    private String apellido;
    private Especialidad especialidad;

    // Constructor para menu (sin id)
    public Medico(String nombre, String apellido, Especialidad especialidad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
    }

    // Constructor para csv (con id)
    public Medico(int id, String nombre, String apellido, Especialidad especialidad) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        // No puede ser nulo ni vacio
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo ni vacío.");
        }
        this.nombre = nombre.trim();
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        // No puede ser nulo ni vacio
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede ser nulo ni vacío.");
        }
        this.apellido = apellido.trim();
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        // No puede ser nulo
        if (especialidad == null) {
            throw new IllegalArgumentException("La especialidad no puede ser nula.");
        }
        this.especialidad = especialidad;
    }

    // equals: mismo nombre y apellido (sin importar mayusculas)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medico medico = (Medico) o;
        return this.nombre != null && this.apellido != null &&
                this.nombre.equalsIgnoreCase(medico.nombre) &&
                this.apellido.equalsIgnoreCase(medico.apellido);
    }

    // toString: "Dr. Carlos Perez - CARDIOLOGIA"
    @Override
    public String toString() {
        return String.format("Dr. %s %s - %s", nombre, apellido, especialidad);
    }

    // Metodos de Registrable
    @Override
    public String getDatosRegistro() {
        return toString();
    }

    @Override
    public boolean esValido() {
        // Validar que los atributos no sean nulos o vacios
        try {
            if (nombre == null || nombre.trim().isEmpty()) return false;
            if (apellido == null || apellido.trim().isEmpty()) return false;
            if (especialidad == null) return false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}