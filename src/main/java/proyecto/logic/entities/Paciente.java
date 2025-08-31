package proyecto.logic.entities;

public class Paciente extends Persona {
    private int edad;
    private String direccion;
    private String telefono;
    private String historialMedico;

    public Paciente(String id, String nombre, int edad, String direccion, String telefono, String historialMedico) {
        super(id, nombre);
        this.edad = edad;
        this.direccion = direccion;
        this.telefono = telefono;
        this.historialMedico = historialMedico;
    }

    // Getters
    public int getEdad() {
        return edad;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getHistorialMedico() {
        return historialMedico;
    }

    // Setters
    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setHistorialMedico(String historialMedico) {
        this.historialMedico = historialMedico;
    }
}