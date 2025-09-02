package sistema.logic.entities;

public class Paciente extends Persona {
    private int edad;
    private String telefono;
    private String nacimiento;

    public Paciente(String id, String nombre, int edad, String telefono, String nacimiento) {
        super(id, nombre);
        this.edad = edad;
        this.telefono = telefono;
        this.nacimiento = nacimiento;
    }

    public Paciente() {
        super("", "");       // Llama al constructor de la clase padre con valores vacíos
        this.edad = 0;       // Edad inicial 0
        this.telefono = "";  // Teléfono vacío
        this.nacimiento = ""; // Fecha de nacimiento vacía
    }

    // Getters
    public int getEdad() {
        return edad;
    }

    public String getNacimiento() {
        return nacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    // Setters
    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}