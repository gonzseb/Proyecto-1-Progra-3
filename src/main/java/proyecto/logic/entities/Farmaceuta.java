package proyecto.logic.entities;

public class Farmaceuta extends Persona {
    private String clave;

    public Farmaceuta(String id, String nombre) {
        super(id, nombre);
        this.clave = id; // Al crear, la clave inicial es igual al id
    }

    // Getters y Setters
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    // Método para cambiar la clave
    public void cambiarClave(String nuevaClave) {
        this.clave = nuevaClave;
    }

    public String mostrarInfo() {
        return "Farmaceuta: " + getNombre() + " ID: " + getId();
    }
}