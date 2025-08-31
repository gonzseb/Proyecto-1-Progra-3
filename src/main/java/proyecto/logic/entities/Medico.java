package proyecto.logic.entities;

public class Medico extends Persona {
    private String clave;
    private String especialidad;

    public Medico(String id, String nombre, String especialidad) {
        super(id, nombre);
        this.clave = id;
        this.especialidad = especialidad;
    }

    public Medico() {
        super("", "");
        this.clave = "";
        this.especialidad = "";
    }

    public String getClave() { return clave; }

    public String getEspecialidad() { return especialidad; }

    public void setClave(String clave) { this.clave = clave; }

    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
}