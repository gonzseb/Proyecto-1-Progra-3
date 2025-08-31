package sistema.presentation.medicos;

import sistema.logic.entities.Medico;

import sistema.logic.Service;

import java.util.List;

public class Controller {
    private final View view;
    private final Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;

        view.setController(this);
        view.setModel(model);

        clear();
    }

    public void clear() {
        model.setCurrent(new Medico());
        model.setList(Service.instance().findAllMedicos());
    }

    public void create(Medico medico) throws Exception {
        Service.instance().createMedico(medico);
        clear();
    }

    public void delete(String id) throws Exception {
        Service.instance().deleteMedico(id);
        clear();
    }

    public void update(Medico medico) throws Exception {
        Service.instance().updateMedico(medico);
        clear();
    }

    public void findMedicoByName(String nombreParcial) {
        List<Medico> resultado = Service.instance().findSomeMedicos(nombreParcial);
        model.setList(resultado);
    }
}