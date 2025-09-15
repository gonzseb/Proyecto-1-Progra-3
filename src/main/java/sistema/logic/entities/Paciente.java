package sistema.logic.entities;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
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
        super("", "");
        this.edad = 0;
        this.telefono = "";
        this.nacimiento = "";
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