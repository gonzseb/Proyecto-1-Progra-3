package sistema.presentation.dashboard;

import sistema.logic.entities.Receta;
import sistema.logic.entities.enums.EstadoReceta;
import sistema.presentation.AbstractModel;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Model extends AbstractModel {
    private LocalDate startDate;
    private LocalDate endDate;
    private String selectedMedicamento;
    private List<Receta> filteredPrescriptions;
    private Map<String, Integer> medicationMonthlyData;
    private Map<EstadoReceta, Integer> statusCounts;

    public static final String DATE_RANGE = "dateRange";
    public static final String MEDICATION_FILTER = "medicationFilter";
    public static final String FILTERED_DATA = "filteredData";

    public Model() {
        super();
        this.startDate = LocalDate.of(2024, 1, 1);
        this.endDate = LocalDate.now();
    }

    // Getters y setters
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        propertyChangeSupport.firePropertyChange(DATE_RANGE, null, startDate);
    }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        propertyChangeSupport.firePropertyChange(DATE_RANGE, null, endDate);
    }

    public String getSelectedMedicamento() { return selectedMedicamento; }
    public void setSelectedMedicamento(String medicamento) {
        this.selectedMedicamento = medicamento;
        propertyChangeSupport.firePropertyChange(MEDICATION_FILTER, null, medicamento);
    }

    public List<Receta> getFilteredPrescriptions() { return filteredPrescriptions; }
    public void setFilteredPrescriptions(List<Receta> prescriptions) {
        this.filteredPrescriptions = prescriptions;
        propertyChangeSupport.firePropertyChange(FILTERED_DATA, null, prescriptions);
    }

    public Map<String, Integer> getMedicationMonthlyData() { return medicationMonthlyData; }
    public void setMedicationMonthlyData(Map<String, Integer> data) {
        this.medicationMonthlyData = data;
    }

    public Map<EstadoReceta, Integer> getStatusCounts() { return statusCounts; }
    public void setStatusCounts(Map<EstadoReceta, Integer> counts) {
        this.statusCounts = counts;
    }
}