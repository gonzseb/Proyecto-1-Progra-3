package proyecto.logic.entities;

public class Farmaceuta extends Persona {
    private String clave;

    public Farmaceuta(String id, String nombre) {
        super(id, nombre);
        this.clave = id;
    }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    @Override
    public String toString() {
        return super.toString() + String.format(", Clave: %s", clave);
    }
}