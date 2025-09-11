package sistema.presentation.despachoRecetas;

import sistema.logic.entities.Receta;
import sistema.logic.entities.Paciente;
import sistema.logic.Service;
import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JPanel panel1;
    private JTextField textFieldIdPaciente;
    private JTable tableListEstadoRecetas;
    private JButton buscarButton;
    private JButton siguienteEstadoButton;
    private JButton estadoAnteriorButton;
    private JLabel pacienteAutomatico;
    private JLabel paciente;

    private Model model;
    private Controller controller;

    public View() {
        setupEventListeners();
    }

    private void setupEventListeners() {
        buscarButton.addActionListener(e -> {
            if (controller != null) {
                controller.searchPrescriptionsByPatient();
            }
        });

        siguienteEstadoButton.addActionListener(e -> {
            if (controller != null) {
                controller.moveToNextState();
            }
        });

        estadoAnteriorButton.addActionListener(e -> {
            if (controller != null) {
                controller.moveToPreviousState();
            }
        });

        textFieldIdPaciente.addActionListener(e -> {
            if (controller != null) {
                controller.searchPrescriptionsByPatient();
            }
        });

        // Table selection listener
        tableListEstadoRecetas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateButtonStates();
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.PRESCRIPTIONS_LIST:
                updatePrescriptionsTable();
                break;
            case Model.CURRENT_PRESCRIPTION:
                updateButtonStates();
                break;
            case Model.SELECTED_PATIENT:
                updatePatientInfo((Paciente) evt.getNewValue());
                break;
        }
    }

    private void updatePrescriptionsTable() {
        if (model != null) {
            int[] cols = {TableModel.ID, TableModel.PACIENTE, TableModel.MEDICO,
                    TableModel.FECHA_CONFECCION, TableModel.ESTADO};
            tableListEstadoRecetas.setModel(new TableModel(cols, model.getPrescriptionsList()));
        }
    }

    private void updatePatientInfo(Paciente patient) {
        if (patient != null && pacienteAutomatico != null) {
            pacienteAutomatico.setText(patient.getNombre());
        } else if (pacienteAutomatico != null) {
            pacienteAutomatico.setText("Paciente no encontrado");
        }
    }

    private void updateButtonStates() {
        int selectedRow = tableListEstadoRecetas.getSelectedRow();
        if (selectedRow >= 0 && model != null) {
            TableModel tableModel = (TableModel) tableListEstadoRecetas.getModel();
            Receta selected = tableModel.getRowAt(selectedRow);

            // Enable/disable buttons based on current state
            siguienteEstadoButton.setEnabled(selected.getEstado().name().equals("CONFECCIONADA") ||
                    selected.getEstado().name().equals("PROCESO") ||
                    selected.getEstado().name().equals("LISTA"));

            estadoAnteriorButton.setEnabled(selected.getEstado().name().equals("PROCESO") ||
                    selected.getEstado().name().equals("LISTA"));
        } else {
            siguienteEstadoButton.setEnabled(false);
            estadoAnteriorButton.setEnabled(false);
        }
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(panel1, message, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(panel1, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public String getPatientId() {
        return textFieldIdPaciente.getText().trim();
    }

    public int getSelectedRow() {
        return tableListEstadoRecetas.getSelectedRow();
    }

    public void clearForm() {
        textFieldIdPaciente.setText("");
        pacienteAutomatico.setText("");
        updateButtonStates();
    }

    public JPanel getPanel() { return panel1; }

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}