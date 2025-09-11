package sistema.presentation.historico;

import sistema.logic.entities.Receta;
import sistema.presentation.AbstractModel;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    private List<Receta> list;
    private Receta current;

    public static final String LIST = "list";
    public static final String CURRENT = "current";

    public Model() {
        super();
        this.list = new ArrayList<>();
    }

    public List<Receta> getList() {
        return list;
    }

    public void setList(List<Receta> list) {
        this.list = list;
        propertyChangeSupport.firePropertyChange(LIST, null, list);
    }

    public Receta getCurrent() {
        return current;
    }

    public void setCurrent(Receta current) {
        this.current = current;
        propertyChangeSupport.firePropertyChange(CURRENT, null, current);
    }

    public void clearAll() {
        setCurrent(null);
        setList(new ArrayList<>());
    }
}