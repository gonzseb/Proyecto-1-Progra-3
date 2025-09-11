package sistema.presentation.prescribir;

import com.github.lgooddatepicker.components.DatePicker;
import sistema.logic.entities.Paciente;
import sistema.ApplicationLogIn;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;

public class View implements PropertyChangeListener {
    private JPanel panel1;
    private JButton buscarPacienteButton;
    private JButton agregarMedicamentoButton;
    private JTable medicamentosTable;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton descartarMedicamentoButton;
    private JButton detallesButton;
    private DatePicker fechaRetiro;
    private JLabel pacienteInfoLabel;

    private Model model;
    private Controller controller;

    public View() {
        // Initialize date picker with default date (today + 7 days)
        fechaRetiro.setDate(LocalDate.now().plusDays(7));

        // Setup event listeners
        setupEventListeners();
    }

    private void setupEventListeners() {
        buscarPacienteButton.addActionListener(e -> {
            if (controller != null) {
                controller.showPatientSelection();
            }
        });

        agregarMedicamentoButton.addActionListener(e -> {
            if (controller != null) {
                controller.showMedicationSelection();
            }
        });

        guardarButton.addActionListener(e -> {
            if (controller != null) {
                try {
                    controller.saveReceta();
                } catch (Exception ex) {
                    showError(ex.getMessage());
                }
            }
        });

        limpiarButton.addActionListener(e -> {
            if (controller != null) {
                controller.clearForm();
            }
        });

        descartarMedicamentoButton.addActionListener(e -> {
            int selectedRow = medicamentosTable.getSelectedRow();
            if (selectedRow >= 0 && controller != null) {
                controller.removeMedicamento(selectedRow);
            } else {
                showMessage("Seleccione un medicamento para descartar");
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.SELECTED_PACIENTE:
                updatePatientInfo((Paciente) evt.getNewValue());
                break;
            case Model.MEDICAMENTOS_LIST:
                updateMedicamentosTable();
                break;
        }
    }

    private void updatePatientInfo(Paciente paciente) {
        if (paciente != null && pacienteInfoLabel != null) {
            pacienteInfoLabel.setText(paciente.getNombre() + " (ID: " + paciente.getId() + ")");
        }
    }

    private void updateMedicamentosTable() {
        if (model != null) {
            int[] cols = {TableModel.MEDICAMENTO, TableModel.PRESENTACION, TableModel.CANTIDAD,
                    TableModel.INDICACIONES, TableModel.DURACION};
            medicamentosTable.setModel(new TableModel(cols, model.getMedicamentosSeleccionados()));
        }
    }

    public boolean validateForm() {
        boolean valid = true;

        if (model.getSelectedPaciente() == null) {
            showError("Debe seleccionar un paciente");
            valid = false;
        }

        if (model.getMedicamentosSeleccionados().isEmpty()) {
            showError("Debe agregar al menos un medicamento");
            valid = false;
        }

        if (fechaRetiro.getDate() == null || fechaRetiro.getDate().isBefore(LocalDate.now())) {
            fechaRetiro.setBackground(ApplicationLogIn.BACKGROUND_ERROR);
            showError("La fecha de retiro debe ser posterior a hoy");
            valid = false;
        } else {
            fechaRetiro.setBackground(null);
        }

        return valid;
    }

    public void clearForm() {
        if (pacienteInfoLabel != null) {
            pacienteInfoLabel.setText("No hay paciente seleccionado");
        }
        fechaRetiro.setDate(LocalDate.now().plusDays(7));
        fechaRetiro.setBackground(null);
        updateMedicamentosTable();
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(panel1, message, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(panel1, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Getters and setters
    public JPanel getPanel() { return panel1; }
    public LocalDate getFechaRetiro() { return fechaRetiro.getDate(); }

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}