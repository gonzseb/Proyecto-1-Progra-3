package sistema.presentation.medicamentos;

import sistema.logic.entities.Medicamento;

import sistema.logic.Service;

import java.util.List;

public class Controller {
    private final sistema.presentation.medicamentos.View view;
    private final sistema.presentation.medicamentos.Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;

        view.setController(this);
        view.setModel(model);

        clear();
    }

    public void clear() {
        model.setCurrent(new Medicamento());
        model.setList(Service.instance().findAllMedicamentos());
    }

    public void create(Medicamento medicamento) throws Exception {
        Service.instance().createMedicamento(medicamento);
        clear();
    }

    public void delete(String codigo) throws Exception {
        Service.instance().deleteMedicamento(codigo);
        clear();
    }

    public void update(Medicamento medicamento) throws Exception {
        Service.instance().updateMedicamento(medicamento);
        clear();
    }

    public void findMedicamentoByName(String nombreParcial) {
        List<Medicamento> resultado = Service.instance().findSomeMedicamentos(nombreParcial);
        model.setList(resultado);
    }
}