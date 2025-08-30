package logic;

public abstract class Persona {
    protected String id;
    protected String nombre;

    public Persona(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public abstract String mostrarInfo();
}
