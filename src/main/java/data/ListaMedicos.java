package data;

import logic.Medico;

import java.util.ArrayList;
import java.util.List;

public class ListaMedicos {
    private List<Medico> medicos;

    public ListaMedicos() {
        medicos = new ArrayList<>();
    }

    // Agregar médico
    public void agregarMedico(Medico medico) {
        medicos.add(medico);
    }

    // Eliminar médico por ID
    public boolean eliminarMedico(String id) {
        return medicos.removeIf(m -> m.getId().equals(id));
    }

    // Buscar médico por ID
    public Medico buscarPorId(String id) {
        for (Medico m : medicos) {
            if (m.getId().equals(id)) {
                return m;
            }
        }
        return null;
    }

    // Mostrar todos los médicos
    public void mostrarMedicos() {
        if (medicos.isEmpty()) {
            System.out.println("No hay médicos registrados.");
        } else {
            for (Medico m : medicos) {
                System.out.println(m.mostrarInfo());
            }
        }
    }

    // Obtener lista completa
    public List<Medico> getMedicos() {
        return medicos;
    }
}
