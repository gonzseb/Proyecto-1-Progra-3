package proyecto.logic;

import proyecto.data.Data;
import proyecto.data.entities.Farmaceuta;
import proyecto.data.entities.Medicamento;
import proyecto.data.entities.Medico;
import proyecto.data.entities.Paciente;
import proyecto.data.lists.ListaMedicos;
import proyecto.exceptions.ElementoDuplicadoException;
import proyecto.exceptions.ValidacionException;

import java.util.List;

public class Service {
    private static Service theInstance;

    public static Service instance() {
        if (theInstance == null) theInstance = new Service();
        return theInstance;
    }

    private final Data data;

    private Service() {
        data = new Data();
    }

    // --- CRUD ---
    public void createMedico(Medico medico) {
        validarMedico(medico);
        try {
            data.getMedicos().agregar(medico);
        } catch (ElementoDuplicadoException e) {
            throw e;
        }
    }

    public Medico readMedico(String id) {
        if (id == null || id.isBlank()) {
            throw new ValidacionException("Debe indicar un ID para buscar");
        }
        return data.getMedicos().buscarPorId(id);
    }

    public ListaMedicos findAllMedicos() {
        return data.getMedicos();
    }

    public void updateMedico(Medico medico) {
        validarMedico(medico);
        data.getMedicos().modificar(medico.getId(), medico.getNombre(), medico.getEspecialidad());
    }

    public void deleteMedico(String id) {
        if (id == null || id.isBlank()) {
            throw new ValidacionException("Debe indicar un ID para eliminar");
        }
        data.getMedicos().eliminarPorId(id);
    }

    public ListaMedicos findSomeMedicos(String nombre) {
        if (nombre == null) nombre = "";
        return data.getMedicos().busquedaParcialPorNombre(nombre);
    }

    // --- Pacientes ---
    public void createPaciente(Paciente paciente) {
        validarPaciente(paciente);
        data.getPacientes().agregar(paciente);
    }

    public Paciente readPaciente(String id) {
        validarId(id);
        return data.getPacientes().buscarPorId(id);
    }

    public List<Paciente> findAllPacientes() {
        return data.getPacientes().listar();
    }

    public void updatePaciente(String id, String nombre, String fecha, String tel) {
        validarId(id);
        data.getPacientes().modificar(id, nombre, fecha, tel);
    }

    public void deletePaciente(String id) {
        validarId(id);
        data.getPacientes().eliminarPorId(id);
    }

    // --- Farmaceutas ---
    public void createFarmaceuta(Farmaceuta f) {
        validarFarmaceuta(f);
        data.getFarmaceutas().agregar(f);
    }

    public Farmaceuta readFarmaceuta(String id) {
        validarId(id);
        return data.getFarmaceutas().buscarPorId(id);
    }

    public List<Farmaceuta> findAllFarmaceutas() {
        return data.getFarmaceutas().listar();
    }

    public void updateFarmaceuta(String id, String nombre, String clave) {
        validarId(id);
        data.getFarmaceutas().modificar(id, nombre, clave);
    }

    public void deleteFarmaceuta(String id) {
        validarId(id);
        data.getFarmaceutas().eliminarPorId(id);
    }

    // --- Medicamentos ---
    public void createMedicamento(Medicamento m) {
        validarMedicamento(m);
        data.getMedicamentos().agregar(m);
    }

    public Medicamento readMedicamento(String codigo) {
        validarCodigo(codigo);
        return data.getMedicamentos().buscarPorCodigo(codigo);
    }

    public List<Medicamento> findAllMedicamentos() {
        return data.getMedicamentos().listar();
    }

    public void updateMedicamento(String codigo, String nombre, String presentacion) {
        validarCodigo(codigo);
        data.getMedicamentos().modificar(codigo, nombre, presentacion);
    }

    public void deleteMedicamento(String codigo) {
        validarCodigo(codigo);
        data.getMedicamentos().eliminarPorCodigo(codigo);
    }

    // --- Validaciones de entrada ---
    private void validarId(String id) {
        if (id == null || id.isBlank()) {
            throw new ValidacionException("Id obligatorio");
        }
    }

    private void validarCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new ValidacionException("Código obligatorio");
        }
    }

    private void validarMedico(Medico medico) {
        if (medico.getId() == null || medico.getId().isBlank()) {
            throw new ValidacionException("El ID del médico no puede estar vacío");
        }
        if (medico.getNombre() == null || medico.getNombre().isBlank()) {
            throw new ValidacionException("El nombre del médico no puede estar vacío");
        }
        if (medico.getEspecialidad() == null || medico.getEspecialidad().isBlank()) {
            throw new ValidacionException("La especialidad no puede estar vacía");
        }
    }

    private void validarPaciente(Paciente p) {
        if (p == null) throw new ValidacionException("Paciente obligatorio");
        validarId(p.getId());
        if (p.getNombre() == null || p.getNombre().isBlank()) {
            throw new ValidacionException("Nombre de paciente obligatorio");
        }
    }

    private void validarFarmaceuta(Farmaceuta f) {
        if (f == null) throw new ValidacionException("Farmaceuta obligatorio");
        validarId(f.getId());
        if (f.getNombre() == null || f.getNombre().isBlank()) {
            throw new ValidacionException("Nombre de farmaceuta obligatorio");
        }
    }

    private void validarMedicamento(Medicamento m) {
        if (m == null) throw new ValidacionException("Medicamento obligatorio");
        validarCodigo(m.getCodigo());
        if (m.getNombre() == null || m.getNombre().isBlank()) {
            throw new ValidacionException("Nombre de medicamento obligatorio");
        }
    }
}