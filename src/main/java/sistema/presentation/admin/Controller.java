package sistema.presentation.admin;

import sistema.logic.entities.Farmaceuta;
import sistema.logic.entities.Medico;

import sistema.logic.Service;

import java.util.List;

public class Controller {
    private final Admin view;
    private final Model model;

    public Controller(Admin view, Model model) {
        this.view = view;
        this.model = model;

        view.setController(this);
        view.setModel(model);

        clear();
    }

    public void clear() {
        model.setCurrent(new Farmaceuta());
        model.setList(Service.instance().findAllFarmaceutas());
    }

    public void create(Farmaceuta farmaceuta) throws Exception { //mover excepciones al view
        Service.instance().createFarmaceuta(farmaceuta);
        clear();
    }

    public void delete(String id) throws Exception {
        Service.instance().deleteFarmaceuta(id);
        clear();
    }

    public void update(Farmaceuta farmaceuta) throws Exception {
        Service.instance().updateFarmaceuta(farmaceuta);
        clear();
    }

    public void findFarmaceutaByName(String nombreParcial) {
        List<Farmaceuta> resultado = Service.instance().findSomeFarmaceutas(nombreParcial);
        model.setList(resultado);
    }
}