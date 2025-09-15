package sistema.logic.entities;

import sistema.logic.entities.enums.UsuarioRol;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Medico extends Usuario {
    private String especialidad;

    public Medico(String id, String nombre, String clave, String especialidad) {
        super(id, nombre, clave, UsuarioRol.MEDICO);
        this.especialidad = especialidad;
    }

    public Medico(String id, String nombre, String especialidad) {
        super(id, nombre, id, UsuarioRol.MEDICO);
        this.especialidad = especialidad;
    }

    public Medico() {
        super();
    }

    // Getters y Setters
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String mostrarInfo() {
        return "Médico: " + getNombre() + " | ID: " + getId() + " | Especialidad: " + especialidad;
    }
}
