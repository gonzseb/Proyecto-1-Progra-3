package sistema.logic.entities;

import sistema.logic.entities.enums.UsuarioRol;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Medico.class, Farmaceuta.class})
public class Usuario extends Persona {

    private String clave;
    private UsuarioRol rol;   // ADMIN, MEDICO, FARMACEUTA

    public Usuario(String id, String nombre, String clave, UsuarioRol rol) {
        super(id, nombre);
        this.clave = clave;
        this.rol = rol;
    }

    public Usuario() {
        super("", "");
        this.clave = "";
        this.rol = null;
    }

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
