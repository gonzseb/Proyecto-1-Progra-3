package sistema.presentation.historico;

import sistema.logic.entities.Receta;
import sistema.logic.entities.enums.EstadoReceta;
import sistema.logic.Service;
import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {
    private View view;
    private Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        view.setModel(model);
        view.setController(this);

        // Load all prescriptions on startup
        loadAllRecetas();
    }

    private void loadAllRecetas() {
        try {
            List<Receta> recetas = Service.instance().findAllRecetas();
            model.setList(recetas);

            if (recetas.isEmpty()) {
                view.showMessage("No hay recetas registradas en el sistema");
            }
        } catch (Exception e) {
            view.showError("Error cargando recetas: " + e.getMessage());
        }
    }

    public void search() {
        String searchType = view.getSearchType();
        String searchText = view.getSearchText();

        if (searchText.isEmpty()) {
            loadAllRecetas();
            return;
        }

        try {
            List<Receta> allRecetas = Service.instance().findAllRecetas();
            List<Receta> filteredRecetas;

            switch (searchType) {
                case "ID Receta":
                    filteredRecetas = allRecetas.stream()
                            .filter(r -> r.getId().toLowerCase().contains(searchText.toLowerCase()))
                            .collect(Collectors.toList());
                    break;

                case "Paciente":
                    // Search by patient ID
                    filteredRecetas = allRecetas.stream()
                            .filter(r -> r.getIdPaciente().toLowerCase().contains(searchText.toLowerCase()))
                            .collect(Collectors.toList());
                    break;

                case "Médico":
                    // Search by doctor ID
                    filteredRecetas = allRecetas.stream()
                            .filter(r -> r.getIdMedico().toLowerCase().contains(searchText.toLowerCase()))
                            .collect(Collectors.toList());
                    break;

                case "Estado":
                    // Search by estado (case insensitive)
                    filteredRecetas = allRecetas.stream()
                            .filter(r -> r.getEstado().toString().toLowerCase().contains(searchText.toLowerCase()))
                            .collect(Collectors.toList());
                    break;

                default:
                    filteredRecetas = allRecetas;
                    break;
            }

            model.setList(filteredRecetas);

            if (filteredRecetas.isEmpty()) {
                view.showMessage("No se encontraron recetas que coincidan con: " + searchText);
            }

        } catch (Exception e) {
            view.showError("Error en la búsqueda: " + e.getMessage());
        }
    }

    public void viewDetails() {
        int selectedRow = view.getSelectedRow();
        if (selectedRow < 0) {
            view.showMessage("Por favor seleccione una receta para ver los detalles");
            return;
        }

        try {
            // Get the selected receta directly from the model's list
            Receta selectedReceta = model.getList().get(selectedRow);
            model.setCurrent(selectedReceta);

            // Create and show details dialog
            showRecetaDetailsDialog(selectedReceta);

        } catch (Exception e) {
            view.showError("Error mostrando detalles: " + e.getMessage());
        }
    }

    private void showRecetaDetailsDialog(Receta receta) {
        StringBuilder details = new StringBuilder();
        details.append("=== DETALLES DE LA RECETA ===\n\n");
        details.append("ID Receta: ").append(receta.getId()).append("\n");

        // Look up patient name
        String pacienteNombre = "Desconocido";
        try {
            List<sistema.logic.entities.Paciente> pacientes = Service.instance().findAllPacientes();
            pacienteNombre = pacientes.stream()
                    .filter(p -> p.getId().equals(receta.getIdPaciente()))
                    .map(sistema.logic.entities.Paciente::getNombre)
                    .findFirst()
                    .orElse("Desconocido");
        } catch (Exception ex) {
            pacienteNombre = "Error";
        }

        // Look up doctor name
        String medicoNombre = "Desconocido";
        try {
            List<sistema.logic.entities.Medico> medicos = Service.instance().findAllMedicos();
            medicoNombre = medicos.stream()
                    .filter(m -> m.getId().equals(receta.getIdMedico()))
                    .map(sistema.logic.entities.Medico::getNombre)
                    .findFirst()
                    .orElse("Desconocido");
        } catch (Exception ex) {
            medicoNombre = "Error";
        }

        details.append("Paciente: ").append(pacienteNombre).append(" (").append(receta.getIdPaciente()).append(")\n");
        details.append("Médico: ").append(medicoNombre).append(" (").append(receta.getIdMedico()).append(")\n");
        details.append("Fecha Confección: ").append(receta.getFechaConfeccion()).append("\n");
        details.append("Fecha Retiro: ").append(receta.getFechaRetiro() != null ? receta.getFechaRetiro() : "No retirada").append("\n");
        details.append("Estado: ").append(receta.getEstado()).append("\n\n");

        details.append("=== MEDICAMENTOS ===\n");
        if (receta.getDetalles().isEmpty()) {
            details.append("No hay medicamentos registrados");
        } else {
            // Get all medications for lookup
            List<sistema.logic.entities.Medicamento> medicamentos = null;
            try {
                medicamentos = Service.instance().findAllMedicamentos();
            } catch (Exception ex) {
                details.append("Error cargando información de medicamentos\n");
            }

            for (int i = 0; i < receta.getDetalles().size(); i++) {
                var detalle = receta.getDetalles().get(i);
                details.append((i + 1)).append(". ");

                // Look up medication name
                String medicamentoNombre = "Desconocido";
                if (medicamentos != null) {
                    medicamentoNombre = medicamentos.stream()
                            .filter(med -> med.getCodigo().equals(detalle.getCodigoMedicamento()))
                            .map(sistema.logic.entities.Medicamento::getNombre)
                            .findFirst()
                            .orElse("Desconocido");
                }

                details.append("Medicamento: ").append(medicamentoNombre).append(" (").append(detalle.getCodigoMedicamento()).append(")\n");
                details.append("   Cantidad: ").append(detalle.getCantidad()).append("\n");
                details.append("   Duración: ").append(detalle.getDuracionDias()).append(" días\n");
                details.append("   Indicaciones: ").append(detalle.getIndicaciones().isEmpty() ? "Sin indicaciones" : detalle.getIndicaciones()).append("\n\n");
            }
        }

        // Show in a dialog with scroll pane for long content
        JTextArea textArea = new JTextArea(details.toString());
        textArea.setEditable(false);
        textArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(500, 400));

        JOptionPane.showMessageDialog(
                view.getPanel(),
                scrollPane,
                "Detalles de Receta - " + receta.getId(),
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void clearSearch() {
        view.clearSearchField();
        loadAllRecetas();
        model.setCurrent(null);
    }
}