package sistema.presentation.despachoRecetas;

import sistema.logic.entities.Receta;
import sistema.logic.entities.Paciente;
import sistema.logic.entities.enums.EstadoReceta;
import sistema.logic.Service;

import javax.swing.*;
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
    }

    public void searchPrescriptionsByPatient() {
        String patientId = view.getPatientId();

        if (patientId.isEmpty()) {
            view.showError("Por favor ingrese el ID del paciente");
            return;
        }

        try {
            List<Paciente> pacientes = Service.instance().findAllPacientes();
            Paciente patient = pacientes.stream()
                    .filter(p -> p.getId().equals(patientId))
                    .findFirst()
                    .orElse(null);

            if (patient == null) {
                view.showError("Paciente con ID " + patientId + " no encontrado");
                model.setSelectedPatient(null);
                model.setPrescriptionsList(List.of());
                return;
            }

            model.setSelectedPatient(patient);

            List<Receta> allRecetas = Service.instance().findAllRecetas();
            List<Receta> patientPrescriptions = allRecetas.stream()
                    .filter(r -> r.getIdPaciente().equals(patientId))
                    .collect(Collectors.toList());

            if (patientPrescriptions.isEmpty()) {
                view.showMessage("No se encontraron recetas para el paciente " + patient.getNombre());
                model.setPrescriptionsList(List.of());
            } else {
                model.setPrescriptionsList(patientPrescriptions);
            }

        } catch (Exception e) {
            view.showError("Error buscando recetas: " + e.getMessage());
        }
    }

    public void moveToNextState() {
        int selectedRow = view.getSelectedRow();
        if (selectedRow < 0) {
            view.showMessage("Por favor seleccione una receta para cambiar su estado");
            return;
        }

        try {
            Receta selectedReceta = model.getPrescriptionsList().get(selectedRow);
            EstadoReceta currentState = selectedReceta.getEstado();
            EstadoReceta nextState = getNextState(currentState);

            if (nextState == null) {
                view.showMessage("La receta ya está en el estado final");
                return;
            }

            selectedReceta.setEstado(nextState);

            if (nextState == EstadoReceta.ENTREGADA) {
                selectedReceta.setFechaRetiro(java.time.LocalDate.now());
            }

            model.setPrescriptionsList(model.getPrescriptionsList());
            model.setCurrentPrescription(selectedReceta);

            view.showMessage("Estado actualizado a: " + nextState);

        } catch (Exception e) {
            view.showError("Error actualizando estado: " + e.getMessage());
        }
    }

    public void moveToPreviousState() {
        int selectedRow = view.getSelectedRow();
        if (selectedRow < 0) {
            view.showMessage("Por favor seleccione una receta para cambiar su estado");
            return;
        }

        try {
            Receta selectedReceta = model.getPrescriptionsList().get(selectedRow);
            EstadoReceta currentState = selectedReceta.getEstado();
            EstadoReceta previousState = getPreviousState(currentState);

            if (previousState == null) {
                view.showMessage("La receta ya está en el estado inicial");
                return;
            }

            selectedReceta.setEstado(previousState);

            if (currentState == EstadoReceta.ENTREGADA) {
                selectedReceta.setFechaRetiro(null);
            }

            model.setPrescriptionsList(model.getPrescriptionsList());
            model.setCurrentPrescription(selectedReceta);

            view.showMessage("Estado actualizado a: " + previousState);

        } catch (Exception e) {
            view.showError("Error actualizando estado: " + e.getMessage());
        }
    }

    private EstadoReceta getNextState(EstadoReceta currentState) {
        switch (currentState) {
            case CONFECCIONADA:
                return EstadoReceta.PROCESO;
            case PROCESO:
                return EstadoReceta.LISTA;
            case LISTA:
                return EstadoReceta.ENTREGADA;
            case ENTREGADA:
                return null;
            default:
                return null;
        }
    }

    private EstadoReceta getPreviousState(EstadoReceta currentState) {
        switch (currentState) {
            case CONFECCIONADA:
                return null; // Already at initial state
            case PROCESO:
                return EstadoReceta.CONFECCIONADA;
            case LISTA:
                return EstadoReceta.PROCESO;
            case ENTREGADA:
                return EstadoReceta.LISTA;
            default:
                return null;
        }
    }

    public void clearForm() {
        view.clearForm();
        model.clearAll();
    }

    public void showPrescriptionDetails(int selectedRow) {
        if (selectedRow < 0) {
            view.showMessage("Por favor seleccione una receta de la tabla");
            return;
        }

        try {
            Receta selectedReceta = model.getPrescriptionsList().get(selectedRow);
            model.setCurrentPrescription(selectedReceta);

            showRecetaDetailsDialog(selectedReceta);

        } catch (Exception e) {
            view.showError("Error mostrando detalles: " + e.getMessage());
        }
    }

    private void showRecetaDetailsDialog(Receta receta) {
        StringBuilder details = new StringBuilder();
        details.append("=== DETALLES DE LA RECETA ===\n\n");
        details.append("ID Receta: ").append(receta.getId()).append("\n");

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
            details.append("No hay medicamentos registrados\n");
        } else {
            List<sistema.logic.entities.Medicamento> medicamentos = null;
            try {
                medicamentos = Service.instance().findAllMedicamentos();
            } catch (Exception ex) {
                details.append("Error cargando información de medicamentos\n");
            }

            for (int i = 0; i < receta.getDetalles().size(); i++) {
                var detalle = receta.getDetalles().get(i);
                details.append((i + 1)).append(". ");

                String medicamentoNombre = "Desconocido";
                if (medicamentos != null) {
                    medicamentoNombre = medicamentos.stream()
                            .filter(med -> med.getCodigo().equals(detalle.getCodigoMedicamento()))
                            .map(sistema.logic.entities.Medicamento::getNombre)
                            .findFirst()
                            .orElse("Desconocido");
                }

                details.append("Medicamento: ").append(medicamentoNombre)
                        .append(" (").append(detalle.getCodigoMedicamento()).append(")\n");
                details.append("   Cantidad: ").append(detalle.getCantidad()).append("\n");
                details.append("   Duración: ").append(detalle.getDuracionDias()).append(" días\n");
                details.append("   Indicaciones: ").append(detalle.getIndicaciones().isEmpty() ? "Sin indicaciones" : detalle.getIndicaciones()).append("\n\n");
            }
        }

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

}