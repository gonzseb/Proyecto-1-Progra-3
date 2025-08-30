package proyecto.logic.entities;

public class Medicamento {
    private String codigo;
    private String nombre;
    private String presentacion;

    public Medicamento(String codigo, String nombre, String presentacion) {
        if (codigo == null || codigo.isBlank()) throw new IllegalArgumentException("Código no puede estar vacío");
        this.codigo = codigo;
        this.nombre = nombre;
        this.presentacion = presentacion;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getPresentacion() { return presentacion; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPresentacion(String presentacion) { this.presentacion = presentacion; }

    @Override
    public String toString() {
        return String.format("Código: %s, Nombre: %s, Presentación: %s", codigo, nombre, presentacion);
    }
}
