package sistema.presentation.pacientes;

import sistema.logic.entities.Paciente;

import sistema.presentation.AbstractModel;

import java.util.ArrayList;
import java.util.List;

import java.beans.PropertyChangeListener;

public class Model extends AbstractModel {
    private Paciente current;
    private List<Paciente> list;

    public static final String CURRENT = "current";
    public static final String LIST = "list";

    public Model() {
        current = new Paciente();
        list = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
    }

    public Paciente getCurrent() {
        return current;
    }

    public void setCurrent(Paciente current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public List<Paciente> getList() {
        return list;
    }

    public void setList(List<Paciente> list) {
        this.list = list;
        firePropertyChange(LIST);
    }
}