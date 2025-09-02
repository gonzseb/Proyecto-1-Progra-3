package sistema.logic;

import sistema.AppPacientes;
import sistema.data.Data;

import sistema.logic.entities.Farmaceuta;
import sistema.logic.entities.Medicamento;
import sistema.logic.entities.Medico;
import sistema.logic.entities.Paciente;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Service {
    private static Service theInstance;

    public static Service instance() {
        if (theInstance == null) theInstance = new Service();
        return theInstance;
    }

    private Data data;

    private Service() {
        data = new Data();
    }

    // -- Médicos --
    public void createMedico(Medico medico) throws Exception {
        boolean exists = data.getMedicos().stream()
                .anyMatch(i -> i.getId().equals(medico.getId()));
        if (exists) {
            throw new Exception("Médico ya existe");
        }
        data.getMedicos().add(medico);
    }

    // Read is missing...

    public void updateMedico(Medico medico) throws Exception {
        Medico existente = data.getMedicos().stream()
                .filter(m -> m.getId().equals(medico.getId()))
                .findFirst()
                .orElse(null);

        if (existente == null) {
            throw new Exception("Médico no existe");
        }

        // Actualizamos los datos
        existente.setNombre(medico.getNombre());
        existente.setEspecialidad(medico.getEspecialidad());
    }

    public void deleteMedico(String id) throws Exception {
        Medico medico = data.getMedicos().stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (medico == null) {
            throw new Exception("Médico no existe");
        }

        data.getMedicos().remove(medico);
    }

    public List<Medico> findAllMedicos() {
        return data.getMedicos();
    }


    public List<Medico> findSomeMedicos(String nombreParcial) {
        return data.getMedicos().stream()
                .filter(m -> m.getNombre().toLowerCase().contains(nombreParcial.toLowerCase()))
                .sorted(Comparator.comparing(Medico::getNombre))
                .collect(Collectors.toList());
    }

    // -- Otras listas --

    // Lista Farmaceutas:
    public void createFarmaceuta(Farmaceuta farmaceuta) throws Exception {
        boolean exists = data.getFarmaceutas().stream()
                .anyMatch(i -> i.getId().equals(farmaceuta.getId()));
        if (exists) {
            throw new Exception("Médico ya existe");
        }
        data.getFarmaceutas().add(farmaceuta);
    }

    public List<Farmaceuta> findAllFarmaceutas() {
        return data.getFarmaceutas();
    }


    public void deleteFarmaceuta(String id) throws Exception {
        Farmaceuta farmaceuta = data.getFarmaceutas().stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (farmaceuta == null) {
            throw new Exception("Farmauceta no existe");
        }

        data.getFarmaceutas().remove(farmaceuta);
    }

    public void updateFarmaceuta(Farmaceuta farmaceuta) throws Exception {
        Farmaceuta existente = data.getFarmaceutas().stream()
                .filter(f -> f.getId().equals(farmaceuta.getId()))
                .findFirst()
                .orElse(null);

        if (existente == null) {
            throw new Exception("Farmaceuta no existe");
        }

        // Actualizamos los datos
        existente.setNombre(farmaceuta.getNombre());
    }

    public List<Farmaceuta> findSomeFarmaceutas(String nombreParcial) {
        return data.getFarmaceutas().stream()
                .filter(f -> f.getNombre().toLowerCase().contains(nombreParcial.toLowerCase()))
                .sorted(Comparator.comparing(Farmaceuta::getNombre))
                .collect(Collectors.toList());
    }

    // Lista Pacientes

    public void createPaciente(Paciente paciente) throws Exception {
        boolean exists = data.getPacientes().stream()
                .anyMatch(i -> i.getId().equals(paciente.getId()));
        if (exists) {
            throw new Exception("Paciente ya existe");
        }
        data.getPacientes().add(paciente);
    }

    public List<Paciente> findAllPacientes() {
        return data.getPacientes();
    }


    public void deletePaciente(String id) throws Exception {
        Paciente paciente = data.getPacientes().stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (paciente == null) {
            throw new Exception("Paciente no existe");
        }

        data.getPacientes().remove(paciente);
    }

    public void updatePaciente(Paciente paciente) throws Exception {
        Paciente existente = data.getPacientes().stream()
                .filter(f -> f.getId().equals(paciente.getId()))
                .findFirst()
                .orElse(null);

        if (existente == null) {
            throw new Exception("Paciente no existe");
        }

        // Actualizamos los datos
        existente.setNombre(paciente.getNombre());
    }

    public List<Paciente> findSomePacientes(String nombreParcial) {
        return data.getPacientes().stream()
                .filter(f -> f.getNombre().toLowerCase().contains(nombreParcial.toLowerCase()))
                .sorted(Comparator.comparing(Paciente::getNombre))
                .collect(Collectors.toList());
    }


}