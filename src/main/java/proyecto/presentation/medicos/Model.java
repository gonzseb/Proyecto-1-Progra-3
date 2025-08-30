package proyecto.presentation.medicos;

import proyecto.logic.entities.Medico;
import proyecto.logic.lists.ListaMedicos;
import proyecto.presentation.AbstractModel;

import java.beans.PropertyChangeListener;

public class Model extends AbstractModel {
    private Medico current;
    private ListaMedicos list;

    public static final String CURRENT = "current";
    public static final String LIST = "list";

    public Model() {
        current = new Medico();
        list = new ListaMedicos();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
    }

    public Medico getCurrent() {
        return current;
    }

    public void setCurrent(Medico current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public ListaMedicos getList() {
        return list;
    }

    public void setList(ListaMedicos list) {
        this.list = list;
        firePropertyChange(LIST);
    }
}