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
        view.loadMedications(Service.instance().findAllMedicamentos());

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
            Map<EstadoReceta, Long> statusCounts = Service.instance().getPrescriptionCountsByStatus();
            view.updatePieChart(statusCounts);

            String selectedMed = model.getSelectedMedicamento();
            if (selectedMed != null && !selectedMed.isEmpty()) {
                if (selectedMed.equals("Todos los medicamentos")) {

                    Map<String, Long> allMedicationsData = Service.instance().getAllMedicationUsageByMonth(
                            model.getStartDate(), model.getEndDate());
                    view.updateLineChart(allMedicationsData, "Todos los medicamentos");
                } else {

                    Map<String, Long> monthlyData = Service.instance().getMedicationUsageByMonth(
                            selectedMed, model.getStartDate(), model.getEndDate());
                    view.updateLineChart(monthlyData, selectedMed);
                }
            }


            List<Receta> filteredData = Service.instance().getPrescriptionsByDateRange(
                    model.getStartDate(), model.getEndDate());
            model.setFilteredPrescriptions(filteredData);

        } catch (Exception e) {
            view.showError("Error updating charts: " + e.getMessage());
        }
    }
}