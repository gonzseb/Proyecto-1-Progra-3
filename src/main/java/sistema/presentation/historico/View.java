package sistema.presentation.historico;

import sistema.logic.entities.Receta;
import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JComboBox<String> comboBox1;
    private JTextField textField1;
    private JButton buscarButton;
    private JTable tableRecetas;
    private JPanel mainPanel;
    private JButton verDetallesButton;
    private JButton limpiarButton;

    private Model model;
    private Controller controller;

    public View() {
        setupComponents();
        setupEventListeners();
    }

    private void setupComponents() {
        // Setup ComboBox options
        comboBox1.addItem("ID Receta");
        comboBox1.addItem("ID Paciente");
        comboBox1.addItem("ID Médico");
        comboBox1.addItem("Estado");
    }

    private void setupEventListeners() {
        buscarButton.addActionListener(e -> {
            if (controller != null) {
                controller.search();
            }
        });

        verDetallesButton.addActionListener(e -> {
            if (controller != null) {
                controller.viewDetails();
            }
        });

        limpiarButton.addActionListener(e -> {
            if (controller != null) {
                controller.clearSearch();
            }
        });

        textField1.addActionListener(e -> {
            if (controller != null) {
                controller.search();
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.LIST:
                updateTable();
                break;
        }
    }

    private void updateTable() {
        if (model != null) {
            int[] cols = {TableModel.ID, TableModel.PACIENTE, TableModel.MEDICO,
                    TableModel.FECHA_CONFECCION, TableModel.ESTADO};
            tableRecetas.setModel(new TableModel(cols, model.getList()));
        }
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(mainPanel, message, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(mainPanel, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Getters for Controller
    public String getSearchType() {
        return (String) comboBox1.getSelectedItem();
    }

    public String getSearchText() {
        return textField1.getText().trim();
    }

    public int getSelectedRow() {
        return tableRecetas.getSelectedRow();
    }

    public void clearSearchField() {
        textField1.setText("");
    }

    // Standard MVC methods
    public JPanel getPanel() { return mainPanel; }

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}