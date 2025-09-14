package sistema.presentation.dashboard;

import sistema.logic.Service;
import sistema.logic.entities.Receta;
import sistema.logic.entities.Medicamento;
import sistema.logic.entities.enums.EstadoReceta;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Controller {
    private final View view;
    private final Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;

        view.setController(this);
        view.setModel(model);

        initializeData();
    }

    private void initializeData() {
        // Load available medications for combobox
        view.loadMedications(Service.instance().findAllMedicamentos());

        // Load initial data
        updateCharts();
    }

    public void updateDateRange(int fromYear, int fromMonth, int toYear, int toMonth) {
        LocalDate startDate = LocalDate.of(fromYear, fromMonth, 1);
        LocalDate endDate = LocalDate.of(toYear, toMonth, 1).plusMonths(1).minusDays(1);

        model.setStartDate(startDate);
        model.setEndDate(endDate);

        updateCharts();
    }

    public void updateMedicationFilter(String medicationCode) {
        model.setSelectedMedicamento(medicationCode);
        updateCharts();
    }

    public void updateCharts() {
        try {
            // Update prescription status pie chart
            Map<EstadoReceta, Long> statusCounts = Service.instance().getPrescriptionCountsByStatus();
            view.updatePieChart(statusCounts);

            // Update medication line chart if medication is selected
            if (model.getSelectedMedicamento() != null && !model.getSelectedMedicamento().isEmpty()) {
                Map<String, Long> monthlyData = Service.instance().getMedicationUsageByMonth(
                        model.getSelectedMedicamento(),
                        model.getStartDate(),
                        model.getEndDate()
                );
                view.updateLineChart(monthlyData, model.getSelectedMedicamento());
            }

            // Update data table
            List<Receta> filteredData = Service.instance().getPrescriptionsByDateRange(
                    model.getStartDate(),
                    model.getEndDate()
            );
            model.setFilteredPrescriptions(filteredData);

        } catch (Exception e) {
            view.showError("Error updating charts: " + e.getMessage());
        }
    }
}