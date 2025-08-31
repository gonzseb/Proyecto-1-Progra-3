package sistema.presentation.medicos;

import sistema.Application;

import sistema.logic.entities.Medico;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private Model model;
    private Controller controller;

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public View() {
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateForm()) {
                    Medico medico = take();
                    try {
                        controller.create(medico);
                        JOptionPane.showMessageDialog(panel, "Médico registrado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }
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

        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (model.getCurrent().getId().isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "No hay médico seleccionado", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(panel,
                        "¿Está seguro de borrar al médico " + model.getCurrent().getNombre() + "?",
                        "Confirmar borrado",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        controller.delete(model.getCurrent().getId());
                        JOptionPane.showMessageDialog(panel, "Médico eliminado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        buscarButton.addActionListener(e -> {
            String textoBusqueda = nombreBusquedaField.getText().trim();
            if (!textoBusqueda.isEmpty()) {
                controller.findMedicoByName(textoBusqueda);

                if (model.getList().isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "No se encontraron médicos con ese nombre.", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(panel, "Ingrese un texto para buscar.", "Campo vacío", JOptionPane.WARNING_MESSAGE);
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

    public JPanel getPanel() { return panel; }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.LIST:
                int[] cols = {TableModel.ID,TableModel.NOMBRE, TableModel.ESPECIALIDAD};

                medicos.setModel(new TableModel(cols, model.getList()));

                break;
            case Model.CURRENT:
                idField.setText(model.getCurrent().getId());
                nombreField.setText(model.getCurrent().getNombre());
                especialidadField.setText(model.getCurrent().getEspecialidad());

                idField.setBackground(null);
                idField.setToolTipText(null);

                nombreField.setBackground(null);
                nombreField.setToolTipText(null);

                especialidadField.setBackground(null);
                especialidadField.setToolTipText(null);

                break;
        }
        this.panel.revalidate();
    }
}