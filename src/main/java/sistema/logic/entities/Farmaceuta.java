package sistema.logic.entities;

import sistema.logic.entities.enums.UsuarioRol;

public class Farmaceuta extends Usuario {

    // Constructor principal
    public Farmaceuta(String id, String nombre, String clave) {
        super(id, nombre, clave, UsuarioRol.FARMACEUTA);
    }

    // Constructor de conveniencia (clave = id)
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
