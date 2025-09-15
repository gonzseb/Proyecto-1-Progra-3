package sistema.presentation.dashboard;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import sistema.logic.Service;
import sistema.logic.entities.Medicamento;
import sistema.logic.entities.Receta;
import sistema.logic.entities.enums.EstadoReceta;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;

public class View implements PropertyChangeListener {
    private JPanel panel1;
    private JComboBox<String> comboBoxDesdeAño;
    private JComboBox<String> comboBoxDesdeMes;
    private JComboBox<String> comboBoxMedicamento;
    private JButton OKButton;
    private JTable tablaMedicamentos;
    private JPanel medicamentosPanel;
    private JPanel medicamentosLineGraph;
    private JPanel datosPanel;
    private JPanel recetasPanel;
    private JPanel recetasPieGraph;
    private JComboBox<String> comboBoxHastaAño;
    private JComboBox<String> comboBoxHastaMes;

    private Model model;
    private Controller controller;
    private TableModel tableModel;

    private ChartPanel lineChartPanel;
    private ChartPanel pieChartPanel;

    public View() {
        setupComponents();
        setupEventListeners();
        initializeChartPanels();
    }

    private void setupComponents() {

        for (int year = 2024; year <= 2030; year++) {
            comboBoxDesdeAño.addItem(String.valueOf(year));
            comboBoxHastaAño.addItem(String.valueOf(year));
        }

        String[] months = {"1-Enero", "2-Febrero", "3-Marzo", "4-Abril", "5-Mayo", "6-Junio",
                "7-Julio", "8-Agosto", "9-Septiembre", "10-Octubre", "11-Noviembre", "12-Diciembre"};

        for (String month : months) {
            comboBoxDesdeMes.addItem(month);
            comboBoxHastaMes.addItem(month);
        }

        comboBoxDesdeAño.setSelectedItem("2024");
        comboBoxDesdeMes.setSelectedIndex(0); // January
        comboBoxHastaAño.setSelectedItem("2025");
        comboBoxHastaMes.setSelectedIndex(11); // December

        tableModel = new TableModel(new int[]{TableModel.MEDICAMENTO, TableModel.PERIODO},
                new java.util.ArrayList<>());
        tablaMedicamentos.setModel(tableModel);
    }

    private void setupEventListeners() {
        OKButton.addActionListener(e -> {
            if (controller != null) {
                updateFilters();
            }
        });
    }

    private void initializeChartPanels() {
        medicamentosLineGraph.setLayout(new BorderLayout());
        recetasPieGraph.setLayout(new BorderLayout());

        updateLineChart(new java.util.HashMap<>(), "");
        updatePieChart(new java.util.HashMap<>());
    }

    private void updateFilters() {
        try {
            int fromYear = Integer.parseInt((String) comboBoxDesdeAño.getSelectedItem());
            int fromMonth = Integer.parseInt(((String) comboBoxDesdeMes.getSelectedItem()).split("-")[0]);
            int toYear = Integer.parseInt((String) comboBoxHastaAño.getSelectedItem());
            int toMonth = Integer.parseInt(((String) comboBoxHastaMes.getSelectedItem()).split("-")[0]);

            String selectedMed = (String) comboBoxMedicamento.getSelectedItem();

            controller.updateDateRange(fromYear, fromMonth, toYear, toMonth);
            controller.updateMedicationFilter(selectedMed != null ? selectedMed.split(" - ")[0] : null);

        } catch (Exception e) {
            showError("Error in date selection: " + e.getMessage());
        }
    }

    public void updateLineChart(Map<String, Long> monthlyData, String medicationName) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<String, Long> entry : monthlyData.entrySet()) {
            dataset.addValue(entry.getValue(), medicationName, entry.getKey());
        }

        JFreeChart lineChart = ChartFactory.createLineChart(
                "Medicamentos por Mes",
                "Mes",
                "Cantidad",
                dataset
        );

        CategoryPlot plot = lineChart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.GRAY);

        if (lineChartPanel != null) {
            medicamentosLineGraph.remove(lineChartPanel);
        }

        lineChartPanel = new ChartPanel(lineChart);
        medicamentosLineGraph.add(lineChartPanel, BorderLayout.CENTER);
        medicamentosLineGraph.revalidate();
        medicamentosLineGraph.repaint();
    }

    public void updatePieChart(Map<EstadoReceta, Long> statusCounts) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        long total = statusCounts.values().stream().mapToLong(Long::longValue).sum();

        for (Map.Entry<EstadoReceta, Long> entry : statusCounts.entrySet()) {
            double percentage = total > 0 ? (entry.getValue().doubleValue() / total) * 100 : 0;
            dataset.setValue(entry.getKey().name() + " = " + entry.getValue() +
                            " (" + String.format("%.0f", percentage) + "%)",
                    entry.getValue());
        }

        JFreeChart pieChart = ChartFactory.createPieChart(
                "Recetas por Estado",
                dataset,
                true, // legend
                true, // tooltips
                false // URLs
        );

        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setSectionPaint("CONFECCIONADA", Color.YELLOW);
        plot.setSectionPaint("PROCESO", Color.RED);
        plot.setSectionPaint("LISTA", Color.BLUE);
        plot.setSectionPaint("ENTREGADA", Color.GREEN);

        if (pieChartPanel != null) {
            recetasPieGraph.remove(pieChartPanel);
        }

        pieChartPanel = new ChartPanel(pieChart);
        recetasPieGraph.add(pieChartPanel, BorderLayout.CENTER);
        recetasPieGraph.revalidate();
        recetasPieGraph.repaint();
    }

    public void loadMedications(List<Medicamento> medications) {
        comboBoxMedicamento.removeAllItems();
        comboBoxMedicamento.addItem("Todos los medicamentos");

        for (Medicamento med : medications) {
            comboBoxMedicamento.addItem(med.getCodigo() + " - " + med.getNombre());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.FILTERED_DATA:
                updateTableData();
                break;
        }
    }

    private void updateTableData() {
        if (model != null && model.getFilteredPrescriptions() != null) {
            java.util.List<MedicationMonthlySummary> summaries = createMonthlySummaries();

            int[] cols = {TableModel.MEDICAMENTO, TableModel.PERIODO};
            tablaMedicamentos.setModel(new TableModel(cols, summaries));
        }
    }


    public void showError(String message) {
        JOptionPane.showMessageDialog(panel1, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(panel1, message, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public JPanel getPanel() { return panel1; }

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private java.util.List<MedicationMonthlySummary> createMonthlySummaries() {
        java.util.List<MedicationMonthlySummary> summaries = new java.util.ArrayList<>();

        if (model.getFilteredPrescriptions() != null) {

            Map<String, Map<String, Long>> medicationByMonth = new java.util.HashMap<>();

            for (Receta receta : model.getFilteredPrescriptions()) {
                String yearMonth = receta.getFechaConfeccion().getYear() + "-" +
                        String.format("%02d", receta.getFechaConfeccion().getMonthValue());

                for (sistema.logic.entities.RecetaDetalle detalle : receta.getDetalles()) {
                    String medCode = detalle.getCodigoMedicamento();


                    String medName = Service.instance().findAllMedicamentos().stream()
                            .filter(m -> m.getCodigo().equals(medCode))
                            .map(sistema.logic.entities.Medicamento::getNombre)
                            .findFirst()
                            .orElse("Desconocido");

                    medicationByMonth.computeIfAbsent(medName, k -> new java.util.HashMap<>())
                            .merge(yearMonth, 1L, Long::sum);
                }
            }

            for (Map.Entry<String, Map<String, Long>> medEntry : medicationByMonth.entrySet()) {
                for (Map.Entry<String, Long> monthEntry : medEntry.getValue().entrySet()) {
                    summaries.add(new MedicationMonthlySummary(
                            medEntry.getKey(),
                            monthEntry.getKey(),
                            monthEntry.getValue().intValue()
                    ));
                }
            }
        }

        return summaries;
    }
}