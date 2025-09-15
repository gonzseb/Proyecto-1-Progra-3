package sistema.presentation.prescribir;

import sistema.logic.Service;
import sistema.logic.entities.Medicamento;
import sistema.logic.entities.Receta;
import sistema.logic.entities.RecetaDetalle;
import sistema.logic.entities.Paciente;
import sistema.presentation.Sesion;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import java.time.LocalDate;
import java.util.List;

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
            // Generate unique ID in format RECxxx
            List<Receta> recetas = Service.instance().findAllRecetas();

            int maxNumero = recetas.stream()
                    .map(Receta::getId)
                    .filter(id -> id.startsWith("REC"))
                    .mapToInt(id -> {
                        try {
                            return Integer.parseInt(id.substring(3)); // Extract number after REC
                        } catch (NumberFormatException e) {
                            return 0;
                        }
                    })
                    .max()
                    .orElse(4); // Start after 4 if no existing recipes

            int siguienteNumero = maxNumero + 1;
            String recetaId = String.format("REC%03d", siguienteNumero); // REC005, REC006, etc.

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

    public void showPrescriptionDetails(int selectedRow) {
        if (this.view == null) {
            System.out.println("Error: view is null");
            return;
        }

        if (selectedRow >= 0 && selectedRow < this.model.getMedicamentosSeleccionados().size()) {
            RecetaDetalle currentDetalle = this.model.getMedicamentosSeleccionados().get(selectedRow);

            // Get the medication object
            Medicamento medication = Service.instance().findAllMedicamentos().stream()
                    .filter(m -> m.getCodigo().equals(currentDetalle.getCodigoMedicamento()))
                    .findFirst()
                    .orElse(null);

            if (medication != null) {
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this.view.getPanel());

                // Use existing dialog in EDIT mode
                sistema.presentation.popUpMedicamentoDetalles.View editDialog =
                        new sistema.presentation.popUpMedicamentoDetalles.View(parentFrame, medication, currentDetalle);
                editDialog.setVisible(true);

                if (editDialog.getRecetaDetalle() != null) {
                    // Remove old detail and add updated one
                    this.model.removeMedicamento(selectedRow);
                    this.model.addMedicamento(editDialog.getRecetaDetalle());

                    this.view.showMessage("Detalles del medicamento actualizados");
                }
            }
        }
    }
}
