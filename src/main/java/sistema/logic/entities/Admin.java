package sistema.logic.entities;

import sistema.logic.entities.enums.UsuarioRol;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Admin extends Usuario {

    public Admin(String id, String clave) {
        super(id, clave, UsuarioRol.ADMIN);
    }

    public Admin(String id) {
        super(id, id, UsuarioRol.ADMIN);
    }

    public Admin() {
        super();
    }

    public void resetClave(Usuario u, String nuevaClave) {
        u.setClave(nuevaClave);
    }
}
