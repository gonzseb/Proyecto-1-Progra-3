package sistema.presentation.pacientes;

import sistema.logic.entities.Paciente;

import sistema.logic.Service;
import sistema.presentation.pacientes.Pacientes;
import sistema.presentation.pacientes.ModelPacientes;

import java.util.List;

public class ControllerPacientes {
    private final Pacientes view;
    private final ModelPacientes model;

    public ControllerPacientes(Pacientes view, ModelPacientes model) {
        this.view = view;
        this.model = model;

        view.setController(this);
        view.setModel(model);

        clear();
    }

    public void clear() {
        model.setCurrent(new Paciente());
        model.setList(Service.instance().findAllPacientes());
    }

    public void create(Paciente paciente) throws Exception { //mover excepciones al view
        Service.instance().createPaciente(paciente);
        clear();
    }

    public void delete(String id) throws Exception {
        Service.instance().deletePaciente(id);
        clear();
    }

    public void update(Paciente paciente) throws Exception {
        Service.instance().updatePaciente(paciente);
        clear();
    }

    public void findPacienteByName(String nombreParcial) {
        List<Paciente> resultado = Service.instance().findSomePacientes(nombreParcial);
        model.setList(resultado);
    }
}
