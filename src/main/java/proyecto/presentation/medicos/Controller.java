package proyecto.presentation.medicos;

import proyecto.logic.entities.Medico;

import proyecto.logic.Service;

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

    public void read(String id) throws Exception {
        Medico medico = new Medico();
        medico.setId(id);
        try {
            model.setCurrent(Service.instance().readMedico(medico));
        } catch (Exception ex) {
            Medico medico2 = new Medico();
            medico2.setId(id);
            model.setCurrent(medico2);
            throw ex;
        }
    }

    public void delete(String id) throws Exception {
        Service.instance().deleteMedico(id);
        clear();
    }

//    public void update(Medico medico) {
//        try {
//            Service.instance().updateMedico(medico);
//            clear();
//            view.mostrarMensaje("Médico actualizado correctamente");
//        } catch (ValidacionException ex) {
//            view.mostrarError(ex.getMessage());
//        } catch (Exception ex) {
//            view.mostrarError("Error inesperado: " + ex.getMessage());
//        }
//    }

//
    public void findMedicoByName(String nombreParcial) {
        List<Medico> resultado = Service.instance().findSomeMedicos(nombreParcial);
        model.setList(resultado);
    }


}