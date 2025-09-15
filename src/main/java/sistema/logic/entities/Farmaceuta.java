package sistema.logic.entities;

import sistema.logic.entities.enums.UsuarioRol;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Farmaceuta extends Usuario {

    public Farmaceuta(String id, String nombre, String clave) {
        super(id, nombre, clave, UsuarioRol.FARMACEUTA);
    }

    public Farmaceuta(String id, String nombre) {
        super(id, nombre, id, UsuarioRol.FARMACEUTA);
    }

    public Farmaceuta() {
        super();
    }

    public String mostrarInfo() {
        return "Farmaceuta: " + getNombre() + " | ID: " + getId();
    }
}
