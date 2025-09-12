package sistema.logic.entities;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
abstract class Persona {

    @XmlID
    protected String id;
    protected String nombre;

    public Persona(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}