package sistema.logic.entities;

import sistema.logic.entities.enums.UsuarioRol;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Medico extends Usuario {
    private String especialidad;

    // Constructor principal
    public Medico(String id, String nombre, String clave, String especialidad) {
        super(id, nombre, clave, UsuarioRol.MEDICO);
        this.especialidad = especialidad;
    }

    // Constructor de conveniencia (clave = id)
    public Medico(String id, String nombre, String especialidad) {
        super(id, nombre, id, UsuarioRol.MEDICO);
        this.especialidad = especialidad;
    }

    public Medico() {
        super(); // llama al constructor vacío de Usuario
    }

    // Getters y Setters
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String mostrarInfo() {
        return "Médico: " + getNombre() + " | ID: " + getId() + " | Especialidad: " + especialidad;
    }
}
