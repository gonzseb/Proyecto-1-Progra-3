package proyecto.presentation.medicos;

import proyecto.Application;
import proyecto.logic.entities.Medico;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JPanel panel;
    private JTextField idField;
    private JTextField especialidadField;
    private JTextField nombreField;
    private JButton guardarButton;
    private JButton borrarButton;
    private JButton limpiarButton;
    private JTextField nombreBusquedaField;
    private JButton buscarButton;
    private JButton reporteButton; // Falta esta implementación
    private JTable medicos;

    // -- MVC --
    private Controller controller;
    private Model model;

    public View() {
        guardarButton.addActionListener(e -> {
            if (validateForm()) {
                controller.create(take());
            }
        });

        buscarButton.addActionListener(e -> {
            String textoBusqueda = nombreBusquedaField.getText().trim();
            if (!textoBusqueda.isEmpty()) {
                controller.buscarMedicosPorNombre(textoBusqueda);
            } else {
                mostrarAviso("Ingrese un texto para buscar");
            }
        });

        limpiarButton.addActionListener(e -> {
            controller.clear();
            nombreBusquedaField.setText("");
        });

        medicos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = medicos.getSelectedRow();
                if (selectedRow >= 0) {
                    Medico seleccionado = ((TableModel) medicos.getModel()).getRowAt(selectedRow);
                    model.setCurrent(seleccionado);
                }
            }
        });

        borrarButton.addActionListener(e -> {
            if (model.getCurrent().getId().isEmpty()) {
                mostrarAviso("No hay médico seleccionado");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(panel,
                    "¿Está seguro de borrar al médico " + model.getCurrent().getNombre() + "?",
                    "Confirmar borrado",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                controller.delete(model.getCurrent().getId());
            }
        });
    }

    // --- Métodos auxiliares ---
    public Medico take() {
        Medico e = new Medico();
        e.setId(idField.getText());
        e.setNombre(nombreField.getText());
        e.setEspecialidad(especialidadField.getText());
        return e;
    }

    private boolean validateForm() {
        boolean valid = true;
        valid &= validarCampo(idField, "ID Requerido");
        valid &= validarCampo(nombreField, "Nombre requerido");
        valid &= validarCampo(especialidadField, "Especialidad requerida");
        return valid;
    }

    private boolean validarCampo(JTextField field, String mensajeError) {
        if (field.getText().isEmpty()) {
            field.setBackground(Application.BACKGROUND_ERROR);
            field.setToolTipText(mensajeError);
            return false;
        } else {
            field.setBackground(null);
            field.setToolTipText(null);
            return true;
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(panel, mensaje, "", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(panel, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarAviso(String mensaje) {
        JOptionPane.showMessageDialog(panel, mensaje, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    public JPanel getPanel() { return panel; }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.LIST:
                int[] cols = {TableModel.ID, TableModel.NOMBRE, TableModel.ESPECIALIDAD};
                medicos.setModel(new TableModel(cols, model.getList().listar()));
                break;
            case Model.CURRENT:
                idField.setText(model.getCurrent().getId());
                nombreField.setText(model.getCurrent().getNombre());
                especialidadField.setText(model.getCurrent().getEspecialidad());
                limpiarEstilosCampos();
                break;
        }
        panel.revalidate();
    }

    private void limpiarEstilosCampos() {
        idField.setBackground(null);
        idField.setToolTipText(null);
        nombreField.setBackground(null);
        nombreField.setToolTipText(null);
        especialidadField.setBackground(null);
        especialidadField.setToolTipText(null);
    }
}