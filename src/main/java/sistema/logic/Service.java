package sistema.logic;

import sistema.data.Data;

import sistema.data.XmlPersister;
import sistema.logic.entities.*;
import sistema.logic.entities.enums.EstadoReceta;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Service {
    private static Service theInstance;

    public static Service instance() {
        if (theInstance == null) theInstance = new Service();
        return theInstance;
    }

    private Data data;

    private Service() {
        try {
            data = XmlPersister.instance().load();
        } catch (Exception e) {
            data = new Data();
        }
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
// Lista medicamentos

    public void createMedicamento(Medicamento medicamento) throws Exception {
        boolean exists = data.getMedicamentos().stream()
                .anyMatch(i -> i.getCodigo().equals(medicamento.getCodigo()));
        if (exists) {
            throw new Exception("Medicamento ya existe");
        }
        data.getMedicamentos().add(medicamento);
    }

    public List<Medicamento> findAllMedicamentos() {
        return data.getMedicamentos();
    }

    public void deleteMedicamento(String codigo) throws Exception {
        Medicamento medicamento = data.getMedicamentos().stream()
                .filter(m -> m.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);

        if (medicamento == null) {
            throw new Exception("Medicamento no existe");
        }

        data.getMedicamentos().remove(medicamento);
    }

    public void updateMedicamento(Medicamento medicamento) throws Exception {
        Medicamento existente = data.getMedicamentos().stream()
                .filter(f -> f.getCodigo().equals(medicamento.getCodigo()))
                .findFirst()
                .orElse(null);

        if (existente == null) {
            throw new Exception("Medicamento no existe");
        }

        // Actualizamos los datos
        existente.setNombre(medicamento.getNombre());
    }

    public List<Medicamento> findSomeMedicamentos(String nombreParcial) {
        return data.getMedicamentos().stream()
                .filter(f -> f.getNombre().toLowerCase().contains(nombreParcial.toLowerCase()))
                .sorted(Comparator.comparing(Medicamento::getNombre))
                .collect(Collectors.toList());
    }

    public Usuario read(Usuario u) throws Exception {
        // Buscar en médicos
        for (Usuario user : data.getMedicos()) {
            if (user.getId().equals(u.getId())) {
                return user; // user ya tiene rol MEDICO
            }
        }

        // Buscar en farmaceutas
        for (Usuario user : data.getFarmaceutas()) {
            if (user.getId().equals(u.getId())) {
                return user; // user ya tiene rol FARMACEUTA
            }
        }

        // Buscar en admins
        for (Usuario user : data.getAdmins()) {
            if (user.getId().equals(u.getId())) {
                return user; // user ya tiene rol ADMIN
            }
        }

        throw new Exception("Usuario no encontrado: " + u.getId());
    }

    // Recetas:

    public void createReceta(Receta receta) throws Exception {
        boolean exists = data.getRecetas().stream()
                .anyMatch(r -> r.getId().equals(receta.getId()));
        if (exists) {
            throw new Exception("Receta ya existe");
        }
        data.getRecetas().add(receta);
    }

    public List<Receta> findAllRecetas() {
        return data.getRecetas();
    }

    //Despacho

    public void updateRecetaEstado(String recetaId, EstadoReceta nuevoEstado) throws Exception {
        Receta receta = data.getRecetas().stream()
                .filter(r -> r.getId().equals(recetaId))
                .findFirst()
                .orElse(null);

        if (receta == null) {
            throw new Exception("Receta no encontrada");
        }

        receta.setEstado(nuevoEstado);
    }

    public List<Receta> findRecetasByPacienteId(String pacienteId) {
        return data.getRecetas().stream()
                .filter(r -> r.getIdPaciente().equals(pacienteId))
                .collect(java.util.stream.Collectors.toList());
    }

    //Para la persistencia al cerrar

    public void stop() {
        try {
            XmlPersister.instance().store(data);
        } catch (Exception e) {
            System.out.println("Error saving data: " + e);
        }
    }

    // Para el Dashboard
    public List<Receta> getPrescriptionsByDateRange(LocalDate startDate, LocalDate endDate) {
        return data.getRecetas().stream()
                .filter(r -> !r.getFechaConfeccion().isBefore(startDate) &&
                        !r.getFechaConfeccion().isAfter(endDate))
                .collect(Collectors.toList());
    }

    public Map<EstadoReceta, Long> getPrescriptionCountsByStatus() {
        return data.getRecetas().stream()
                .collect(Collectors.groupingBy(Receta::getEstado, Collectors.counting()));
    }

    public Map<String, Long> getMedicationUsageByMonth(String medicationCode,
                                                       LocalDate startDate, LocalDate endDate) {
        return data.getRecetas().stream()
                .filter(r -> !r.getFechaConfeccion().isBefore(startDate) &&
                        !r.getFechaConfeccion().isAfter(endDate))
                .filter(r -> r.getDetalles().stream()
                        .anyMatch(d -> d.getCodigoMedicamento().equals(medicationCode)))
                .collect(Collectors.groupingBy(
                        r -> r.getFechaConfeccion().getYear() + "-" +
                                String.format("%02d", r.getFechaConfeccion().getMonthValue()),
                        Collectors.counting()));
    }

}