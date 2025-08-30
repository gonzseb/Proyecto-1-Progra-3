package data;

import logic.Paciente;

import java.util.ArrayList;
import java.util.List;

public class ListaPacientes {
    private List<Paciente> pacientes;

    public ListaPacientes() {
        pacientes = new ArrayList<>();
    }

    // Agregar paciente
    public void agregarPaciente(Paciente paciente) {
        pacientes.add(paciente);
    }

    // Eliminar paciente por ID
    public boolean eliminarPaciente(String id) {
        return pacientes.removeIf(p -> p.getId().equals(id));
    }

    // Buscar paciente por ID
    public Paciente buscarPorId(String id) {
        for (Paciente p : pacientes) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    // Mostrar todos los pacientes
    public void mostrarPacientes() {
        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
        } else {
            for (Paciente p : pacientes) {
                System.out.println(p.mostrarInfo());
            }
        }
    }

    // Obtener lista completa
    public List<Paciente> getPacientes() {
        return pacientes;
    }
}
