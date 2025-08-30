package proyecto.data.entities;

public class Paciente extends Persona {
    private String fechaNacimiento;
    private String telefono;

    public Paciente(String id, String nombre, String fechaNacimiento, String telefono) {
        super(id, nombre);
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
    }

    public String getFechaNacimiento() { return fechaNacimiento; }
    public String getTelefono() { return telefono; }

    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    @Override
    public String toString() {
        return super.toString() + String.format(", Fecha de Nacimiento: %s, Teléfono: %s", fechaNacimiento, telefono);
    }
}