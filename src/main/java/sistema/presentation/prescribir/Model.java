package sistema.presentation.prescribir;

import sistema.logic.entities.Receta;
import sistema.logic.entities.RecetaDetalle;
import sistema.logic.entities.Paciente;
import sistema.presentation.AbstractModel;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    private Receta currentReceta;
    private Paciente selectedPaciente;
    private List<RecetaDetalle> medicamentosSeleccionados;

    public static final String CURRENT_RECETA = "currentReceta";
    public static final String SELECTED_PACIENTE = "selectedPaciente";
    public static final String MEDICAMENTOS_LIST = "medicamentosList";

    public Model() {
        super();
        this.medicamentosSeleccionados = new ArrayList<>();
    }

    public Receta getCurrentReceta() { return currentReceta; }

    public void setCurrentReceta(Receta receta) {
        this.currentReceta = receta;
        propertyChangeSupport.firePropertyChange(CURRENT_RECETA, null, receta);
    }

    public Paciente getSelectedPaciente() { return selectedPaciente; }

    public void setSelectedPaciente(Paciente paciente) {
        this.selectedPaciente = paciente;
        propertyChangeSupport.firePropertyChange(SELECTED_PACIENTE, null, paciente);
    }

    public List<RecetaDetalle> getMedicamentosSeleccionados() {
        return medicamentosSeleccionados;
    }

    public void addMedicamento(RecetaDetalle detalle) {
        this.medicamentosSeleccionados.add(detalle);
        propertyChangeSupport.firePropertyChange(MEDICAMENTOS_LIST, null, medicamentosSeleccionados);
    }

    public void removeMedicamento(int index) {
        if (index >= 0 && index < medicamentosSeleccionados.size()) {
            medicamentosSeleccionados.remove(index);
            propertyChangeSupport.firePropertyChange(MEDICAMENTOS_LIST, null, medicamentosSeleccionados);
        }
    }

    public void clearAll() {
        setCurrentReceta(null);
        setSelectedPaciente(null);
        medicamentosSeleccionados.clear();
        propertyChangeSupport.firePropertyChange(MEDICAMENTOS_LIST, null, medicamentosSeleccionados);
    }
}