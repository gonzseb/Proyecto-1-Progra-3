package proyecto.presentation.medicos;

import proyecto.data.entities.Medico;
import proyecto.data.lists.ListaMedicos;
import proyecto.exceptions.ElementoDuplicadoException;
import proyecto.exceptions.ValidacionException;
import proyecto.logic.Service;

public class Controller {
    private final View view;
    private final Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);

        // Inicializa lista
        model.setList(Service.instance().findAllMedicos());
        model.setCurrent(new Medico());
    }

    public void create(Medico medico) {
        try {
            Service.instance().createMedico(medico);
            clear();
            view.mostrarMensaje("Médico guardado correctamente");
        } catch (ValidacionException | ElementoDuplicadoException ex) {
            view.mostrarError(ex.getMessage());
        } catch (Exception ex) {
            view.mostrarError("Error inesperado: " + ex.getMessage());
        }
    }

    public void update(Medico medico) {
        try {
            Service.instance().updateMedico(medico);
            clear();
            view.mostrarMensaje("Médico actualizado correctamente");
        } catch (ValidacionException ex) {
            view.mostrarError(ex.getMessage());
        } catch (Exception ex) {
            view.mostrarError("Error inesperado: " + ex.getMessage());
        }
    }

    public void delete(String id) {
        try {
            Service.instance().deleteMedico(id);
            clear();
            view.mostrarMensaje("Médico eliminado correctamente");
        } catch (ValidacionException ex) {
            view.mostrarError(ex.getMessage());
        } catch (Exception ex) {
            view.mostrarError("Error inesperado: " + ex.getMessage());
        }
    }

    public void buscarMedicosPorNombre(String nombreParcial) {
        ListaMedicos resultado = Service.instance().findSomeMedicos(nombreParcial);
        model.setList(resultado);
    }

    public void read(String id) {
        try {
            Medico m = Service.instance().readMedico(id);
            model.setCurrent(m);
        } catch (ValidacionException ex) {
            view.mostrarError(ex.getMessage());
        } catch (Exception ex) {
            view.mostrarError("Error inesperado: " + ex.getMessage());
        }
    }

    public void clear() {
        model.setCurrent(new Medico());
        model.setList(Service.instance().findAllMedicos());
    }
}