package sistema.logic;

import sistema.data.Data;

import sistema.logic.entities.Medico;

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

    public Medico readMedico(Medico medico) throws Exception {
        Medico result = data.getMedicos().stream()
                .filter(i -> i.getId().equals(medico.getId()))
                .findFirst()
                .orElse(null);
        if (result != null) {
            return result;
        } else {
            throw new Exception("Médico no existe");
        }
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
}