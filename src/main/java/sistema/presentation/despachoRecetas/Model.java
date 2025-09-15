package sistema.presentation.despachoRecetas;

import sistema.logic.entities.Receta;
import sistema.logic.entities.Paciente;
import sistema.presentation.AbstractModel;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    private List<Receta> prescriptionsList;
    private Receta currentPrescription;
    private Paciente selectedPatient;

    public static final String PRESCRIPTIONS_LIST = "prescriptionsList";
    public static final String CURRENT_PRESCRIPTION = "currentPrescription";
    public static final String SELECTED_PATIENT = "selectedPatient";

    public Model() {
        super();
        this.prescriptionsList = new ArrayList<>();
    }

    public List<Receta> getPrescriptionsList() {
        return prescriptionsList;
    }

    public void setPrescriptionsList(List<Receta> prescriptionsList) {
        this.prescriptionsList = prescriptionsList;
        propertyChangeSupport.firePropertyChange(PRESCRIPTIONS_LIST, null, prescriptionsList);
    }

    public Receta getCurrentPrescription() {
        return currentPrescription;
    }

    public void setList(List<Receta> list) {
        this.prescriptionsList = list;
        propertyChangeSupport.firePropertyChange(PRESCRIPTIONS_LIST, null, list);
    }

    public void setCurrentPrescription(Receta currentPrescription) {
        this.currentPrescription = currentPrescription;
        propertyChangeSupport.firePropertyChange(CURRENT_PRESCRIPTION, null, currentPrescription);
    }

    public Paciente getSelectedPatient() {
        return selectedPatient;
    }

    public void setSelectedPatient(Paciente selectedPatient) {
        this.selectedPatient = selectedPatient;
        propertyChangeSupport.firePropertyChange(SELECTED_PATIENT, null, selectedPatient);
    }

    public void clearAll() {
        setCurrentPrescription(null);
        setSelectedPatient(null);
        setPrescriptionsList(new ArrayList<>());
    }
}