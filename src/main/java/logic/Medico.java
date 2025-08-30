package logic;

public class Medico extends Persona {
    private String clave;
    private String especialidad;

    public Medico(String id, String nombre, String especialidad) {
        super(id, nombre);
        this.especialidad = especialidad;
        this.clave = id; // Al crear, la clave inicial es igual al id
    }

    // Getters y Setters
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    // Método para cambiar la clave
    public void cambiarClave(String nuevaClave) {
        this.clave = nuevaClave;
    }

    @Override
    public String mostrarInfo() {
        return "Médico: " + nombre + " ID: " + id + " Especialidad: " + especialidad;
    }
}

