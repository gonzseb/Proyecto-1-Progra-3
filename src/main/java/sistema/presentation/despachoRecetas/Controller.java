package sistema.presentation.despachoRecetas;

import sistema.logic.entities.Receta;
import sistema.logic.entities.Paciente;
import sistema.logic.entities.enums.EstadoReceta;
import sistema.logic.Service;
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
            // First, find and set the patient to display their name
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

            // Find all prescriptions for this patient
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

            // Update the prescription state
            selectedReceta.setEstado(nextState);

            // If moving to ENTREGADA, set pickup date to today
            if (nextState == EstadoReceta.ENTREGADA) {
                selectedReceta.setFechaRetiro(java.time.LocalDate.now());
            }

            // Refresh the table to show updated state
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

            // Update the prescription state
            selectedReceta.setEstado(previousState);

            // If moving back from ENTREGADA, clear pickup date
            if (currentState == EstadoReceta.ENTREGADA) {
                selectedReceta.setFechaRetiro(null);
            }

            // Refresh the table to show updated state
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
                return null; // Already at final state
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
}