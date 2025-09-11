package sistema.presentation.prescribir;

import sistema.logic.Service;
import sistema.logic.entities.Receta;
import sistema.logic.entities.RecetaDetalle;
import sistema.logic.entities.Paciente;
import sistema.presentation.Sesion;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import java.time.LocalDate;

public class Controller {
    private final View view;
    private final Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;

        view.setController(this);
        view.setModel(model);

        clear();
    }

    public void clear() {
        model.clearAll();
    }

    public void showPatientSelection() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(view.getPanel());
        sistema.presentation.popUpPacientes.View dialog = new sistema.presentation.popUpPacientes.View(parentFrame);
        dialog.setVisible(true);

        Paciente selectedPatient = dialog.getSelectedPatient();
        if (selectedPatient != null) {
            model.setSelectedPaciente(selectedPatient);
            view.showMessage("Paciente seleccionado: " + selectedPatient.getNombre());
        }
    }

    public void showMedicationSelection() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(view.getPanel());
        sistema.presentation.popUpSeleccionMedicamento.View dialog =
                new sistema.presentation.popUpSeleccionMedicamento.View(parentFrame);
        dialog.setVisible(true);

        RecetaDetalle detalle = dialog.getRecetaDetalle();
        if (detalle != null) {
            model.addMedicamento(detalle);
            view.showMessage("Medicamento agregado correctamente");
        }
    }

    public void removeMedicamento(int index) {
        model.removeMedicamento(index);
        view.showMessage("Medicamento eliminado de la prescripción");
    }

    public void saveReceta() throws Exception {
        if (!view.validateForm()) {
            return;
        }

        try {
            // Generate unique ID
            String recetaId = generateRecetaId();
            String medicoId = Sesion.getUsuario().getId();
            String pacienteId = model.getSelectedPaciente().getId();
            LocalDate fechaConfeccion = LocalDate.now();

            Receta nuevaReceta = new Receta(recetaId, medicoId, pacienteId, fechaConfeccion);
            nuevaReceta.setFechaRetiro(view.getFechaRetiro());

            // Add all medication details
            for (RecetaDetalle detalle : model.getMedicamentosSeleccionados()) {
                nuevaReceta.agregarDetalle(detalle);
            }

            Service.instance().createReceta(nuevaReceta);
            view.showMessage("Prescripción guardada exitosamente.\nID: " + recetaId);
            clear();

        } catch (Exception e) {
            throw new Exception("Error al guardar la prescripción: " + e.getMessage());
        }
    }

    public void clearForm() {
        clear();
    }

    private String generateRecetaId() {
        String date = LocalDate.now().toString().replace("-", "");
        String random = String.format("%04d", (int)(Math.random() * 10000));
        return "REC-" + date + "-" + random;
    }

    public boolean hasUnsavedChanges() {
        return model.getSelectedPaciente() != null ||
                !model.getMedicamentosSeleccionados().isEmpty();
    }
}