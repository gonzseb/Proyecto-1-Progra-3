package sistema.presentation.login;

import sistema.logic.entities.Usuario;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Model {
    private Usuario usuario;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public static final String CURRENT = "current";

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        Usuario old = this.usuario;
        this.usuario = usuario;
        pcs.firePropertyChange(CURRENT, old, usuario);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }
}
