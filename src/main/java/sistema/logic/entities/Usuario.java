package sistema.logic.entities;

import sistema.logic.entities.enums.UsuarioRol;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Usuario extends Persona {

    private String clave;     // contraseña en texto plano
    private UsuarioRol rol;   // ADMIN, MEDICO, FARMACEUTA

    // Constructor principal
    public Usuario(String id, String nombre, String clave, UsuarioRol rol) {
        super(id, nombre); // id y nombre vienen de Persona
        this.clave = clave;
        this.rol = rol;
    }

    public Usuario() {
        super("", "");    // llama al constructor de Persona
        this.clave = "";
        this.rol = null;
    }

    // Constructor de conveniencia (clave inicial = id)
    public Usuario(String id, String nombre, UsuarioRol rol) {
        this(id, nombre, id, rol); // clave = id
    }

    // Getters y setters
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public UsuarioRol getRol() { return rol; }
    public void setRol(UsuarioRol rol) { this.rol = rol; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return getId() != null && getId().equals(usuario.getId());
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + getId() + '\'' +
                ", nombre='" + getNombre() + '\'' +
                ", rol=" + rol +
                '}';
    }

    public String getRolString() {
        if (rol == null) return "";
        switch (rol) {
            case ADMIN: return "ADM";
            case MEDICO: return "MED";
            case FARMACEUTA: return "FAR";
            default: return "";
        }
    }

}
